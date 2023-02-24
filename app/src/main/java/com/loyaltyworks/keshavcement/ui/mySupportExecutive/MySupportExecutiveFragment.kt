package com.loyaltyworks.keshavcement.ui.mySupportExecutive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMySupportExecutiveBinding
import com.loyaltyworks.keshavcement.model.ActivateDeactivateExecutiveRequest
import com.loyaltyworks.keshavcement.model.LstCustParentChildMapping
import com.loyaltyworks.keshavcement.model.MySupportExecutiveRequest
import com.loyaltyworks.keshavcement.ui.mySupportExecutive.adapter.MySupportExecutiveAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList

class MySupportExecutiveFragment : Fragment(), View.OnClickListener, MySupportExecutiveAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentMySupportExecutiveBinding
    private lateinit var viewModel: MySupportExecutiveViewModel

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempSupportExecutiveList = ArrayList<LstCustParentChildMapping>()
    var currentList = ArrayList<LstCustParentChildMapping>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mSupportExecutiveLayoutManager: LinearLayoutManager? = null
    var supportExecutiveAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MySupportExecutiveViewModel::class.java)
        binding = FragmentMySupportExecutiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MySupportExecutiveView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MySupportExecutiveFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.createNew.setOnClickListener(this)

        // use a linear layout manager
        mSupportExecutiveLayoutManager = LinearLayoutManager(requireContext())
        mSupportExecutiveLayoutManager!!.isAutoMeasureEnabled = true
        mSupportExecutiveLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.mySupportExecutiveRecycler.layoutManager = mSupportExecutiveLayoutManager
        binding.mySupportExecutiveRecycler.isNestedScrollingEnabled = true
        binding.mySupportExecutiveRecycler.setHasFixedSize(false)
        binding.mySupportExecutiveRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.mySupportExecutiveRecycler.itemAnimator = DefaultItemAnimator()

        loadSupportExecutiveListObserver()
        setFileterSpinners()
    }
    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mSupportExecutiveLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.mySupportExecutiveRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        callApi( 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }


    private fun callApi(startIndex: Int) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()

        LoadingDialogue.showDialog(requireContext())
        viewModel.getSupportExecutiveListData(
            MySupportExecutiveRequest(
                actionType = 17,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                startIndex = startIndex,
                noOfRows = limit
            )
        )
    }

    private fun loadSupportExecutiveListObserver() {
        /*** My Support Executive List Observer ***/
        viewModel.supportExecutiveListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstCustParentChildMapping.isNullOrEmpty()){
                    binding.mySupportExecutiveRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempSupportExecutiveList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        supportExecutiveAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (supportExecutive in it.lstCustParentChildMapping){
                        tempSupportExecutiveList.add(tempSupportExecutiveList.size,supportExecutive)
                    }

                    currentList.clear()
                    currentList.addAll(tempSupportExecutiveList)

                    if (!isLoaded) {
                        isLoaded = true
                        supportExecutiveAdapter = MySupportExecutiveAdapter(currentList,this)
                        binding.mySupportExecutiveRecycler.adapter = supportExecutiveAdapter
                    }else{
                        supportExecutiveAdapter!!.notifyDataSetChanged()
                    }


                }else {
                    if (page == 1){
                        binding.mySupportExecutiveRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.create_new ->{
            if (BlockMultipleClick.click())return
                currentList.clear()
            findNavController().navigate(R.id.action_mySupportExecutiveFragment_to_newSupportExecutiveFragment)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        }
    }

    override fun onActivateDeactivateClickResponse(
        itemView: View,
        status: String,
        lstCustParentChildMapping: LstCustParentChildMapping
    ) {
        viewModel.getActivateDeactivateSupportExecutiveData(
            ActivateDeactivateExecutiveRequest(
                actionType = "5",
                userid = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                customerId = lstCustParentChildMapping.userID.toString(),
                isActive = status
            )
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.activateDeactivateSupportExecutiveLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.returnMessage.isNullOrEmpty()){
                    if (it.returnMessage == "1"){
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","Status updated ",
                            object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    currentList.clear()
                                    callApi( 1)
                                }

                            })

                    }else{
                        Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}