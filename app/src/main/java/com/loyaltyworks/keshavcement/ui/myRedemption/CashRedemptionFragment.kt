package com.loyaltyworks.keshavcement.ui.myRedemption

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashRedemptionBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonStatusAdapter
import com.loyaltyworks.keshavcement.model.adapter.StatusSpinnerAdapter
import com.loyaltyworks.keshavcement.ui.myRedemption.adapter.CashRedemptionAdapter
import com.loyaltyworks.keshavcement.ui.myRedemption.adapter.MyRedemptionAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class CashRedemptionFragment : Fragment(),View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentCashRedemptionBinding
    private lateinit var myRedemptionViewModel: MyRedemptionViewModel

    var userID: Int = -1

    var statusList = mutableListOf<CommonStatusSpinner>()
    var statusId: Int = -1
    private var FromDate: String = ""
    private var ToDate: String =""

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
        binding = FragmentCashRedemptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CashTransferRedemptionView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CashTransferRedemptionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)
        HandleOnBackPressed()


        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
        binding.clearBtn.setOnClickListener(this)
        binding.filterOkBtn.setOnClickListener(this)
        binding.fromDate.setOnClickListener(this)
        binding.toDate.setOnClickListener(this)

        binding.statusSpinner.onItemSelectedListener = this

        userID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toInt()

        statusSpinner()

        // use a linear layout manager
        mRedemptionLayoutManager = LinearLayoutManager(requireContext())
        mRedemptionLayoutManager!!.isAutoMeasureEnabled = true
        mRedemptionLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.cashRedeemRecycler.layoutManager = mRedemptionLayoutManager
        binding.cashRedeemRecycler.isNestedScrollingEnabled = true
        binding.cashRedeemRecycler.setHasFixedSize(false)
        binding.cashRedeemRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.cashRedeemRecycler.itemAnimator = DefaultItemAnimator()

        loadRedemptionListObserver()
        setFileterSpinners()
    }

    private fun statusSpinner() {

        statusList.clear()

        statusList.add( CommonStatusSpinner(productName = "Select Status", id = -1))
        statusList.add( CommonStatusSpinner(productName = "Pending", id = 0))
        statusList.add( CommonStatusSpinner(productName = "Approved", id = 1))
        statusList.add( CommonStatusSpinner(productName = "Rejected", id = 2))

        binding.statusSpinner.adapter = SpinnerCommonStatusAdapter(requireActivity(),R.layout.spinner_popup_row,statusList)
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
        binding.cashRedeemRecycler.addOnScrollListener(scrollListener!!)
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

        myRedemptionRequest.actionType = "277"
        myRedemptionRequest.actorId = userID.toString()
        myRedemptionRequest.startIndex = startIndex
        myRedemptionRequest.noOfRows = limit

        val objCatalogueDetails = ObjCatalogueDetails()
        objCatalogueDetails.selectedStatus = statusId.toString()
        objCatalogueDetails.redemptionTypeId = -1

        if (FromDate.isNotEmpty() && ToDate.isNotEmpty()){
            objCatalogueDetails.jFromDate = AppController.dateAPIFormats(FromDate)
            objCatalogueDetails.jToDate = AppController.dateAPIFormats(ToDate)
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
                    binding.cashRedeemRecycler.visibility = View.VISIBLE
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
                        redemptionAdapter = CashRedemptionAdapter(currentList)
                        binding.cashRedeemRecycler.adapter = redemptionAdapter
                    }else{
                        redemptionAdapter!!.notifyDataSetChanged()
                    }



                } else {
                    if (page == 1){
                        binding.cashRedeemRecycler.visibility = View.GONE
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
            R.id.filterOpen -> {

                binding.filterLayout.visibility = View.VISIBLE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_up_dialog)
            }

            R.id.filterClose -> {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.from_date ->{
                DatePickerBox.date(1, activity) {
                    binding.fromDateTxt.text = it.toString()
                    FromDate = it
                    try {
                        DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                            if (!it) {
                                FromDate = ""
                                binding.fromDateTxt.text = ""
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            R.id.to_date ->{
                DatePickerBox.date(1, activity) {
                    binding.toDateTxt.text = it.toString()
                    ToDate = it
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            ToDate = ""
                            binding.toDateTxt.text = ""
                        }
                    }
                }
            }


            R.id.clear_btn ->{
                statusId = -1
                FromDate = ""
                ToDate = ""
                binding.fromDateTxt.text = ""
                binding.toDateTxt.text = ""
                binding.fromDateTxt.hint = getString(R.string.select_from_date)
                binding.toDateTxt.hint = getString(R.string.select_to_date)
                binding.statusSpinner.setSelection(0)

                listFull = false
                isLoaded = false

                currentList.clear()
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.filter_ok_btn ->{
                if (FromDate.isNotEmpty() && ToDate.isEmpty()) {
                    Toast.makeText(requireContext(), "To date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                } else if (FromDate.isEmpty() && ToDate.isNotEmpty()) {
                    Toast.makeText(requireContext(), "From date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                }

                currentList.clear()
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.status_spinner -> {
                statusId = (parent.getItemAtPosition(position) as CommonStatusSpinner).id!!
                Log.d("fdsfsdf", "status id : " + statusId)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

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