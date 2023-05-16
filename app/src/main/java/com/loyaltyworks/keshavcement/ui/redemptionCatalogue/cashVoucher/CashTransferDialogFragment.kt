package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashVoucher

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferDialogBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonAdapter
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CashTransferDialogFragment : DialogFragment() , View.OnClickListener{
    private lateinit var binding: FragmentCashTransferDialogBinding
    private lateinit var viewModel: PurchaseRequestViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel
    private lateinit var viewModelProductCataloge: ProductCatalogueViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType: CommonSpinner
    var userTypeId: Int = -1

    var _dealerSubDealerList = mutableListOf<LstCustParentChildMappingDealer>()
    var dealerSubDealerListAdapter: ArrayAdapter<String>? = null
    var dealerSubDealerId: String = ""

    private lateinit var OTPNumber: String
    var START_MILLI_SECONDS = 60000L
    var time_in_milli_seconds = 0L
    lateinit var timers: CountDownTimer

    private lateinit var objCataloguee: ObjCataloguee
    val catalogueList : MutableList<ObjCatalogueList> = ArrayList()
    lateinit var  _lstCustomerJson: List<LstCustomerJson>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        viewModelProductCataloge = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentCashTransferDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            objCataloguee = arguments?.getSerializable("CashVoucherData") as ObjCataloguee
            _lstCustomerJson = arguments?.getSerializable("CustomerAddress") as List<LstCustomerJson>
        }


        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) != BuildConfig.Dealer){

            binding.otpLayoutTitle.text = getString(R.string.cash_transfer)
            binding.otpLayout.visibility = View.VISIBLE
            binding.otpSentNumber.text = "OTP will receive at " + PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile
            SendOTPRequest()
        }else{
//            binding.userLayoutTitle.text = getString(R.string.cash_transfer)
            binding.otpLayoutTitle.text = getString(R.string.cash_voucher)
            binding.otpLayout.visibility = View.VISIBLE
            binding.otpSentNumber.text = "OTP will receive at " + PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile
            SendOTPRequest()
        }

        binding.redeemBtn.setOnClickListener(this)
        binding.closeBtn.setOnClickListener(this)
        binding.okBtn.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(100)


        /***  OTP observer ***/
        loginViewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null) {
                if (!it.returnMessage.isNullOrEmpty()) {
                    OTPNumber = it.returnMessage.toString()
                    startTimer(time_in_milli_seconds)
                    Log.d("otp", it.returnMessage.toString())

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

        /*** Redeem CashVoucher/CashTransfer Observer ***/
        viewModelProductCataloge.saveCatalogueRedeemData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty())  {

                val message = it.returnMessage.toString()
                Log.d("hrfijhrof","jrhfiju "+ message)

                if (message != "-1" && message.split("-").toTypedArray()[1] == "00") {
                    Toast.makeText(requireContext(), getString(R.string.your_account_has_been_deactivated), Toast.LENGTH_SHORT).show()

                }

                if (message.contains("~")){
                    if (message.split("~")[0] == "-1"){
                        Toast.makeText(requireContext(), getString(R.string.you_are_not_eligible_to_redeem), Toast.LENGTH_SHORT).show()
                    }
                        if (::timers.isInitialized){
                            timers.cancel()
                        }
                        findNavController().popBackStack()

                }else if (message.split("-").toTypedArray()[1].toInt() > 0)  {
                    LoadingDialogue.dismissDialog()
                    binding.otpLayout.visibility = View.GONE
                    binding.closeBtn.visibility = View.GONE
                    binding.successLayout.visibility = View.VISIBLE


                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.close_btn ->{
                if (::timers.isInitialized){
                    timers.cancel()
                }
                findNavController().popBackStack()
            }

            R.id.ok_btn ->{
                if (::timers.isInitialized){
                    timers.cancel()
                }
                requireActivity().finish();
                requireActivity().overridePendingTransition( 0, 0);
                startActivity(requireActivity().intent);
                requireActivity().overridePendingTransition( 0, 0);
            }

            R.id.resend_otp ->{
                SendOTPRequest()
            }

            R.id.redeem_btn ->{
                if (BlockMultipleClick.click()) return

                if (binding.otpViewCash.otp.isNullOrEmpty()){
                    Toast.makeText(requireContext(),getString(R.string.enter_otp),Toast.LENGTH_SHORT).show()
                }else if (binding.otpViewCash.otp != OTPNumber){
                    Toast.makeText(requireContext(),getString(R.string.invalid_otp),Toast.LENGTH_SHORT).show()
                }else if (binding.otpViewCash.otp == OTPNumber){
                    redeemProcess()
                }
            }
        }
    }

    private fun redeemProcess() {
        val catalogue = ObjCatalogueList()

        catalogue.catalogueId = objCataloguee.catalogueId
        catalogue.deliveryType = "In Store"
        catalogue.hasPartialPayment = false
        catalogue.noOfPointsDebit = objCataloguee.pointsRequired
        catalogue.noOfQuantity = objCataloguee.noOfQuantity
        catalogue.pointsRequired = objCataloguee.pointsRequired
        catalogue.productCode = objCataloguee.productCode
        catalogue.productImage = objCataloguee.productImage
        catalogue.productName = objCataloguee.productName
        catalogue.redemptionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        catalogue.redemptionId = 0
        catalogue.redemptionRefno = objCataloguee.redemptionRefno
        catalogue.redemptionTypeId = 9
        catalogue.status = 0
        catalogue.customerCartId = objCataloguee.customerCartId
        catalogue.catogoryId = objCataloguee.catogoryId
        catalogue.termsCondition = objCataloguee.termsCondition
        catalogue.totalCash = objCataloguee.totalCash
        catalogue.vendorId = objCataloguee.vendorId
        catalogue.vendorName = objCataloguee.vendorName
        catalogueList.add(catalogue)

        LoadingDialogue.showDialog(requireContext())

        viewModelProductCataloge.getSaveCatalogueRedeem(SaveCatalogueRedemptionRequest(
            actionType = 51,
            actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
            memberName = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName.toString(),
            objCatalogueList = catalogueList,
            dealerLoyaltyId = dealerSubDealerId,
            objCustShippingAddressDetails = ObjCustShippingAddressDetails(
                address1 =_lstCustomerJson[0].address1,
                cityId =_lstCustomerJson[0].districtId,
                cityName =_lstCustomerJson[0].districtName,
                countryId =_lstCustomerJson[0].countryId,
                stateId =_lstCustomerJson[0].stateId,
                stateName =_lstCustomerJson[0].stateName,
                zip = _lstCustomerJson[0].zip,
                email = _lstCustomerJson[0].email,
                fullName = _lstCustomerJson[0].firstName,
                mobile = _lstCustomerJson[0].mobile,
            ),
            sourceMode = 6

        ))
    }

    private fun SendOTPRequest() {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile,
                userId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                userName = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId,
                name = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName,
                oTPType = "OTPForRewardCardsENCashAuthorization"
            )
        )

    }



    private fun startTimer(time_in_seconds: Long) {
        if (binding.timer.text.isNotEmpty()) {
            binding.timer.text = ""
        }
        timers = object : CountDownTimer(START_MILLI_SECONDS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.visibility = View.VISIBLE
                binding.resendOtp.visibility = View.GONE
                binding.timer.text = (millisUntilFinished / 1000).toString()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding.timer.text = "00.00"
                binding.timer.visibility = View.GONE
                binding.resendOtp.visibility = View.VISIBLE
            }

        }

        timers.start()

    }


}