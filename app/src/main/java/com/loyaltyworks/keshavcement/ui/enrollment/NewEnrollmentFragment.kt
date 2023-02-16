package com.loyaltyworks.keshavcement.ui.enrollment

import android.os.Bundle
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
import com.loyaltyworks.keshavcement.databinding.FragmentNewEnrollmentBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonAdapter
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.model.adapter.StateAdapter
import com.loyaltyworks.keshavcement.ui.CommonViewModel
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class NewEnrollmentFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentNewEnrollmentBinding
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var viewModel: EnrollmentViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType: CommonSpinner
    var userTypeId: Int = -1

    var stateList = mutableListOf<State>()
    var mSelectedState: State? = null

    var _districtList = mutableListOf<LstDistrict>()
    var districtListAdapter: ArrayAdapter<String>? = null
    var districtId: String = "-1"

    var _talukList = mutableListOf<LstTaluk>()
    var talukListAdapter: ArrayAdapter<String>? = null
    var talukId: String = "-1"

    var actorID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(EnrollmentViewModel::class.java)
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        binding = FragmentNewEnrollmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "NewEnrollmentView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NewEnrollmentFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            actorID = PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerIdSE)
        }else{
            actorID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
        }

        binding.customerTypeSpinner.onItemSelectedListener = this
        binding.stateSpinner.onItemSelectedListener = this
        binding.districtSpinner.onItemSelectedListener = this
        binding.talukSpinner.onItemSelectedListener = this

        binding.submitEnrollment.setOnClickListener(this)

        userTypeSpinner()
        StateRequest()

    }

    private fun userTypeSpinner() {
        userTypeList.clear()

            userTypeList.add( CommonSpinner(name = "Select Customer Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Engineer", id = 1))
            userTypeList.add( CommonSpinner(name = "Mason", id = 2))

        binding.customerTypeSpinner.adapter = SpinnerCommonAdapter(requireActivity(), R.layout.spinner_popup_row,userTypeList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

        /***  Mobile Number Existancy Check  Observer ***/
        loginViewModel.mobileNumberExists.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it != 1) {
                        enrollSubmitApi()
                    }else{
                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.mobile_already_exist),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {

                            }
                        })
                    }
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }
        })


        /*** Enrollment Observer ***/
        viewModel.enrollmentLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage.toString().split("~")[0] == "1"){

                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",getString(R.string.enrolled_new_customer),
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
            ))
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submit_enrollment ->{
                if (BlockMultipleClick.click()) return

                if (userTypeId == -1){
                    Toast.makeText(requireContext(), getString(R.string.select_customer_type), Toast.LENGTH_SHORT).show()
                }else if (binding.nameEdt.text.toString().trim().isNullOrEmpty()){
                    binding.nameEdt.error = getString(R.string.enter_name)
                    binding.nameEdt.requestFocus()
                }else if (binding.firmNameEdt.text.toString().trim().isNullOrEmpty()){
                    binding.firmNameEdt.error = getString(R.string.enter_firm_name)
                    binding.firmNameEdt.requestFocus()
                }else if (binding.mobileNumberEdt.text.toString().trim().isNullOrEmpty()){
                    binding.mobileNumberEdt.error = getString(R.string.enter_mobile_number)
                    binding.mobileNumberEdt.requestFocus()
                }else if (!binding.mobileNumberEdt.text.toString().trim().isNullOrEmpty() && binding.mobileNumberEdt.text.toString().trim().length < 10){
                    binding.mobileNumberEdt.error = getString(R.string.enter_valid_mobile_no)
                    binding.mobileNumberEdt.requestFocus()
                }else if (binding.addressEdt.text.toString().trim().isNullOrEmpty()){
                    binding.addressEdt.error = getString(R.string.enter_address)
                    binding.addressEdt.requestFocus()
                }else if (binding.pinEdt.text.toString().trim().isNullOrEmpty()){
                    binding.pinEdt.error = getString(R.string.enter_pin_code)
                    binding.pinEdt.requestFocus()
                }else if (!binding.pinEdt.text.toString().isNullOrBlank() && binding.pinEdt.text.toString().length < 6){
                    binding.pinEdt.error = getString(R.string.invalid_pin_code)
                    binding.pinEdt.requestFocus()
                }else if (mSelectedState!!.stateId == -1){
                    Toast.makeText(requireContext(), getString(R.string.select_state), Toast.LENGTH_SHORT).show()

                }else if (districtId == "-1" /*mSelectedCity!!.cityId == -1*/){
                    Toast.makeText(requireContext(), getString(R.string.select_district), Toast.LENGTH_SHORT).show()

                }else if (talukId == "-1" /*mSelectedCity!!.cityId == -1*/){
                    Toast.makeText(requireContext(), getString(R.string.select_taluk), Toast.LENGTH_SHORT).show()

                }else{
                    checkCustomerExistancy(binding.mobileNumberEdt.text.toString())
                }

            }

        }
    }


    private fun checkCustomerExistancy(userName: String) {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.getMobileEmailExistenceCheck(
            CustomerExistenceRequest(
                actionType = "65",
                actorId = userTypeId.toString(),
                location = (Location(
                    userName = userName,
                ))

            )
        )
    }

    private fun enrollSubmitApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getEnrollmentData(
            EnrollmentRequest(
                actionType = "0",
                HierarchyMapDetailsEnrollment(
                    customerUserID = actorID
                ),
                ObjCustomerEnrollment(
                    customerTypeID = userTypeId.toString(),
                    firstName = binding.nameEdt.text.toString(),
                    customerEmail = binding.emailEdt.text.toString(),
                    customerMobile = binding.mobileNumberEdt.text.toString(),
                    address = binding.addressEdt.text.toString(),
                    customerZip = binding.pinEdt.text.toString(),
                    customerStateId = mSelectedState!!.stateId.toString(),
                    districtId = districtId,
                    talukId = talukId,
                    registrationSource = "3",
                    merchantId = "1",
                    isActive = "1"
                ),
                ObjCustomerOfficalInfoEnrollment(
                    companyName = binding.firmNameEdt.text.toString()
                )

            )
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.customer_type_spinner ->{
                mSelecteduserType = parent.getItemAtPosition(position) as CommonSpinner
                userTypeId = mSelecteduserType.id!!
                Log.d("fdsfsdf", mSelecteduserType.name!!)
            }

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