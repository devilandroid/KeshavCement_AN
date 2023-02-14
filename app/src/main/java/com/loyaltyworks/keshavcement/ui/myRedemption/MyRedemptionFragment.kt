package com.loyaltyworks.keshavcement.ui.myRedemption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMyRedemptionBinding
import com.loyaltyworks.keshavcement.model.CommonStatusSpinner
import com.loyaltyworks.keshavcement.model.MyRedemptionRequest
import com.loyaltyworks.keshavcement.model.ObjCatalogueDetails
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.ui.enrollment.EnrollmentViewModel
import com.loyaltyworks.keshavcement.ui.myRedemption.adapter.MyRedemptionAdapter
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class MyRedemptionFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMyRedemptionBinding
    private lateinit var myRedemptionViewModel: MyRedemptionViewModel

    var userID: Int = -1

    private var fromDate: String = ""
    private var toDate: String =""

    var catalogeTypeList = mutableListOf<CommonStatusSpinner>()
    private lateinit var mSelectedCatalogueTYpe:CommonStatusSpinner
    var statusId: Int = -1

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempRedemptionList = ArrayList<ObjCatalogueRedemReq>()
    var currentList = ArrayList<ObjCatalogueRedemReq>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mRedemptionLayoutManager: LinearLayoutManager? = null
    var redemptionAdapter: RecyclerView.Adapter<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myRedemptionViewModel = ViewModelProvider(this).get(MyRedemptionViewModel::class.java)
        binding =  FragmentMyRedemptionBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MyRedemptionView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MyRedemptionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)

        HandleOnBackPressed()

        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)


        userID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toInt()

        // use a linear layout manager
        mRedemptionLayoutManager = LinearLayoutManager(requireContext())
        mRedemptionLayoutManager!!.isAutoMeasureEnabled = true
        mRedemptionLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.redeemRecycler.layoutManager = mRedemptionLayoutManager
        binding.redeemRecycler.isNestedScrollingEnabled = true
        binding.redeemRecycler.setHasFixedSize(false)
        binding.redeemRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.redeemRecycler.itemAnimator = DefaultItemAnimator()

        loadRedemptionListObserver()
        setFileterSpinners()
    }


    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mRedemptionLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.redeemRecycler.addOnScrollListener(scrollListener!!)
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

        val  myRedemptionRequest = MyRedemptionRequest()

        myRedemptionRequest.actionType = "52"
        myRedemptionRequest.actorId = userID.toString()
        myRedemptionRequest.startIndex = startIndex
        myRedemptionRequest.noOfRows = limit

        val objCatalogueDetails = ObjCatalogueDetails()
        objCatalogueDetails.selectedStatus = -1
        objCatalogueDetails.redemptionTypeId = statusId

        if (fromDate.isNotEmpty() && toDate.isNotEmpty()){
            objCatalogueDetails.jFromDate = fromDate
            objCatalogueDetails.jToDate = toDate
        }

        myRedemptionRequest.objCatalogueDetails = objCatalogueDetails

        myRedemptionViewModel.setMyRedemptionListingRequest(myRedemptionRequest)
    }

    private fun loadRedemptionListObserver() {
        /*** Redemption list Observer ***/
        myRedemptionViewModel.myRedemptionLiveData.observe(viewLifecycleOwner, Observer{
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.objCatalogueRedemReqList.isNullOrEmpty()) {
                    binding.redeemRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempRedemptionList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        redemptionAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (redemption in it.objCatalogueRedemReqList){
                        tempRedemptionList.add(tempRedemptionList.size,redemption)
                    }

                    currentList.clear()
                    currentList.addAll(tempRedemptionList)

                    if (!isLoaded) {
                        isLoaded = true
                        redemptionAdapter = MyRedemptionAdapter(currentList)
                        binding.redeemRecycler.adapter = redemptionAdapter
                    }else{
                        redemptionAdapter!!.notifyDataSetChanged()
                    }



                } else {
                    if (page == 1){
                        binding.redeemRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }

        })
    }



    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.filterOpen -> {

                binding.filterLayout.visibility = View.VISIBLE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_up_dialog)
            }

            R.id.filterClose -> {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }
        }

    }



    /**This is required here for handling action bar navigate up button*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            if (binding.filterLayout.visibility == View.VISIBLE) {
                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            } else {
                requireActivity().onBackPressed()
            }
            return true
        }else{
            return super.onOptionsItemSelected(item)
        }
    }

    private fun HandleOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.filterLayout.visibility == View.VISIBLE) {
                    binding.filterLayout.visibility = View.GONE
                    binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
                } else {
                    isEnabled = false
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}