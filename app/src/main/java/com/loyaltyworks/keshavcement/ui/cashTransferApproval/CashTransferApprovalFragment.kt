package com.loyaltyworks.keshavcement.ui.cashTransferApproval

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferApprovalBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.cashTransferApproval.adapter.CashTransferApprovalAdapter
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.myRedemption.MyRedemptionViewModel
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog
import java.util.ArrayList


class CashTransferApprovalFragment : Fragment(), CashTransferApprovalAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCashTransferApprovalBinding
    private lateinit var myRedemptionViewModel: MyRedemptionViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel
    private lateinit var viewModel: CashTransferApprovalViewModel

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempCashTransferApprovalList = ArrayList<ObjCatalogueRedemReq>()
    var currentList = ArrayList<ObjCatalogueRedemReq>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mCashTransferApprovalLayoutManager: LinearLayoutManager? = null
    var cashTransferApprovalAdapter: RecyclerView.Adapter<*>? = null

    private lateinit var OTPNumber: String
    var successMsg: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myRedemptionViewModel = ViewModelProvider(this).get(MyRedemptionViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CashTransferApprovalViewModel::class.java)
        binding = FragmentCashTransferApprovalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CashTransferApprovalView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CashTransferApprovalFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.searchFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {

                if (binding.searchFilter.hasFocus()){
                    callApi(1,s.toString())
                    Log.d("rbfhrbfhr","call 1")
                }

            }
        })


        // use a linear layout manager
        mCashTransferApprovalLayoutManager = LinearLayoutManager(requireContext())
        mCashTransferApprovalLayoutManager!!.isAutoMeasureEnabled = true
        mCashTransferApprovalLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.cashTransferApprovalRecycler.layoutManager = mCashTransferApprovalLayoutManager
        binding.cashTransferApprovalRecycler.isNestedScrollingEnabled = true
        binding.cashTransferApprovalRecycler.setHasFixedSize(false)
        binding.cashTransferApprovalRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.cashTransferApprovalRecycler.itemAnimator = DefaultItemAnimator()

        loadCashTransferApprovalObserver()
        setFileterSpinners()
    }


    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mCashTransferApprovalLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1, binding.searchFilter.text.toString())
            }
        }
        binding.cashTransferApprovalRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        LoadingDialogue.showDialog(requireContext())
        callApi( 1, binding.searchFilter.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    private fun callApi(startIndex: Int,srchTxt:String) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()



        val  myRedemptionRequest = MyRedemptionRequest()

        myRedemptionRequest.actionType = "52"
        myRedemptionRequest.partyLoyaltyID =  PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId
        myRedemptionRequest.startIndex = startIndex
        myRedemptionRequest.noOfRows = limit
        myRedemptionRequest.searchText = srchTxt
//        myRedemptionRequest.customerTypeID = selectedCustTypeId

        val objCatalogueDetails = ObjCatalogueDetails()
        objCatalogueDetails.selectedStatus = "0"
        objCatalogueDetails.catogoryId = "8"
        objCatalogueDetails.redemptionTypeId = -1

//        if (FromDate.isNotEmpty() && ToDate.isNotEmpty()){
//            objCatalogueDetails.jFromDate = AppController.dateAPIFormats(FromDate)
//            objCatalogueDetails.jToDate = AppController.dateAPIFormats(ToDate)
//        }

        myRedemptionRequest.objCatalogueDetails = objCatalogueDetails

        myRedemptionViewModel.setMyRedemptionListingRequest(myRedemptionRequest)
    }


    private fun loadCashTransferApprovalObserver() {
        /*** Cash Transfer Approval Listing Observer ***/
        myRedemptionViewModel.myRedemptionLiveData.observe(viewLifecycleOwner, Observer{
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.objCatalogueRedemReqList.isNullOrEmpty()) {
                    binding.cashTransferApprovalRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempCashTransferApprovalList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        cashTransferApprovalAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (redemption in it.objCatalogueRedemReqList){
                        tempCashTransferApprovalList.add(tempCashTransferApprovalList.size,redemption)
                    }

                    currentList.clear()
                    currentList.addAll(tempCashTransferApprovalList)

                    if (!isLoaded) {
                        isLoaded = true
                        cashTransferApprovalAdapter = CashTransferApprovalAdapter(currentList,this)
                        binding.cashTransferApprovalRecycler.adapter = cashTransferApprovalAdapter
                    }else{
                        cashTransferApprovalAdapter!!.notifyDataSetChanged()
                    }



                } else {
                    if (page == 1){
                        binding.cashTransferApprovalRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }

        })

    }

    override fun onApproveClickResponse(
        itemView: View,
        position: Int,
        status: String,
        objCatalogueRedemReqList: ObjCatalogueRedemReq
    ) {
        SendOtpRequest()

        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString()
            ,"Submit" ,object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {
                }

                override fun onRedeemClick(otp: String) {
                    if(otp =="123456" /*OTPNumber*/){
                        RedeemOTPDialog.hideDialog()
                        successMsg = getString(R.string.cash_voucher_approved)

                        /*** Call Approve Api ***/
                        approveRejectApi(objCatalogueRedemReqList,status)

                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest()
                }
            })
    }



    override fun onRejectClickResponse(
        itemView: View,
        position: Int,
        status: String,
        objCatalogueRedemReqList: ObjCatalogueRedemReq
    ) {
        SendOtpRequest()

        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString()
            ,"Submit",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {
                }

                override fun onRedeemClick(otp: String) {
                    if(otp == "123456"/*OTPNumber*/){
                        RedeemOTPDialog.hideDialog()
                        successMsg = getString(R.string.cash_voucher_rejected)

                        /*** Call Reject Api ***/
                        approveRejectApi(objCatalogueRedemReqList,status)

                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest()
                }
            })
    }


    private fun approveRejectApi(objCatalogueRedemReqList: ObjCatalogueRedemReq, status: String) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCashTransferApproveReject(
            CashTransferApproveRejectRequest(
                actionType = "264",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                domain = "KESHAV_CEMENT",
                searchText = objCatalogueRedemReqList.enteredRemarks,
                objCatalogueDetails = ObjCatalogueDetailsCashTransferApproval(
                    memberName = objCatalogueRedemReqList.loyaltyId,
                    status = status,
                    redemptionId = objCatalogueRedemReqList.redemptionId
                )
            )
        )

    }

    private fun SendOtpRequest() {
        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString(),
                userId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                userName = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId,
                name = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName.toString()
            )
        )

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  OTP observer ***/
        loginViewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (!it.returnMessage.isNullOrEmpty()) {
                    OTPNumber = it.returnMessage.toString()
                    Log.d("otp", it.returnMessage.toString())

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

        /*** Cash Transfer Approve/Reject Observer ***/
        viewModel.cashTransferApproveRejectLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage.split("~")[0] == "1"){
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",
                        successMsg,object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                currentList.clear()
                                binding.searchFilter.text.clear()
                                callApi(1,"")
                            }

                        })
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }
}