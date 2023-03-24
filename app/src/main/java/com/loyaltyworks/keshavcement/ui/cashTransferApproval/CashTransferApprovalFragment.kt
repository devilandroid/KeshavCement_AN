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
    var tempCashTransferApprovalList = ArrayList<LstCustomerCashTransferedDetail>()
    var currentList = ArrayList<LstCustomerCashTransferedDetail>()
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
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CashTransferApprovalView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CashTransferApprovalFragment")
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

        viewModel.getCashTransferApprovalListData(
            CashTransferApprovalListRequest(
                actionType = 3,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                startIndex = startIndex,
                pageSize = limit,
                searchText = srchTxt,
                isTranHistory = "-1",
                status = "0"

            )
        )

    }


    private fun loadCashTransferApprovalObserver() {
        /*** Cash Transfer Approval Listing Observer ***/
        viewModel.cashTransferApprovalListLiveData.observe(viewLifecycleOwner, Observer{
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstCustomerCashTransferedDetails.isNullOrEmpty()) {
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

                    for (redemption in it.lstCustomerCashTransferedDetails){
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
        objCatalogueRedemReqList: LstCustomerCashTransferedDetail
    ) {
        SendOtpRequest(objCatalogueRedemReqList)

        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.cash_transfer),getString(R.string.enter_otp_to_complete_the_process),objCatalogueRedemReqList.customerMobile.toString()
            ,"Submit" ,object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {
                }

                override fun onRedeemClick(otp: String) {
                    if(otp =="123456" /*OTPNumber*/){
                        RedeemOTPDialog.hideDialog()
                        successMsg = getString(R.string.cash_transfer_approved)

                        /*** Call Approve Api ***/
                        approveRejectApi(objCatalogueRedemReqList,status)

                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest(objCatalogueRedemReqList)
                }
            })
    }



    override fun onRejectClickResponse(
        itemView: View,
        position: Int,
        status: String,
        objCatalogueRedemReqList: LstCustomerCashTransferedDetail
    ) {

        successMsg = getString(R.string.cash_transfer_rejected)

        /*** Call Reject Api ***/
        approveRejectApi(objCatalogueRedemReqList,status)

    }


    private fun approveRejectApi(objCatalogueRedemReqList: LstCustomerCashTransferedDetail, status: String) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCashTransferApproveReject(
            CashTransferApproveRejectRequest(
                actionType = 4,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                loyaltyId = objCatalogueRedemReqList.loyaltyId,
                partyLoyaltyId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userName.toString(),
                remarks = objCatalogueRedemReqList.enteredRemarks,
                status = status,
                cashTranId = objCatalogueRedemReqList.cashTransferId
            )
        )

    }

    private fun SendOtpRequest(objCatalogueRedemReqList: LstCustomerCashTransferedDetail) {
        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = objCatalogueRedemReqList.customerMobile,
                userId = "-1",
                userName = objCatalogueRedemReqList.loyaltyId,
                name = objCatalogueRedemReqList.customerName
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
            if (it != null){
                if (it.returnValue!! > 0){
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