package com.loyaltyworks.keshavcement.ui.pendingClaimRequest

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
import com.loyaltyworks.keshavcement.databinding.FragmentPendingClaimRequestBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.pendingClaimRequest.adapter.PendingClaimRequestAdapter
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog
import java.util.ArrayList


class PendingClaimRequestFragment : Fragment(), PendingClaimRequestAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentPendingClaimRequestBinding
    private lateinit var viewModel: PendingClaimViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel
    private lateinit var stockViewModel: PurchaseRequestViewModel
    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempPendingClaimList = ArrayList<LstTransactionApprovalDetail>()
    var currentList = ArrayList<LstTransactionApprovalDetail>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mPendingClaimLayoutManager: LinearLayoutManager? = null
    var pendingClaimAdapter: RecyclerView.Adapter<*>? = null

    private lateinit var OTPNumber: String
    var successMsg: String = ""

    private lateinit var clickedItemData: LstTransactionApprovalDetail
    private lateinit var approveStatus: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PendingClaimViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        stockViewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        binding = FragmentPendingClaimRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_PendingClaimRequestView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_PendingClaimRequestFragment")
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
        mPendingClaimLayoutManager = LinearLayoutManager(requireContext())
        mPendingClaimLayoutManager!!.isAutoMeasureEnabled = true
        mPendingClaimLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.pendingClaimRecycler.layoutManager = mPendingClaimLayoutManager
        binding.pendingClaimRecycler.isNestedScrollingEnabled = true
        binding.pendingClaimRecycler.setHasFixedSize(false)
        binding.pendingClaimRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.pendingClaimRecycler.itemAnimator = DefaultItemAnimator()

        loadPendingClaimListObserver()
        setFileterSpinners()

    }


    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mPendingClaimLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1,binding.searchFilter.text.toString())
            }
        }
        binding.pendingClaimRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        LoadingDialogue.showDialog(requireContext())
        callApi( 1,binding.searchFilter.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    private fun callApi(startIndex: Int,srchTxt: String) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()


        viewModel.getPendingClaimListData(
            PendingClaimListRequest(
                actionType = 21,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                approvalStatusID = 0,
                customerTypeId = -1,
                noOfRows = limit,
                startIndex = startIndex,
                searchText = srchTxt
            )
        )
    }

    private fun loadPendingClaimListObserver() {
        /*** Pending Claim List Observer ***/
        viewModel.pendingClaimListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstTransactionApprovalDetails.isNullOrEmpty()){
                    binding.pendingClaimRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempPendingClaimList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        pendingClaimAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (claims in it.lstTransactionApprovalDetails){
                        tempPendingClaimList.add(tempPendingClaimList.size,claims)
                    }

                    currentList.clear()
                    currentList.addAll(tempPendingClaimList)

                    if (!isLoaded) {
                        isLoaded = true
                        pendingClaimAdapter = PendingClaimRequestAdapter(currentList,this)
                        binding.pendingClaimRecycler.adapter = pendingClaimAdapter
                    }else{
                        pendingClaimAdapter!!.notifyDataSetChanged()
                    }

                }else{
                    if (page == 1){
                        binding.pendingClaimRecycler.visibility = View.GONE
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
        lstTransactionApprovalDetails: LstTransactionApprovalDetail
    ) {
        clickedItemData = lstTransactionApprovalDetails
        approveStatus = status
        /*** Checking Stock Availability ***/
        checkStockStatusApi(lstTransactionApprovalDetails.updatedQuantity,lstTransactionApprovalDetails.prodCode!!)

    }

    private fun approveApiCall(clickedItemData: LstTransactionApprovalDetail) {
        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.pending_claims),getString(R.string.enter_otp_to_complete_claim),clickedItemData.mobile.toString(),
            "Submit",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {
                }

                override fun onRedeemClick(otp: String) {
                    if(otp == OTPNumber){
                        RedeemOTPDialog.hideDialog()
                        successMsg = getString(R.string.approved_the_purchase)

                        /*** Call Approve Api ***/
                        approveRejectClaimApi(clickedItemData.updatedQuantity, clickedItemData.ltyTranTempID!!,
                            clickedItemData.enteredRemarks,approveStatus)

                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp),Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest(clickedItemData)
                }
            })

    }


    override fun onRejectClickResponse(
        itemView: View,
        position: Int,
        status: String,
        lstTransactionApprovalDetails: LstTransactionApprovalDetail
    ) {

        successMsg = getString(R.string.rejected_the_purchase)

        /*** Call Reject Api ***/
        approveRejectClaimApi(lstTransactionApprovalDetails.quantity!!,lstTransactionApprovalDetails.ltyTranTempID!!,
            lstTransactionApprovalDetails.enteredRemarks,status)

       /* SendOtpRequest(lstTransactionApprovalDetails)

        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),lstTransactionApprovalDetails.mobile.toString(),
            "Submit",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {
                }

                override fun onRedeemClick(otp: String) {
                    if(otp == "123456"*//*OTPNumber*//*){
                        RedeemOTPDialog.hideDialog()
                        successMsg = getString(R.string.rejected_the_purchase)

                        *//*** Call Reject Api ***//*
                        approveRejectClaimApi(lstTransactionApprovalDetails.quantity!!,lstTransactionApprovalDetails.ltyTranTempID!!,
                            lstTransactionApprovalDetails.enteredRemarks,status)

                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp),Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest(lstTransactionApprovalDetails)
                }
            })
*/
    }


    private fun checkStockStatusApi(updatedQuantity: Int, ltyTranTempID: String) {
        LoadingDialogue.showDialog(requireContext())
        stockViewModel.getCheckStockData(
            SubmitPurchaseRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ritailerId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                approvalStatus = "5",
                productSaveDetailList = listOf(
                    ProductSaveDetail(
                        productCode = ltyTranTempID.toString(),
                        quantity = updatedQuantity.toString()
                    )
                )

            )
        )
    }


    private fun SendOtpRequest(lstTransactionApprovalDetails: LstTransactionApprovalDetail) {
        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = lstTransactionApprovalDetails.mobile,
                userId = "-1",
                userName = lstTransactionApprovalDetails.loyaltyId,
                name = lstTransactionApprovalDetails.memberName.toString(),
                oTPType = "OTPForRewardCardsENCashAuthorization"
            )
        )

    }


    private fun approveRejectClaimApi(updatedQuantity: Int, ltyTranTempID: Int,remarks: String,status: String) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getApproveRejectPendingClaimData(
            PendingClaimApproveRejectRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                approvalRemarks = remarks,
                approvalStatusID = status,
                lstTransactionApprovals = listOf(LstTransactionApproval(
                    ltyTranTempID = ltyTranTempID,
                    quantity = updatedQuantity
                ))
            )
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Check Stock Avalability Observer ***/
        stockViewModel.checkStockLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.pointsBalance != null){

                if (it.pointsBalance == 0){
                    LoadingDialogue.dismissDialog()
                    Toast.makeText(requireContext(), getString(R.string.insuffcient_quantity), Toast.LENGTH_SHORT).show()

                }else if (it.pointsBalance == 1){
                    SendOtpRequest(clickedItemData)
                    approveApiCall(clickedItemData)

                }else{
                    LoadingDialogue.dismissDialog()
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }else{
                LoadingDialogue.dismissDialog()
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })


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

        /*** Claim Approve/Reject Observer ***/
        viewModel.pendingClaimApproveRejectLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){

                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",
                        successMsg,object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                currentList.clear()
                                binding.searchFilter.text.clear()
                                callApi(1,"")
                            }

                        })

                }else if (it.returnMessage == "2"){
                    Toast.makeText(requireContext(), getString(R.string.insuffcient_quantity), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }
}