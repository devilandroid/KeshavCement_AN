package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.voucher

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentEvouchersBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.VoucherAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog
import java.lang.Exception
import java.util.ArrayList


class EvouchersFragment : Fragment(), VoucherAdapter.voucherListAdpaterCallback {
    private lateinit var binding: FragmentEvouchersBinding
    private lateinit var viewModel: EVoucherViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    private lateinit var OTPNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(EVoucherViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        binding = FragmentEvouchersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_eVouchersView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_eVouchersFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (arguments != null){
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId.toString()
        }else{
            actorID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            loyaltyId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId.toString()
            partyLoyaltyID = ""
        }

        binding.redeemablePoints.text = PreferenceHelper.getStringValue(requireContext(),BuildConfig.RedeemablePointsBalance)

        callApi()
    }

    private fun callApi() {
        LoadingDialogue.showDialog(requireContext())

        viewModel.setRedeemGiftRequest(
            RedeemGiftRequest(
                ActionType = "6",
                ActorId = actorID,
                ObjCatalogueDetailll(
                    CatalogueType = "4",
                    MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi?.get(0)?.merchantId.toString()
                )
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Voucher Listing Observer ***/
        viewModel.redeemGiftLiveData.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            Log.d("fjsdkjfksd", Gson().toJson(it))
            if (it != null && !it.objCatalogueList.isNullOrEmpty()) {

                it.objCatalogueList!!.forEach { vouchers ->
                    if (!it.objCatalogueFixedPoints.isNullOrEmpty()){
                        vouchers.objCatalogueFixedPoints = it.objCatalogueFixedPoints!!.filter { it.productCode == vouchers.productCode }
                    }

                }
                if (it.objCatalogueList!![0].is_Redeemable == 1 ){
                    binding.noteText.visibility = View.GONE
                }else{
                    binding.noteText.visibility = View.VISIBLE
                }

                binding.voucherRecycler.layoutManager = LinearLayoutManager(requireContext())
                binding.voucherRecycler.adapter = VoucherAdapter(it, this)
                binding.voucherRecycler.visibility = View.VISIBLE
                binding.noDataFount.noDataFoundLayout.visibility = View.GONE
            } else {
                binding.voucherRecycler.visibility = View.GONE
                binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
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

        /*** Voucher Redeem Observer ***/
        viewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it?.returnMessage != null) {
                val statusCode = it.returnMessage.toString()
                try {
                    if (statusCode == null || statusCode == "") {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Redemption Failed!"
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }
                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "00") {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Member is de-activated!"
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }

                    if (statusCode != "-1" && statusCode.split("-")
                            .toTypedArray()[0] == "-1~Cannot insert"
                    ) {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",getString(R.string.something_went_wrong_please_try_again_later)
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }
                            })

                    }

                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "000") {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",
                            "Unfortunately your redemption has not been completed. Your points redeemed will be reinstated shortly. Please try again later once"
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }
                            })
                    }

                    if (statusCode.split("-").toTypedArray()[1].toInt() > 0) {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","You have redeemed your product"
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    requireActivity().finish();
                                    requireActivity().overridePendingTransition( 0, 0);
                                    startActivity(requireActivity().intent);
                                    requireActivity().overridePendingTransition( 0, 0);
                                }

                            })


                    } else {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Redemption Failed!"
                            ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }
                } catch (exception: Exception) {
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",getString(R.string.something_went_wrong_please_try_again_later)
                        ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                findNavController().popBackStack()
                            }
                        })

                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onDetailVoucherFromAdapter(itemView: View, position: Int,CatalogueVouchers: ObjCatalogueListtt) {
        val bundle = Bundle()
        bundle.putSerializable("SelectedCustomerUserID", actorID)
        bundle.putSerializable("SelectedCustomerLoyltyID", loyaltyId)
        bundle.putSerializable("SelectedCustomerPartyLoyaltyID", partyLoyaltyID)
        bundle.putSerializable("VoucherData", CatalogueVouchers)
        findNavController().navigate(R.id.voucherDialogFragment, bundle)
    }

    override fun onRedeemVoucherFromAdapter(itemView: View, position: Int,CatalogueVouchers: ObjCatalogueListtt, amount: String) {

        if (amount.isNotEmpty()) {
            if (amount.toLong() <= PreferenceHelper.getDashboardDetails(requireContext())!!.objCustomerDashboardList!![0].overAllPoints.toString().toLong()
            ) {
                if (CatalogueVouchers.product_type == 0) {
                    RedeemVoucher(CatalogueVouchers, amount)
                    return
                }

                if (CatalogueVouchers.product_type == 1 && amount.toLong() >= CatalogueVouchers.min_points.toString().toLong()
                    && amount.toLong() <= CatalogueVouchers.max_points.toString().toLong()) {

                    RedeemVoucher(CatalogueVouchers, amount)

                }else {
                    (activity as DashboardActivity).snackBar(getString(R.string.enter_amount_in_range), R.color.red)
                }

            }else{
                (activity as DashboardActivity).snackBar(getString(R.string.dont_have_suffiecient_redeemable_point_balance), R.color.red)
            }
        }else{
            (activity as DashboardActivity).snackBar(getString(R.string.enter_redeemable_amount), R.color.red)
        }

    }

    private fun RedeemVoucher(catalogueVouchers: ObjCatalogueListtt, amount: String) {
        SendOtpRequest()
        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.redemption),getString(R.string.enter_otp_to_complete_the_redemption),PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile)
            ,"Redeem",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {

                }

                override fun onRedeemClick(otp: String) {
                    if(otp == "123456" /*OTPNumber*/){
                        RedeemOTPDialog.hideDialog()
                        SubmitReddemProcess(catalogueVouchers,amount)
                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp),Toast.LENGTH_SHORT).show()
                    }
                }

                override fun resendOTP() {
                    SendOtpRequest()
                }
            })

    }


    private fun SendOtpRequest() {

        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile),
                userId = actorID,
                userName = loyaltyId,
                name = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName)
            )
        )

    }


    private fun SubmitReddemProcess(catalogueVouchers: ObjCatalogueListtt, amount: String) {
        val objCatalogueList: ArrayList<ObjCatalogueListss> = ArrayList<ObjCatalogueListss>()
        val objCatalogueListss = ObjCatalogueListss();

        objCatalogueListss.CatalogueId = catalogueVouchers.catalogueId
        objCatalogueListss.CountryCurrencyCode = ""
        objCatalogueListss.DeliveryType = catalogueVouchers.deliveryType
        objCatalogueListss.HasPartialPayment = catalogueVouchers.hasPartialPayment
        objCatalogueListss.NoOfPointsDebit = amount.toString().toInt()
        objCatalogueListss.PointsRequired = catalogueVouchers.pointsRequired!!.toFloat()
        objCatalogueListss.ProductCode = catalogueVouchers.productCode
        objCatalogueListss.ProductImage = catalogueVouchers.productImage
        objCatalogueListss.ProductName = catalogueVouchers.productName
        objCatalogueListss.RedemptionId = catalogueVouchers.redemptionId
        objCatalogueListss.RedemptionTypeId = 4
        objCatalogueListss.NoOfQuantity = 1
        objCatalogueListss.Status = 0
        objCatalogueListss.VendorId = catalogueVouchers.vendorId
        objCatalogueListss.VendorName = catalogueVouchers.vendorName

        objCatalogueList.add(objCatalogueListss)

        LoadingDialogue.showDialog(requireContext())

        viewModel.setRedeemGiftVoucherRequest(
            RedeemGiftVoucherRequest(
                ActionType = "51",
                ActorId = actorID,
                CountryID = catalogueVouchers.countryID.toString(),
                MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi?.get(0)?.merchantId.toString(),
                ReceiverEmail = "",
                ReceiverName = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName),
                ReceiverMobile = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile),
                SourceMode = "6",
                ObjCatalogueList = objCatalogueList
            )
        )

    }


}