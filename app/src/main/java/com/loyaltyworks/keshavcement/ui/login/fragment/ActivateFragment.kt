package com.loyaltyworks.keshavcement.ui.login.fragment

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
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.Keyboard
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class ActivateFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentActivateBinding
    private lateinit var viewModel: LoginRegistrationViewModel
    private lateinit var commonViewModel: CommonViewModel

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 60000L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var OTP: String = ""

    var stateList = mutableListOf<State>()
    var mSelectedState: State? = null

    var _districtList = mutableListOf<LstDistrict>()
    var districtListAdapter: ArrayAdapter<String>? = null
    var districtId: String = "-1"

    var _talukList = mutableListOf<LstTaluk>()
    var talukListAdapter: ArrayAdapter<String>? = null
    var talukId: String = "-1"

    var identityNo: String = ""
    var identityType: String = ""
    var aadharNo: String = ""
    var gstNo: String = ""
    var memberID: String = ""

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
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ActivateView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ActivateFragment")
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

        binding.stateSpinner.onItemSelectedListener = this
        binding.districtSpinner.onItemSelectedListener = this
        binding.talukSpinner.onItemSelectedListener = this

        binding.otpSubmitButton.setOnClickListener(this)
        binding.backLoginBtn.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)

        binding.backToLogin.setOnClickListener(this)
        binding.activateBtn.setOnClickListener(this)
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
                        StateRequest()
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

            R.id.activate_btn ->{
                val email: String = binding.email.text.toString()

                if (binding.customerTypeName.text.toString().isNullOrBlank()){
                    Toast.makeText(requireContext(), "Customer Type should not be empty", Toast.LENGTH_SHORT).show()

                }else if (PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer &&
                    binding.memberId.text.toString().isNullOrBlank()){

                    binding.memberId.error = getString(R.string.enter_member_id)
                    binding.memberId.requestFocus()

                }else if (binding.name.text.toString().isNullOrBlank()){
                    binding.name.error = getString(R.string.enter_your_name)
                    binding.name.requestFocus()

                }else if (binding.firmName.text.toString().isNullOrBlank()){
                    binding.firmName.error = getString(R.string.enter_firm_name)
                    binding.firmName.requestFocus()

                }else if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.email.error = requireContext().resources.getString(R.string.enter_valid_emailid)

                }else if (binding.mobile.text.toString().isNullOrBlank()){
                    binding.mobile.error = getString(R.string.enter_mobile_number)
                    binding.mobile.requestFocus()

                }else if (binding.mobile.text.toString().length < 10){
                    binding.mobile.text.clear()
                    binding.mobile.error = getString(R.string.enter_valid_mobile_no)
                    binding.mobile.requestFocus()

                }else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer ||
                    PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) &&
                    binding.aadharNumber.text.toString().isNullOrBlank()){

                    binding.aadharNumber.error = getString(R.string.enter_aadhar_number)
                    binding.aadharNumber.requestFocus()

                }else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Engineer ||
                            PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Mason) &&
                    !binding.aadharNumber.text.toString().isNullOrBlank() && binding.aadharNumber.text.toString().length < 12){

                    binding.aadharNumber.error = getString(R.string.enter_valid_aadhar_number)
                    binding.aadharNumber.requestFocus()

                }else if ((PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.Dealer ||
                    PreferenceHelper.getStringValue(requireContext(),BuildConfig.CustomerType) == BuildConfig.SubDealer) &&
                    binding.gstNumber.text.toString().isNullOrBlank()){

                    binding.gstNumber.error = getString(R.string.enter_gst_number)
                    binding.gstNumber.requestFocus()

                }else if (binding.address.text.toString().isNullOrBlank()){
                    binding.address.error = getString(R.string.enter_your_address)
                    binding.address.requestFocus()

                }else if (binding.pincode.text.toString().isNullOrBlank()){
                    binding.pincode.error = getString(R.string.enter_pin_code)
                    binding.pincode.requestFocus()

                }else if (!binding.pincode.text.toString().isNullOrBlank() && binding.pincode.text.toString().length < 6){
                    binding.pincode.text.clear()
                    binding.pincode.error = getString(R.string.invalid_pin_code)
                    binding.pincode.requestFocus()

                }else if (mSelectedState!!.stateId == -1){
                    Toast.makeText(requireContext(), getString(R.string.select_your_state), Toast.LENGTH_SHORT).show()

                }else if (districtId == "-1"){
                    Toast.makeText(requireContext(), getString(R.string.select_your_district), Toast.LENGTH_SHORT).show()

                }else if (talukId == "-1"){
                    Toast.makeText(requireContext(), getString(R.string.select_your_taluk), Toast.LENGTH_SHORT).show()

                }else{
                    activateCustomer()
                }
            }

        }
    }

    private fun activateCustomer() {
        Toast.makeText(requireContext(), "not imple.", Toast.LENGTH_SHORT).show()
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

        /***  state list observer  ***/
        commonViewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.stateList.isNullOrEmpty()) {
                    stateList.clear()

                    stateList.addAll(it.stateList!!)
                    stateList.add(0, State(
                        stateName = "Select State",
                        stateId = -1
                    )
                    )

                    binding.stateSpinner.adapter = StateAdapter(requireContext(),android.R.layout.simple_spinner_item,stateList)

                }else{
                    stateList.add(0, State(
                        stateName = "Select State",
                        stateId = -1
                    )
                    )

                    binding.stateSpinner.adapter = StateAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,stateList)
                }

            }

        })

        /*** District List Observer ***/
        commonViewModel.districtLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstDistrict.isNullOrEmpty()){
                    val districtLists: MutableList<LstDistrict> = it.lstDistrict!!.toMutableList()
                    _districtList = districtLists

                    val districtListName = ArrayList<String>()

                    for (commonSpinner in districtLists) {
                        districtListName.add(commonSpinner.districtName!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select District"
                    commonSpinner.id = -1
                    districtListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstDistrict()
                    custlist1.districtName = "Select District"
                    custlist1.districtId = -1
                    _districtList.add(0,custlist1)

                    districtListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtListName)
                    districtListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.districtSpinner.adapter = districtListAdapter

                }else {
                    val districtListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select District"
                    commonSpinner.id = -1
                    districtListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstDistrict()
                    custlist1.districtName = "Select District"
                    custlist1.districtId = -1
                    _districtList.add(0,custlist1)

                    districtListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtListNames)
                    districtListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.districtSpinner.adapter = districtListAdapter
                }
            }
        })

        /*** Taluk List Observer ***/
        commonViewModel.talukLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstTaluk.isNullOrEmpty()){
                    val talukLists: MutableList<LstTaluk> = it.lstTaluk!!.toMutableList()
                    _talukList = talukLists

                    val talukListName = ArrayList<String>()

                    for (commonSpinner in talukLists) {
                        talukListName.add(commonSpinner.talukName!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Taluk"
                    commonSpinner.id = -1
                    talukListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstTaluk()
                    custlist1.talukName = "Select Taluk"
                    custlist1.talukId = -1
                    _talukList.add(0,custlist1)

                    talukListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, talukListName)
                    talukListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.talukSpinner.adapter = talukListAdapter

                }else {
                    val talukListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Taluk"
                    commonSpinner.id = -1
                    talukListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstTaluk()
                    custlist1.talukName = "Select Taluk"
                    custlist1.talukId = -1
                    _talukList.add(0,custlist1)

                    talukListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, talukListNames)
                    talukListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.talukSpinner.adapter = talukListAdapter
                }
            }
        })


    }

    private fun SendOTPRequest() {
        viewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                MerchantUserName = BuildConfig.MerchantName,
                UserName = "",
                UserId = -1,
                MobileNo = binding.otpMobileNumber.text.toString(),
                OTPType = "Enrollment"
            )
        )

    }

    private fun StateRequest() {
        commonViewModel.getStateData(
            StateListRequest(
                actionType = "2",
                isActive = "true",
                sortColumn = "STATE_NAME",
                sortOrder = "ASC",
                startIndex = "1",
                countryID = BuildConfig.CountryID
            )
        )
    }

    private fun talukRequest(distictId: String) {
        commonViewModel.getTalukData(
            TalukListRequest(
                actionType = "1",
                districtId = distictId
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when ((parent as Spinner).id) {

            R.id.state_spinner -> {
                mSelectedState = parent.getItemAtPosition(position) as State

                if (mSelectedState!!.stateId!! > 0) {
                    /*** District Api call ***/
                    commonViewModel.getDistrictData(
                        DistrictListRequest(
                            stateId = mSelectedState!!.stateId.toString()
                        )
                    )

                }else{
                    val districtListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select District"
                    commonSpinner.id = -1
                    districtListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstDistrict()
                    custlist1.districtName = "Select District"
                    custlist1.districtId = -1
                    _districtList.add(0,custlist1)

                    districtListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtListNames)
                    districtListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.districtSpinner.adapter = districtListAdapter
                }

            }

            R.id.district_spinner -> {
                districtId = _districtList[position].districtId.toString()
                Log.d("bhbrfhrb","district ID : " + districtId)
                if (districtId != "-1"){
                    /*** Taluk Api call ***/
                    talukRequest(districtId)

                }else{
                    val talukListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Taluk"
                    commonSpinner.id = -1
                    talukListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstTaluk()
                    custlist1.talukName = "Select Taluk"
                    custlist1.talukId = -1
                    _talukList.add(0,custlist1)

                    talukListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, talukListNames)
                    talukListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.talukSpinner.adapter = talukListAdapter
                }
            }

            R.id.taluk_spinner -> {
                talukId = _talukList[position].talukId.toString()
                Log.d("bhbrfhrb","taluk ID : " + talukId)
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}