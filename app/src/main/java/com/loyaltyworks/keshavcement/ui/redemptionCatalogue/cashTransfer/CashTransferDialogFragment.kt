package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CashTransferDialogFragment : DialogFragment() , View.OnClickListener, AdapterView.OnItemSelectedListener{
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
            binding.userLayout.visibility = View.VISIBLE
        }else{
            binding.userLayout.visibility = View.GONE
            binding.otpLayout.visibility = View.VISIBLE
            binding.otpSentNumber.text = "OTP will receive at " + PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile
            SendOTPRequest()
        }

        binding.userTypeSpinner.onItemSelectedListener = this
        binding.customerNameSpinner.onItemSelectedListener = this

        binding.nextBtn.setOnClickListener(this)
        binding.redeemBtn.setOnClickListener(this)
        binding.closeBtn.setOnClickListener(this)
        binding.okBtn.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)

        userTypeSpinner()

    }

    private fun userTypeSpinner() {
        userTypeList.clear()

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            userTypeList.add( CommonSpinner(name = "Select User Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){

            userTypeList.add( CommonSpinner(name = "Select User Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))
            userTypeList.add( CommonSpinner(name = "Sub Dealer", id = 4))

        }

        binding.userTypeSpinner.adapter = SpinnerCommonAdapter(requireActivity(), R.layout.spinner_popup_row,userTypeList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(100)

        /*** Dealer Sub-Dealer List Observer ***/
        viewModel.dealerSubDealerListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstCustParentChildMapping.isNullOrEmpty()){
                    val dealerSubDealerLists: MutableList<LstCustParentChildMappingDealer> = it.lstCustParentChildMapping!!.toMutableList()
                    _dealerSubDealerList = dealerSubDealerLists

                    val dealerSubDealerListName = ArrayList<String>()

                    for (commonSpinner in dealerSubDealerLists) {
                        dealerSubDealerListName.add(commonSpinner.firstName!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerSubDealerListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dealerSubDealerListName)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter


                }else{
                    val dealerListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }
            }

        })

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
                if (message.split("-").toTypedArray()[1].toInt() > 0)  {
                    LoadingDialogue.dismissDialog()
                    binding.userLayout.visibility = View.GONE
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
            R.id.next_btn ->{
                if (BlockMultipleClick.click()) return

                if (userTypeId == -1){
                    Toast.makeText(requireContext(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show()
                    return
                }else if (dealerSubDealerId == "-1"){
                    Toast.makeText(requireContext(), getString(R.string.please_select_name), Toast.LENGTH_SHORT).show()
                    return
                }else{
                    binding.userLayout.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE
                    binding.otpSentNumber.text = "OTP will receive at " + PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile
                    SendOTPRequest()
                }

            }

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

        catalogue.catalogueId = objCataloguee.catogoryId
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
        catalogue.redemptionTypeId = 1
        catalogue.status = 0
        catalogue.customerCartId = objCataloguee.customerCartId
        catalogue.catogoryId = objCataloguee.categoryID
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
                name = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName
            )
        )

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.user_type_spinner ->{
                mSelecteduserType = parent.getItemAtPosition(position) as CommonSpinner
                userTypeId = mSelecteduserType.id!!
                Log.d("fdsfsdf", mSelecteduserType.name!!)

                if (userTypeId > 0){
                    /*** Dealer Sub-Dealer Api call ***/
                    viewModel.getDealerSubDealerListData(
                        DealerSubDealerListRequest(
                            actionType = 16,
                            actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                            searchText = userTypeId.toString()
                        )
                    )

                }else{
                    val dealerListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }
            }

            R.id.customer_name_spinner ->{
                dealerSubDealerId = _dealerSubDealerList[position].loyaltyID.toString()
                Log.d("bhbrfhrb","dealer Sub-Dealer ID : " + dealerSubDealerId)

            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

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