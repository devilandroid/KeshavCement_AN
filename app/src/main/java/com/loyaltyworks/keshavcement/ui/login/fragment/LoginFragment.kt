package com.loyaltyworks.keshavcement.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentLoginBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.CustomerTypeSpinnerAdapter
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.customerType.CustomerTypeViewModel
import com.loyaltyworks.keshavcement.ui.login.LoginActivity
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.Keyboard
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class LoginFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var commonViewModel: CustomerTypeViewModel
    private lateinit var viewModel: LoginRegistrationViewModel

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 60000L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var OTP: String = ""
    var passw: String = ""

    var mSelectedCustomer: LstAttributesDetail? = null
    var selectedCustomerTypeID: String = ""
    var customerTypeList = mutableListOf<LstAttributesDetail>()

    private var isLoginClick:Boolean = false
    private var isForgotClick:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        commonViewModel = ViewModelProvider(this).get(CustomerTypeViewModel::class.java)
        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "LoginView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        initialSetup()


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("jfkdjskf","fjkdsjfkds")
                backButtonClicked()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.customerTypeSpinner.onItemSelectedListener = this

        binding.loginButton.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
        binding.activateNowButton.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)
        binding.forgotSubmitOtp.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)
        binding.forgotBackButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

        customoerTypeSpinnerApi()
    }

    private fun initialSetup() {
        if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            binding.activateLayout.visibility = View.GONE
            binding.forgotPassword.visibility = View.GONE
            binding.registerLayout.visibility = View.GONE
            binding.bottomImg.setImageResource(R.drawable.group_1022)
        }else{
            binding.activateLayout.visibility = View.VISIBLE
            binding.forgotPassword.visibility = View.VISIBLE
            binding.registerLayout.visibility = View.VISIBLE
            binding.bottomImg.setImageResource(R.drawable.group_1001)
        }
    }

    private fun customoerTypeSpinnerApi() {
        Log.d("hbefhbfr","call 4")
        LoadingDialogue.showDialog(requireContext())
        commonViewModel.getCustomerTypeRequest(
            CustomerTypeRequest(
                actionType = 33
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Customer Type Observer ***/
        commonViewModel.getCustomerTypeResponse.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {

                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()){
                    if (customerTypeList.size > 0) {
                        customerTypeList.clear()
                    }

                    customerTypeList.addAll(it.lstAttributesDetails)

                    binding.customerTypeSpinner.adapter =
                        CustomerTypeSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item,customerTypeList)
                    Log.d("hbefhbfr","call 1")
//                if (isFirstLoad){
                    /*customerTypeList.forEach { lstAttributesDetail ->
                        if (lstAttributesDetail.attributeId == PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType)){
                            Log.d("hbefhbfr","call 2")
                            binding.customerTypeSpinner.setSelection(i)
                            isFirstLoad = false
                            i=0
                            return@forEach
                        }
                        i++
                    }*/
//                }

                    customerTypeList.forEachIndexed { index, lstAttributesDetail ->
                        if (lstAttributesDetail.attributeId == PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType)){
                            binding.customerTypeSpinner.setSelection(index)
                        }
                    }

                }else{
                    if (customerTypeList.size > 0) {
                        customerTypeList.clear()
                    }
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }
        })

        /***  Mobile Number Existancy Check  Observer ***/
        viewModel.mobileNumberExists.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it == 1) {
                        LoadingDialogue.dismissDialog()
                        if (isForgotClick){
                            /*** send otp for forgot ***/
                            SendOTPRequest()
                        }else if (isLoginClick){
                            loginApi(binding.userName.text.toString(),binding.password.text.toString())
                        }

                    }else if (it == 2){
                        LoadingDialogue.dismissDialog()
                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.your_account_has_been_deactivated),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {
                                binding.userName.text.clear()
                                binding.password.text.clear()
                                binding.forgotUserName.text.clear()
                            }
                        })
                    }else if (it == 3){
                        LoadingDialogue.dismissDialog()
                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.incorrect_account_type),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {
                                binding.userName.text.clear()
                                binding.password.text.clear()
                                binding.forgotUserName.text.clear()
                            }
                        })
                    } else {
                        LoadingDialogue.dismissDialog()

                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.membe_doesnt_exist),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {
                                binding.userName.text.clear()
                                binding.password.text.clear()
                                binding.forgotUserName.text.clear()
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

//                    binding.spinnerLayout.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE

                    binding.forgotUserName.isEnabled = false
                    binding.otpSentNumber.text = "OTP will receive at " + binding.forgotUserName.text.toString()

                    OTP = it.returnMessage!!
                    startTimer(time_in_milli_seconds)
                    binding.forgotSubmitTxt.text = "Submit"

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }

        })

        /*** Login Observer ***/
        viewModel.loginDetails.observe(viewLifecycleOwner, Observer{

            LoadingDialogue.dismissDialog()

            /**
             *  Result = 1 -> Successful
             *           -1 -> Invalid password
             *           6 -> Invalid membership Id
             *           other -> Invalid User name and password.
             */

            if (it != null && !it.userList.isNullOrEmpty()) {

                val result: String = when (it.userList?.get(0)?.result) {

                    1 -> {
                        loginProcess(it)

                        // login message display
                        "Login successful "
                    }
                    -1 -> {
                        if (isForgotClick){
                            PreferenceHelper.setBooleanValue(requireContext(),BuildConfig.ForgotPasswordClicked,true)
                            loginProcess(it)
                            ""
                        }else{
                            binding.password.text!!.clear()
                            "Invalid password"
                        }

                    }
                    6 -> {
                        "Invalid username Id"
                    }
                    else -> {
                        "Enter valid membership id and password "
                    }
                }

                // display snack bar
                (activity as LoginActivity).snackBar(result, R.color.dark_blue)
            }

        })

    }

    private fun loginProcess(loginResponse: LoginResponse) {
        // set Login successful
        PreferenceHelper.setBooleanValue(requireContext(), BuildConfig.IsLoggedIn, true)
        // save login details
        PreferenceHelper.setLoginDetails(requireContext(), loginResponse)
        val intent = Intent(requireContext(), DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    private fun loginApi(userName: String, pwd:String) {
        //Push token :
        var token: String? = PreferenceHelper.getStringValue(context = requireContext(), BuildConfig.PUSH_TOKEN)
        Log.d("TAG", "token : $token")

        if (token!!.isEmpty()) token = FirebaseInstanceId.getInstance().token
        print("onCreate: TOKEN $token")

        // login api call
        viewModel.getLoginData(
            LoginRequest(
                Browser = "Android",
                LoggedDeviceName = "Android",
                UserName = userName,
                Password = pwd,
                PushID = token,
                UserActionType = "GetPasswordDetails",
                UserType = "Customer"
            )
        )
    }

    private fun SendOTPRequest() {
        viewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                MerchantUserName = BuildConfig.MerchantName,
                UserName = "",
                UserId = -1,
                MobileNo = binding.forgotUserName.text.toString(),
                OTPType = "Enrollment"
            )
        )

    }

    private fun backButtonClicked() {
        if (binding.forgotLayout.visibility == View.VISIBLE){
            if (binding.otpLayout.visibility == View.GONE) {
                binding.forgotHeader.visibility = View.GONE
                binding.forgotLayout.visibility = View.GONE

                binding.loginLayout.visibility = View.VISIBLE
                binding.loginHeader.visibility = View.VISIBLE
                binding.activateLayout.visibility = View.VISIBLE
            }else{
                timers.cancel()
                binding.timer.text = ""
                binding.forgotUserName.isEnabled = true
                OTP = ""

                binding.otpView.setOTP("")
                binding.forgotUserName.setText("")
                binding.forgotSubmitTxt.text = "Generate OTP"

                binding.otpLayout.visibility = View.GONE
                binding.forgotHeader.visibility = View.GONE
                binding.forgotLayout.visibility = View.GONE

                binding.loginLayout.visibility = View.VISIBLE
                binding.loginHeader.visibility = View.VISIBLE
                binding.activateLayout.visibility = View.VISIBLE
            }

        }else{
            findNavController().popBackStack()
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.forgot_password ->{
                if (BlockMultipleClick.click())return
                binding.loginLayout.visibility = View.GONE
                binding.loginHeader.visibility = View.GONE
                binding.activateLayout.visibility = View.GONE

                binding.forgotHeader.visibility = View.VISIBLE
                binding.forgotLayout.visibility = View.VISIBLE
            }

            R.id.forgot_back_button ->{
                if (BlockMultipleClick.click())return
                backButtonClicked()
            }

            R.id.resend_otp ->{
                if (BlockMultipleClick.click())return
                SendOTPRequest()
            }

            R.id.forgot_submit_otp ->{
                if (binding.forgotUserName.text.toString().isNullOrEmpty()) {
                    binding.forgotUserName.requestFocus()
                    binding.forgotUserName.error = getString(R.string.enter_mobile_number)
                    return
                }

                if (binding.forgotUserName.text.toString().length != 10) {
                    binding.forgotUserName.text.clear()
                    binding.forgotUserName.requestFocus()
                    binding.forgotUserName.error = getString(R.string.enter_valid_mobile_no)
                    return
                }

                if (binding.forgotSubmitTxt.text.toString() == "Generate OTP") {
                    if (::timers.isInitialized){
                        timers.cancel()
                    }

                    isForgotClick = true
                    isLoginClick = false
                    var userName:String = binding.forgotUserName.text.toString()
                    checkCustomerExistancy(userName)

                }else {
                    if (binding.otpView.otp.toString().isNullOrEmpty()) {
                        Toast.makeText(requireContext(),"Please enter OTP", Toast.LENGTH_SHORT).show()
                    }else if (binding.otpView.otp.toString().length == 6 && binding.otpView.otp.toString() == OTP){
                        Keyboard.hideKeyboard(requireContext(),binding.mLoginHost)
                        timers.cancel()

                        /*** Login Process After Forgot Password ***/
                        loginApi(binding.forgotUserName.text.toString(),passw)


                    }else{
                        Toast.makeText(requireContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show()
                    }

                }

            }

            R.id.activate_now_button ->{
                if (BlockMultipleClick.click())return
                findNavController().navigate(R.id.action_loginFragment_to_activateFragment)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.register_btn ->{
                if (BlockMultipleClick.click())return
                if (customerTypeList.size > 0) {
                    customerTypeList.clear()
                }
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.login_button ->{
                if (BlockMultipleClick.click())return
                // hide keyboard
                Keyboard.hideKeyboard(requireContext(), binding.mLoginHost)

                if (binding.userName.text.toString().isNullOrBlank()) {
                    binding.userName.error = getString(R.string.enter_mobile_number_membership_id)
                    binding.userName.requestFocus()
                    return
                }else if(binding.userName.text.toString().isNotEmpty() && binding.userName.text.toString().length < 10){
                    binding.userName.text!!.clear()
                    binding.userName.error = getString(R.string.enter_valid_mobile_number_membership_id)
                    binding.userName.requestFocus()
                    return
                }else if (binding.password.text.toString().isEmpty()) {
                    binding.password.error = getString(R.string.prompt_password)
                    binding.password.requestFocus()
                    return
                } /*else if (!binding.tcBtn.isChecked) {
                    // display snack bar
                    snackBar(this, "Please accept terms and conditions !", R.color.colorPrimary)
                    return
                }*/
                else {
                    isForgotClick = false
                    LoadingDialogue.showDialog(requireContext())
                    isLoginClick = true
                    var userName: String = binding.userName.text.toString()
                    checkCustomerExistancy(userName)
                }

            }

        }
    }


    private fun checkCustomerExistancy(userName: String) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getMobileEmailExistenceCheck(
            CustomerExistenceRequest(
                actionType = "65",
                actorId = PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType),
                location = (Location(
                    userName = userName,
                ))

            )
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when ((parent as Spinner).id) {
            R.id.customer_type_spinner ->{
//                    val selectedId: String = (parent.getItemAtPosition(position) as LstAttributesDetail).attributeId!!
                    mSelectedCustomer = parent.getItemAtPosition(position) as LstAttributesDetail
                    selectedCustomerTypeID = mSelectedCustomer!!.attributeId!!
                    Log.d("hbefhbfr","call 3")
                    Log.d("rg3ggggg","id : " + selectedCustomerTypeID)
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,selectedCustomerTypeID)
                    initialSetup()

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