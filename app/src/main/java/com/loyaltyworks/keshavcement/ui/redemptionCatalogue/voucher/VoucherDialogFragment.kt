package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.voucher


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.VoucherPointsAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog
import kotlinx.android.synthetic.main.fragment_voucher_dialog_list_dialog.*
import java.util.ArrayList

class VoucherDialogFragment : BottomSheetDialogFragment() {
    var defaultData = arrayOf("0")
    var objCatalogueFixedPoint: ObjCatalogueFixedPoint? = null

    private lateinit var viewModel: EVoucherViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    private lateinit var objCatalogueList: ObjCatalogueListtt

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""
    private lateinit var OTPNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EVoucherViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        return inflater.inflate(R.layout.fragment_voucher_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_eVouchersDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_eVouchersDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        val bundle1 = this.arguments
        if (bundle1 != null) {
            objCatalogueList = arguments?.getSerializable("VoucherData") as ObjCatalogueListtt
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = requireArguments().getString("SelectedCustomerPartyLoyaltyID").toString()

            try {

                Glide.with(requireContext()).asBitmap()
                    .error(R.drawable.ic_baseline_photo_size_select_actual_24)
                    .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24).load(
                        objCatalogueList.productImage
                    )
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .optionalFitCenter().into(voucher_imgs)

                voucher_imgs.setPadding(0, 0, 0, 0)

            } catch (e: Exception) {
            }


            detail_voucher_name.text = objCatalogueList.productName
            category.text = objCatalogueList.catogoryName
            amount_range.text = getString(R.string.amount_range) +" INR " + objCatalogueList.min_points.toString() + "-" +  objCatalogueList.max_points.toString()
            detailDesc_tv.text = objCatalogueList.productDesc
            detailtc_tv.text = objCatalogueList.termsCondition

        }


        if (objCatalogueList.product_type == 1) {

            amount_range.text = "Redeemable Range ${objCatalogueList.min_points.toString()} - ${objCatalogueList.max_points.toString()}"
            mng_amount_fld.visibility = View.VISIBLE
            mng_price_spinner.visibility = View.GONE
            mng_price_spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, defaultData)
            mng_redeem_btn.visibility = View.VISIBLE

        }else {

            amount_range.visibility = View.INVISIBLE
            mng_amount_fld.setText("")
            mng_amount_fld.visibility = View.GONE

            if (!objCatalogueList.objCatalogueFixedPoints.isNullOrEmpty()) {
                mng_price_spinner.adapter = VoucherPointsAdapter(requireContext(), objCatalogueList.objCatalogueFixedPoints!!)
            } else mng_price_spinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_row_small_size, defaultData)

            mng_price_spinner.visibility = View.VISIBLE

        }

        mng_price_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                objCatalogueFixedPoint = parent!!.getItemAtPosition(position) as ObjCatalogueFixedPoint
                Log.d("fjdskf", objCatalogueFixedPoint!!.fixedPoints.toString())
            }
        }

        mng_redeem_btn.setOnClickListener {

            if (objCatalogueFixedPoint?.fixedPoints != null || !TextUtils.isEmpty(mng_amount_fld.text.toString())) {

                if (objCatalogueList.product_type == 0) {
                    if (objCatalogueFixedPoint!!.fixedPoints!! <= PreferenceHelper.getDashboardDetails(requireContext())!!.objCustomerDashboardList!![0].overAllPoints!!){
                        RedeemVoucher(objCatalogueFixedPoint!!.fixedPoints.toString())
                    }else{
                        (activity as DashboardActivity).snackBar(getString(R.string.dont_have_suffiecient_redeemable_point_balance), R.color.red)
                    }
                    return@setOnClickListener
                }

                if (mng_amount_fld.text.toString().toLong() <= PreferenceHelper.getDashboardDetails(requireContext())!!.objCustomerDashboardList!![0].overAllPoints.toString().toLong()) {

                    if (mng_amount_fld.text.toString().toLong() >= objCatalogueList.min_points.toString().toLong()) {
                        RedeemVoucher(mng_amount_fld.text.toString())

                    } else {
                        mng_amount_fld.error = getString(R.string.enter_amount_in_range)
                        mng_amount_fld.requestFocus()
//                        (activity as MainActivity).snackBar(
//                            "Enter redeemable amount in range",
//                            R.color.red
//                        )
                    }
                } else {
                    mng_amount_fld.error =
                        getString(R.string.dont_have_suffiecient_redeemable_point_balance)
                    mng_amount_fld.requestFocus()

//                    (activity as MainActivity).snackBar(
//                        "You do not have sufficient point balance to redeem voucher",
//                        R.color.red
//                    )
                }
            } else {
                mng_amount_fld.error = getString(R.string.enter_redeemable_amount)
                mng_amount_fld.requestFocus()
//                (activity as MainActivity).snackBar("Enter redeemable amount", R.color.red)
            }

        }


    }

    private fun RedeemVoucher(amountss: String) {
        SendOtpRequest()
        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.redemption),getString(R.string.enter_otp_to_complete_the_redemption),PreferenceHelper.getStringValue(requireContext(), BuildConfig.SelectedCustomerMobile)
            ,"Redeem",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                override fun onOk() {

                }

                override fun onRedeemClick(otp: String) {
                    if(otp == "123456" /*OTPNumber*/){
                        RedeemOTPDialog.hideDialog()
                        SubmitReddemProcess(amountss)
                    }else{
                        Toast.makeText(requireContext(),getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
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

    private fun SubmitReddemProcess(amountss: String) {

        val objCatalogueLists: ArrayList<ObjCatalogueListss> =
            ArrayList<ObjCatalogueListss>()
        val objCatalogueListss = ObjCatalogueListss();

        objCatalogueListss.CatalogueId = objCatalogueList.catalogueId
        objCatalogueListss.CountryCurrencyCode = ""
        objCatalogueListss.Address1 = ""
        objCatalogueListss.DeliveryType = objCatalogueList.deliveryType
        objCatalogueListss.HasPartialPayment = objCatalogueList.hasPartialPayment
        objCatalogueListss.NoOfPointsDebit =amountss.toInt()
        objCatalogueListss.PointsRequired = objCatalogueList.pointsRequired!!.toFloat()
        objCatalogueListss.ProductCode = objCatalogueList.productCode
        objCatalogueListss.ProductImage = objCatalogueList.productImage
        objCatalogueListss.ProductName = objCatalogueList.productName
//                                    objCatalogueListss.RedemptionDate =
//                                            SimpleDateFormat("yyyy-MM-dd").format(
//                                                    Date()
//                                            )
        objCatalogueListss.RedemptionId = objCatalogueList.redemptionId
        objCatalogueListss.RedemptionTypeId =
            objCatalogueList.redemptionTypeId
        objCatalogueListss.Status = 0
        objCatalogueListss.VendorId = objCatalogueList.vendorId
        objCatalogueListss.VendorName = objCatalogueList.vendorName

        objCatalogueLists.add(objCatalogueListss)

        LoadingDialogue.showDialog(requireContext())

        viewModel.setRedeemGiftVoucherRequest(
            RedeemGiftVoucherRequest(
                ActionType = "51",
                ActorId = actorID,
                CountryID = objCatalogueList.countryID.toString(),
                MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi?.get(0)?.merchantId.toString(),
                ReceiverEmail ="",
                ReceiverName = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName),
                ReceiverMobile = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile),
                SourceMode = "6",
                ObjCatalogueList = objCatalogueLists
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

        /*** Voucher Redeem Observer ***/
        viewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it?.returnMessage != null) {
                val statusCode = it.returnMessage.toString()
                try {
                    if (statusCode == null || statusCode == "") {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Redemption Failed!"
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }
                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "00") {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Member is de-activated!"
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }

                    if (statusCode != "-1" && statusCode.split("-")
                            .toTypedArray()[0] == "-1~Cannot insert"
                    ) {

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",getString(R.string.something_went_wrong_please_try_again_later)
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }
                            })

                    }

                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "000") {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",
                            "Unfortunately your redemption has not been completed. Your points redeemed will be reinstated shortly. Please try again later once"
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }
                            })
                    }

                    if (statusCode.split("-").toTypedArray()[1].toInt() > 0) {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","You have redeemed your product"
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    requireActivity().finish();
                                    requireActivity().overridePendingTransition( 0, 0);
                                    startActivity(requireActivity().intent);
                                    requireActivity().overridePendingTransition( 0, 0);
                                }

                            })


                    } else {
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops","Redemption Failed!"
                            ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }
                } catch (exception: java.lang.Exception) {
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Oops",getString(R.string.something_went_wrong_please_try_again_later)
                        ,object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
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
}