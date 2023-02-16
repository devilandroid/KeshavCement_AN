package com.loyaltyworks.keshavcement.ui.claim

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentClaimBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.TimeChangeDialog
import java.time.LocalDate


class ClaimFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentClaimBinding
    private lateinit var viewModel: PurchaseRequestViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType: CommonSpinner
    var userTypeId: Int = -1

    var _dealerSubDealerList = mutableListOf<LstCustParentChildMappingDealer>()
    var dealerSubDealerListAdapter: ArrayAdapter<String>? = null
    var dealerSubDealerId: String = "-1"
    var selectedCustomerMobile: String = ""

    var _productList = mutableListOf<LstAttributesDetailProduct>()
    var productListAdapter: ArrayAdapter<String>? = null
    var productId: String = "-1"
    var productCode: String = ""

    var plusMinusBtn = false

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 60000L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var OTP: String = ""

    var sucessMsg: String = ""

    var ActorId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        binding = FragmentClaimBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ClaimView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ClaimFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            ActorId = PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerIdSE)
        }else{
            ActorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
        }

        binding.custTypeSpinner.onItemSelectedListener = this
        binding.customerNameSpinner.onItemSelectedListener = this
        binding.productSpinner.onItemSelectedListener = this

        binding.sendOtpBtn.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)
        binding.saveProceedBtn.setOnClickListener(this)
        binding.qtyPlus.setOnClickListener(this)
        binding.qtyMinus.setOnClickListener(this)

        Log.d("efburhbfr","fjrf : " + PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerNameSE))
        userTypeSpinner()

        binding.qtyTextview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().equals("", ignoreCase = true)) return

                if (s.toString().equals("0", ignoreCase = true) || s.toString().isEmpty()) {
                    binding.qtyTextview.setText("")
                    return
                }

            }

        })

    }

    private fun userTypeSpinner() {
        userTypeList.clear()

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            userTypeList.add( CommonSpinner(name = "Select Customer Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Engineer", id = 1))
            userTypeList.add( CommonSpinner(name = "Mason", id = 2))
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            userTypeList.add( CommonSpinner(name = "Select Customer Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Engineer", id = 1))
            userTypeList.add( CommonSpinner(name = "Mason", id = 2))
            userTypeList.add( CommonSpinner(name = "Sub Dealer", id = 4))
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){

            if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerNameSE).equals("Dealer",true)){
                userTypeList.add( CommonSpinner(name = "Select Customer Type", id = -1))
                userTypeList.add( CommonSpinner(name = "Engineer", id = 1))
                userTypeList.add( CommonSpinner(name = "Mason", id = 2))
                userTypeList.add( CommonSpinner(name = "Sub Dealer", id = 4))
            }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerNameSE).equals("Sub Dealer",true)){
                userTypeList.add( CommonSpinner(name = "Select Customer Type", id = -1))
                userTypeList.add( CommonSpinner(name = "Engineer", id = 1))
                userTypeList.add( CommonSpinner(name = "Mason", id = 2))
            }

        }


        binding.custTypeSpinner.adapter = SpinnerCommonWhiteTextAdapter(requireActivity(), R.layout.spinner_popup_row,userTypeList)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.qty_plus ->{
                plusMinusBtn = true
                var currentCount: Int

                if (binding.qtyTextview.text.toString().isEmpty()){
                    currentCount = 0
                }else{
                    currentCount = binding.qtyTextview.getText().toString().toInt()
                }
                ++currentCount

                binding.qtyTextview.setText(currentCount.toString())
            }

            R.id.qty_minus ->{
                plusMinusBtn = true

                if (binding.qtyTextview.text.toString().isEmpty() || binding.qtyTextview.text.toString().toInt() == 1)
                    return
                var currCountMin: Int = binding.qtyTextview.text.toString().toInt()
                --currCountMin

                binding.qtyTextview.setText(currCountMin.toString())
            }

            R.id.resend_otp ->{
                if (BlockMultipleClick.click())return
                SendOTPRequest()
            }

            R.id.send_otp_btn ->{
                if (BlockMultipleClick.click())return

                if(Settings.Global.getInt(requireActivity().contentResolver, Settings.Global.AUTO_TIME) == 1) {

                    if (userTypeId == -1){
                        Toast.makeText(requireContext(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show()
                        return
                    }else if (dealerSubDealerId == "-1"){
                        Toast.makeText(requireContext(), getString(R.string.please_select_name), Toast.LENGTH_SHORT).show()
                        return
                    }else if (productId == "-1"){
                        Toast.makeText(requireContext(), getString(R.string.please_select_product), Toast.LENGTH_SHORT).show()
                        return
                    }else if (binding.qtyTextview.text.toString().isNullOrEmpty()){
                        Toast.makeText(requireContext(), getString(R.string.please_enter_quantity), Toast.LENGTH_SHORT).show()
                        return
                    }else if (binding.qtyTextview.text.toString().equals("0")){
                        Toast.makeText(requireContext(), getString(R.string.quantity_should_be_greater_than_0), Toast.LENGTH_SHORT).show()
                        return
                    }else if (binding.sendOtpBtn.text == "Send OTP"){
                        if (::timers.isInitialized){
                            timers.cancel()
                        }
                        SendOTPRequest()


                    }else{
                        if (binding.otpViewClaim.otp.toString().isNullOrEmpty()) {
                            Toast.makeText(requireContext(),"Please enter OTP", Toast.LENGTH_SHORT).show()
                        }else if (binding.otpViewClaim.otp.toString().length == 6 && binding.otpViewClaim.otp.toString() == OTP){
                            timers.cancel()
                            sucessMsg = "Claim is successfully completed."
                            submitClaimApi("1")

                        }else{
                            Toast.makeText(requireContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    // Disabed
                    Log.d("jhryfgry","changed")
                    TimeChangeDialog.showTimeChangeDialog(requireContext(), object: TimeChangeDialog.TimeChangeDialogCallBack{
                        override fun onOk() {
                            startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
                        }

                    })
                }


            }

            R.id.save_proceed_btn ->{
                if (BlockMultipleClick.click())return

                if(Settings.Global.getInt(requireActivity().contentResolver, Settings.Global.AUTO_TIME) == 1) {
                    if (userTypeId == -1){
                        Toast.makeText(requireContext(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show()
                        return
                    }else if (dealerSubDealerId == "-1"){
                        Toast.makeText(requireContext(), getString(R.string.please_select_name), Toast.LENGTH_SHORT).show()
                        return
                    }else if (productId == "-1"){
                        Toast.makeText(requireContext(), getString(R.string.please_select_product), Toast.LENGTH_SHORT).show()
                        return
                    }else if (binding.qtyTextview.text.toString().isNullOrEmpty()){
                        Toast.makeText(requireContext(), getString(R.string.please_enter_quantity), Toast.LENGTH_SHORT).show()
                        return
                    }else if (binding.qtyTextview.text.toString().equals("0")){
                        Toast.makeText(requireContext(), getString(R.string.quantity_should_be_greater_than_0), Toast.LENGTH_SHORT).show()
                        return
                    }else{
                            sucessMsg = "Saved. Check pending claims."
                            submitClaimApi("-1")
                    }
                }else {
                    // Disabed
                    Log.d("jhryfgry","changed")
                    TimeChangeDialog.showTimeChangeDialog(requireContext(), object: TimeChangeDialog.TimeChangeDialogCallBack{
                        override fun onOk() {
                            startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
                        }

                    })
                }

            }
        }
    }

    private fun SendOTPRequest() {
        loginViewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                userName = "",
                userId = "-1",
                mobileNo = selectedCustomerMobile,
                oTPType = "Enrollment"
            )
        )

    }

    @SuppressLint("NewApi")
    private fun submitClaimApi(status: String) {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getPurchaseSubmitData(
            SubmitPurchaseRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                ritailerId = dealerSubDealerId,
                sourceDevice = 1,
                tranDate = LocalDate.now().toString(),
                approvalStatus = status,
                productSaveDetailList = listOf(
                    ProductSaveDetail(
                        productCode = productCode,
                        quantity = binding.qtyTextview.text.toString()
                    )
                )

            )
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.cust_type_spinner -> {
                mSelecteduserType = parent.getItemAtPosition(position) as CommonSpinner
                userTypeId = mSelecteduserType.id!!
                Log.d("fdsfsdf", mSelecteduserType.name!!)

                if (userTypeId > 0){
                    /*** Dealer Sub-Dealer Api call ***/
                    viewModel.getDealerSubDealerListData(
                        DealerSubDealerListRequest(
                            actionType = 16,
                            actorId = ActorId,
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

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }

            }

            R.id.customer_name_spinner ->{
                dealerSubDealerId = _dealerSubDealerList[position].userID.toString()
                selectedCustomerMobile = _dealerSubDealerList[position].mobile.toString()
                Log.d("bhbrfhrb","dealer Sub-Dealer ID : " + dealerSubDealerId)

                if (dealerSubDealerId != "-1"){
                    /*** Product List Api call ***/
                    viewModel.getProductDropdownData(
                        ProductDropdownRequest(
                            actionType = "105"
                        )
                    )

                }else{
                    val productListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListNames)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter
                }
            }

            R.id.product_spinner ->{
                productId = _productList[position].attributeId.toString()
                productCode = _productList[position].attributeContents.toString()
                Log.d("bhbrfhrb","product ID : " + productId)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  OTP Observer  ***/
        loginViewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue!! > 0) {

                    binding.saveProceedBtn.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE


                    binding.custTypeSpinner.isEnabled = false
                    binding.customerNameSpinner.isEnabled = false
                    binding.productSpinner.isEnabled = false
                    binding.qtyPlus.isEnabled = false
                    binding.qtyMinus.isEnabled = false
                    binding.qtyTextview.isEnabled = false
                    binding.productSpinner.isEnabled = false

                    binding.otpSentNumber.text = "OTP will receive at " + selectedCustomerMobile

                    OTP = it.returnMessage!!
                    startTimer(time_in_milli_seconds)
                    binding.sendOtpBtn.text = "Submit"



                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }

        })

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

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerSubDealerListName)
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

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }
            }

        })

        /*** Product List Observer ***/
        viewModel.productDropdownLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()){
                    val productLists: MutableList<LstAttributesDetailProduct> = it.lstAttributesDetails!!.toMutableList()
                    _productList = productLists

                    val productListName = ArrayList<String>()

                    for (commonSpinner in productLists) {
                        productListName.add(commonSpinner.attributeValue!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListName)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter



                }else{
                    val productListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListNames)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter
                }
            }

        })

        /*** Submit Purchase Request Observer ***/
        viewModel.purchaseSubmitLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,
                        "Successfully!",sucessMsg, object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                findNavController().popBackStack()
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


    private fun startTimer(time_in_seconds: Long) {
        if (binding.timer.text.isNotEmpty()) {
            binding.timer.text = ""
        }
        timers = object : CountDownTimer(START_MILLI_SECONDS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                try {
                    binding.timer.visibility = View.VISIBLE
                    binding.resendOtp.visibility = View.GONE
                    binding.timer.text = "00 : " + (millisUntilFinished / 1000).toString()
                } catch (e: Exception) {
                }
            }

            override fun onFinish() {
                try {
                    binding.timer.text = "00.00"
                    binding.timer.visibility = View.GONE
                    binding.resendOtp.visibility = View.VISIBLE
                } catch (e: Exception) {
                }


            }

        }

        timers.start()

    }


}