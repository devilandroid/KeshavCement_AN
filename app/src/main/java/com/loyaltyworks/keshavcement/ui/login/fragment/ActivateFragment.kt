package com.loyaltyworks.keshavcement.ui.login.fragment

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentActivateBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.StateAdapter
import com.loyaltyworks.keshavcement.ui.CommonViewModel
import com.loyaltyworks.keshavcement.ui.login.LoginActivity
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.Keyboard
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RegisterSuccessDialog
import java.time.LocalDate
import java.time.Period


class ActivateFragment : Fragment(), View.OnClickListener{

    private lateinit var binding: FragmentActivateBinding
    private lateinit var viewModel: LoginRegistrationViewModel
    private lateinit var commonViewModel: CommonViewModel

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 60000L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var OTP: String = ""

    var _lstCustomerJson: List<LstCustomerJson>? = null
    var _lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJson>? = null

    var aadharNo: String = ""
    var gstNo: String = ""
    var sapCode: String = ""

    var anniversaryDate = ""
    var birthdate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        binding = FragmentActivateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_ActivateView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_ActivateFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("jfkdjskf","fjkdsjfkds")
                backButtonClicked()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        initialSetup()


        binding.otpSubmitButton.setOnClickListener(this)
        binding.backLoginBtn.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)

        binding.backToLogin.setOnClickListener(this)
        binding.activateBtn.setOnClickListener(this)

        binding.birthDate.setOnClickListener(this)
        binding.anniversaryDate.setOnClickListener(this)
    }

    private fun initialSetup() {
        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) {
            binding.memberIdLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.GONE
            binding.aadharLayout.visibility = View.VISIBLE

        } else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer) {
            binding.memberIdLayout.visibility = View.VISIBLE
            binding.gstLayout.visibility = View.VISIBLE
            binding.aadharLayout.visibility = View.GONE
        } else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer) {
            binding.memberIdLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.VISIBLE
            binding.aadharLayout.visibility = View.GONE
        }
    }

    private fun backButtonClicked() {
        if (binding.otpLayout.visibility == View.GONE) {
            findNavController().popBackStack()
        }else{
//            timers.cancel()
            binding.timer.text = ""
            binding.otpMobileNumber.isEnabled = true
            OTP = ""

            binding.otpView.setOTP("")
            binding.otpMobileNumber.setText("")
            binding.otpSubmitTxt.text = "Generate OTP"

            findNavController().popBackStack()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.back_login_btn ->{
                backButtonClicked()
            }

            R.id.otp_submit_button ->{
                if (binding.otpMobileNumber.text.toString().isNullOrBlank()){
                    binding.otpMobileNumber.error = getString(R.string.enter_mobile_number)
                    binding.otpMobileNumber.requestFocus()
                }else if (binding.otpMobileNumber.text.toString().length < 10){
                    binding.otpMobileNumber.text.clear()
                    binding.otpMobileNumber.error = getString(R.string.enter_valid_mobile_no)
                    binding.otpMobileNumber.requestFocus()
                }else if (binding.otpSubmitTxt.text.toString() == "Generate OTP") {
                    if (::timers.isInitialized){
                        timers.cancel()
                    }

                    checkCustomerExistancy()

                }else{

                    if (binding.otpView.otp.toString().isNullOrEmpty()) {
                        Toast.makeText(requireContext(),"Please enter OTP", Toast.LENGTH_SHORT).show()
                    }else if (binding.otpView.otp.toString().length == 6 && binding.otpView.otp.toString() == OTP){
                        Keyboard.hideKeyboard(requireContext(),binding.mActivateHost)
                        binding.firstLayout.visibility = View.GONE
                        binding.activateFormLayout.visibility = View.VISIBLE

                        binding.mobile.setText(binding.otpMobileNumber.text.toString())
                        binding.mobile.isEnabled = false

                        setCustomerTypeName()
                        customerDataFetchApi()
                        timers.cancel()

                    }else{
                        Toast.makeText(requireContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                    }


                }
            }

            R.id.resend_otp -> {
                SendOTPRequest()
            }

            R.id.back_to_login ->{
                backButtonClicked()
            }

            R.id.birth_date ->{
                DatePickerBox.date(1, activity) {
                    val year = Integer.parseInt(it.split("/")[2])
                    val month = Integer.parseInt(it.split("/")[1])
                    val day = Integer.parseInt(it.split("/")[0])

                    if(getAge(year,month,day)<18){
                        Toast.makeText(requireContext(),"Age should not be lesser than 18 years",Toast.LENGTH_SHORT).show()
                    }else{
                        binding.birthDate.text = it.toString()
                        birthdate = it
                    }
                }
            }

            R.id.anniversary_date ->{
                DatePickerBox.date(1, activity) {
                    binding.anniversaryDate.text = it.toString()
                    anniversaryDate = it

                }
            }

            R.id.activate_btn ->{
                val email: String = binding.email.text.toString()

                if (binding.customerTypeName.text.toString().isNullOrBlank()){
//                    Toast.makeText(requireContext(), getString(R.string.customer_type_mandatory), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.customer_type_mandatory),R.color.red)

                }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer &&
                    binding.memberId.text.toString().isNullOrBlank()){
//                    Toast.makeText(requireContext(), getString(R.string.member_id_mandatory), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.member_id_mandatory),R.color.red)

                }else if (binding.name.text.toString().isNullOrBlank()){
//                    binding.name.error = getString(R.string.enter_your_name)
                    binding.name.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.enter_your_name),R.color.red)

                }else if (binding.firmName.text.toString().isNullOrBlank()){

//                    Toast.makeText(requireContext(), getString(R.string.firm_name_mandatory), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.firm_name_mandatory),R.color.red)

                }else if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    binding.email.error = requireContext().resources.getString(R.string.enter_valid_emailid)
                    binding.email.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.enter_valid_emailid),R.color.red)

                }else if (binding.mobile.text.toString().isNullOrBlank()){
//                    Toast.makeText(requireContext(), getString(R.string.mobile_number_mandatory), Toast.LENGTH_SHORT).show()
                    binding.mobile.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.mobile_number_mandatory),R.color.red)

                }else if (binding.mobile.text.toString().length < 10){
//                    Toast.makeText(requireContext(), getString(R.string.invalid_mobile_number), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.invalid_mobile_number),R.color.red)

                }/*else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer ||
                    PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) &&
                    binding.aadharNumber.text.toString().isNullOrBlank()){

                    Toast.makeText(requireContext(), getString(R.string.aadhar_number_mandatory), Toast.LENGTH_SHORT).show()

                }else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer ||
                    PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer) &&
                    binding.gstNumber.text.toString().isNullOrBlank()){

                    Toast.makeText(requireContext(), getString(R.string.gst_number_mandatory), Toast.LENGTH_SHORT).show()

                }*/else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer ||
                            PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) &&
                    !binding.aadharNumber.text.toString().isNullOrBlank() && binding.aadharNumber.text.toString().length < 12){

                    binding.aadharNumber.requestFocus()
//                    Toast.makeText(requireContext(), getString(R.string.invalid_aadhar_number), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.enter_valid_aadhar_number),R.color.red)

                }else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer ||
                            PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer) &&
                    !binding.gstNumber.text.toString().trim().isNullOrBlank() && binding.gstNumber.text.toString().trim().length < 15){

//                    binding.gstNumber.error = getString(R.string.enter_valid_gst_number)
                    (activity as LoginActivity).snackBar(getString(R.string.enter_valid_gst_number),R.color.red)
                    binding.gstNumber.requestFocus()

                }else if (binding.address.text.toString().isNullOrBlank()){
//                    binding.address.error = getString(R.string.enter_your_address)
                    binding.address.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.enter_your_address),R.color.red)

                }else if (binding.pincode.text.toString().isNullOrBlank()){
//                    binding.pincode.error = getString(R.string.enter_pin_code)
                    binding.pincode.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.enter_pin_code),R.color.red)

                }else if (!binding.pincode.text.toString().isNullOrBlank() && binding.pincode.text.toString().length < 6){
                    binding.pincode.text.clear()
//                    binding.pincode.error = getString(R.string.invalid_pin_code)
                    binding.pincode.requestFocus()
                    (activity as LoginActivity).snackBar(getString(R.string.invalid_pin_code),R.color.red)

                }else if (birthdate.isNullOrEmpty()){
//                    Toast.makeText(requireContext(), getString(R.string.select_dob), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.select_dob),R.color.red)

                }else if (_lstCustomerJson!![0].stateId == -1){
//                    Toast.makeText(requireContext(), getString(R.string.select_your_state), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.select_your_state),R.color.red)

                }else if (_lstCustomerJson!![0].districtId == -1){
                    Toast.makeText(requireContext(), getString(R.string.select_your_district), Toast.LENGTH_SHORT).show()
                    (activity as LoginActivity).snackBar(getString(R.string.select_your_district),R.color.red)

                }/*else if (_lstCustomerJson!![0].talukId == -1){
                    Toast.makeText(requireContext(), getString(R.string.select_your_taluk), Toast.LENGTH_SHORT).show()

                }*/else{
                    activateCustomer()
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(year: Int, month: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, month, dayOfMonth),
            LocalDate.now()
        ).years
    }

    private fun customerDataFetchApi() {
        viewModel.setProfileRequest(
            ProfileRequest(
                ActionType = "6",
                MerchantID = "1",
                MobileNumber = binding.otpMobileNumber.text.toString()
            )
        )
    }

    private fun activateCustomer() {

        if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) {
            aadharNo = binding.aadharNumber.text.toString()

        } else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer) {
            gstNo = binding.gstNumber.text.toString()
            sapCode = binding.memberId.text.toString()


        } else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer) {
            gstNo = binding.gstNumber.text.toString()

        }

        viewModel.activateCustomerData(
            ActivateCustomerRequest(
                actionType = "262",
                actorId = _lstCustomerJson!![0].userId.toString(),
                ObjCustomerJsonActivate(
                    address1 = binding.address.text.toString(),
                    stateId = _lstCustomerJson!![0].stateId.toString(),
                    customerId = _lstCustomerJson!![0].customerId.toString(),
                    firstName = binding.name.text.toString(),
                    mobile = _lstCustomerJson!![0].mobile,
                    zip = _lstCustomerJson!![0].zip,
                    districtId = _lstCustomerJson!![0].districtId.toString(),
                    cityId = _lstCustomerJson!![0].cityId.toString(),
                    talukId = _lstCustomerJson!![0].talukId.toString(),
                    email = binding.email.text.toString(),
                    rELATEDPROJECTTYPE = "KESHAV_CEMENT",
                    addressId = _lstCustomerJson!![0].addressId.toString(),
                    aadharNumber = aadharNo,
                    anniversary = AppController.dateAPIFormats(anniversaryDate),
                    dob = AppController.dateAPIFormats(birthdate)
                ),
                ObjCustomerOfficalInfoActivate(
                    companyName = _lstCustomerOfficalInfoJson!![0].companyName,
                    sapNo = sapCode,
                    gSTNumber = gstNo
                )
            )
        )

    }

    private fun setCustomerTypeName() {
        if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer){
            binding.customerTypeName.setText("Engineer")
        }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.customerTypeName.setText("Mason")
        }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.customerTypeName.setText("Dealer")
        }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.customerTypeName.setText("Sub Dealer")
        }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            binding.customerTypeName.setText("Support Executive")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Mobile Number Existancy Check  Observer ***/
        viewModel.mobileNumberExists.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it == 1) {
                        LoadingDialogue.dismissDialog()
                        /*** send otp for activate ***/
                        SendOTPRequest()


                    }else if (it == 3){
                        LoadingDialogue.dismissDialog()
                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.incorrect_account_type),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {
                                binding.otpMobileNumber.text.clear()
                            }
                        })
                    } else {
                        LoadingDialogue.dismissDialog()

                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.membe_doesnt_exist),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {
                                binding.otpMobileNumber.text.clear()
                            }

                        })
                    }
                } else {
                    LoadingDialogue.dismissDialog()
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }


        })

        /***  OTP Observer  ***/
        viewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue!! > 0) {

                    binding.spinnerLayout.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE

                    binding.otpMobileNumber.isEnabled = false
                    binding.otpSentNumber.text = "OTP will receive at " + binding.otpMobileNumber.text.toString()

                    OTP = it.returnMessage!!
                    startTimer(time_in_milli_seconds)
                    binding.otpSubmitTxt.text = "Submit"

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }

        })

        /*** Customer Data Observer ***/
        viewModel.getProfileResponse.observe(viewLifecycleOwner, Observer {

            if (it != null && !it.lstCustomerJson.isNullOrEmpty()) {


                _lstCustomerJson = it.lstCustomerJson
                _lstCustomerOfficalInfoJson = it.lstCustomerOfficalInfoJson

                if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == _lstCustomerJson!![0].customerTypeID.toString()) {


                    if (it.lstCustomerJson[0].isVerified == 1) {

                        RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",getString(R.string.customer_already_activated),
                            object : RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                            override fun onOk() {
                                findNavController().popBackStack()
                            }

                        })

                    }else if (it.lstCustomerJson[0].isVerified == 4) {
                        RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",getString(R.string.your_account_is_in_pending_kindly_contact_your_administrator),
                            object : RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })

                    }else if (it.lstCustomerJson[0].isVerified == 0 && it.lstCustomerJson[0].isActive == 0) {
                        RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",getString(R.string.your_account_has_been_deactivated),
                            object : RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })
                    } else if (it.lstCustomerJson[0].customerType.equals("Dealer", true) && it.lstCustomerJson[0].customerCategoryId == 1) {

                        RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",getString(R.string.you_are_not_eligible_to_activate_your_account),
                            object : RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                                override fun onOk() {
                                    findNavController().popBackStack()
                                }

                            })
                    } else {
                        if (it.lstCustomerJson[0].customerType != null)
                            binding.customerTypeName.text = it.lstCustomerJson[0].customerType

                        if (it.lstCustomerJson[0].loyaltyId != null)
                            binding.memberId.setText(it.lstCustomerJson[0].loyaltyId)


                        if (it.lstCustomerJson[0].firstName != null)
                            binding.name.setText(it.lstCustomerJson[0].firstName)

                        if (it.lstCustomerJson[0].email != null)
                            binding.email.setText(it.lstCustomerJson[0].email)

                        if (it.lstCustomerJson[0].mobile != null)
                            binding.mobile.setText(it.lstCustomerJson[0].mobile)

                        if (it.lstCustomerJson[0].address1 != null)
                            binding.address.setText(it.lstCustomerJson[0].address1)

                        if (it.lstCustomerJson[0].zip != null)
                            binding.pincode.setText(it.lstCustomerJson[0].zip)

                        if (it.lstCustomerJson[0].stateName != null)
                            binding.state.text = it.lstCustomerJson[0].stateName

                        if (it.lstCustomerJson[0].districtName != null)
                            binding.district.text = it.lstCustomerJson[0].districtName

                        if (it.lstCustomerJson[0].talukName != null)
                            binding.taluk.text = it.lstCustomerJson[0].talukName

                        if (it.lstCustomerJson[0].cityName != null)
                            binding.city.text = it.lstCustomerJson[0].cityName

                        if (!it.lstCustomerOfficalInfoJson.isNullOrEmpty()){
                            if (it.lstCustomerOfficalInfoJson[0].companyName != null)
                                binding.firmName.setText(it.lstCustomerOfficalInfoJson[0].companyName)

                            if (it.lstCustomerOfficalInfoJson[0].gstNumber != null)
                                binding.gstNumber.setText(it.lstCustomerOfficalInfoJson[0].gstNumber)

                        }

                    }


                } else {
                    RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",getString(R.string.incorrect_account_type),
                        object : RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                            override fun onOk() {
                                findNavController().popBackStack()
                            }

                        })

                }


            }


        })

        /*** Activate Account Observer ***/
        viewModel.activateCustomerLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){

                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",getString(R.string.your_account_has_been_activated_successfully),
                        object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
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

    private fun SendOTPRequest() {
        viewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                userName = "",
                userId = "-1",
                mobileNo = binding.otpMobileNumber.text.toString(),
                oTPType = "Enrollment"
            )
        )

    }


    private fun checkCustomerExistancy() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getMobileEmailExistenceCheck(
            CustomerExistenceRequest(
                actionType = "65",
                actorId = PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType),
                location =  (Location(
                    userName = binding.otpMobileNumber.text.toString(),
                ))

            )
        )
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