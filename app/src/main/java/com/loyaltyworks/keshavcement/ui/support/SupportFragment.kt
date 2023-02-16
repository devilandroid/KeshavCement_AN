package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentSupportBinding
import com.loyaltyworks.keshavcement.model.ObjCustomerAllQueryJson
import com.loyaltyworks.keshavcement.model.QueryListingRequest
import com.loyaltyworks.keshavcement.ui.support.adapter.SupportAdapter
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.HelpTopicsDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_support.*
import java.util.ArrayList


class SupportFragment : Fragment(), SupportAdapter.OnClickCallBack, View.OnClickListener,
    HelpTopicsDialog.OnItemClickCallback {

    private lateinit var binding: FragmentSupportBinding

    private lateinit var viewModel: SupportViewModel
    var userID: Int = -1
    var actionType: Int = -1

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempQueryList = ArrayList<ObjCustomerAllQueryJson>()
    var currentList = ArrayList<ObjCustomerAllQueryJson>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mQueryLayoutManager: LinearLayoutManager? = null
    var queryAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(SupportViewModel::class.java)
        binding =  FragmentSupportBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "QueryListView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "QueryListFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)


        userID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toInt()

        binding.newTicketLl.setOnClickListener(this)

        // use a linear layout manager
        mQueryLayoutManager = LinearLayoutManager(requireContext())
        mQueryLayoutManager!!.isAutoMeasureEnabled = true
        mQueryLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.queryListRecycler.layoutManager = mQueryLayoutManager
        binding.queryListRecycler.isNestedScrollingEnabled = true
        binding.queryListRecycler.setHasFixedSize(false)
        binding.queryListRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.queryListRecycler.itemAnimator = DefaultItemAnimator()

        loadQueryListObserver()
        setFileterSpinners()
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mQueryLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                queryListApi(page + 1)
            }
        }
        binding.queryListRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        queryListApi(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    private fun loadQueryListObserver() {
        /*** Query list Observer ***/
        viewModel.queryListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()

                if (it != null && !it.objCustomerAllQueryJsonList.isNullOrEmpty()) {
                    query_list_recycler?.visibility = View.VISIBLE
//                query_filterDisplay?.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempQueryList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        queryAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }
                    for (query in it.objCustomerAllQueryJsonList){
                        tempQueryList.add(tempQueryList.size,query)
                    }
                    currentList.clear()
                    currentList.addAll(tempQueryList)

                    if (!isLoaded) {
                        isLoaded = true
                        queryAdapter = SupportAdapter(currentList, this)
                        binding.queryListRecycler.adapter = queryAdapter
                    }else{
                        queryAdapter!!.notifyDataSetChanged()
                    }



                    /* // navigate to chat page
                     val bundles = this.arguments
                     if (bundles != null) {
                         val chatId = arguments?.getInt("ChatID")
                         // clear arguments after data fetch
                         arguments?.clear()
                         if (chatId != null) {
                             it.objCustomerAllQueryJsonList.forEachIndexed { index, objCustomerAllQueryList ->
                                 if (chatId == objCustomerAllQueryList.customerTicketID) {
                                     val bundle = Bundle()
                                     bundle.putSerializable(
                                         "SUPPORT_DATA",
                                         it.objCustomerAllQueryJsonList[index]
                                     )
                                     view?.findNavController()?.navigate(
                                         R.id.action_supportFragment_to_queryChatFragment,
                                         bundle
                                     )
                                 }
                             }

                         }
                     }*/

                } else {
                    if (page == 1){
                        binding.queryListRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }

        })
    }

    private fun queryListApi(startIndex: Int) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()


        LoadingDialogue.showDialog(requireContext())
        viewModel.getQueryListLiveData(
            QueryListingRequest(
                actionType = "1",
                actorId = userID.toString(),
                ticketStatusId = "-1",
                helpTopicID = "-1",
                startIndex = startIndex,
                pageSize = limit
            )
        )
    }


    override fun onQueryListItemClickResponse(itemView: View, position: Int,productList: List<ObjCustomerAllQueryJson?>?) {
        val bundle = Bundle()
        bundle.putSerializable("SUPPORT_DATA", productList?.get(position))
        bundle.putInt("ActionId", actionType)
        itemView.findNavController().navigate(R.id.action_supportFragment_to_queryChatFragment, bundle)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){
            R.id.new_ticket_ll -> {
                findNavController().navigate(R.id.newTicketFragment)
//                 HelpTopicsDialog.showDialog(requireContext(),this)
            }
        }
    }

    override fun onHelpTopicsItemClicked() {
        findNavController().navigate(R.id.newTicketFragment)
    }


}