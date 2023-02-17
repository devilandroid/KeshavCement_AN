package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

import android.os.Bundle
import android.text.TextUtils
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentEditAddressBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.StateAdapter
import com.loyaltyworks.keshavcement.ui.CommonViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import java.io.Serializable

class EditAddressFragment : Fragment(),  AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentEditAddressBinding
    private lateinit var commonViewModel: CommonViewModel

    var stateList = mutableListOf<State>()
    var mSelectedState: State? = null

    var _districtList = mutableListOf<LstDistrict>()
    var districtListAdapter: ArrayAdapter<String>? = null
    var districtId: String = "-1"
    var districtName: String = ""

    var i = 0
    var error = ""

    lateinit var _lstCustomerJson: LstCustomerJson

    var isFirstLoad : Boolean = true

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        binding = FragmentEditAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "EditAddressView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "EditAddressFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)


        if (this.arguments != null) {
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = requireArguments().getString("SelectedCustomerPartyLoyaltyID").toString()

            _lstCustomerJson = requireArguments().getSerializable("CustomerProfileData") as LstCustomerJson
        }

        binding.stateSpinner.onItemSelectedListener = this
        binding.districtSpinner.onItemSelectedListener = this


        binding.NameTxt.setText(_lstCustomerJson.firstName)
        binding.mobileEdt.setText(_lstCustomerJson.mobile)
        binding.emailEdt.setText(_lstCustomerJson.email)
        binding.addressEdt.setText(_lstCustomerJson.address1)
        binding.pinEdt.setText(_lstCustomerJson.zip)

        StateRequest()

        binding.editProceed.setOnClickListener {

            if (!validate()) {
                if (error.isNotEmpty())
                    AppController.showSuccessPopUpDialog(requireContext(),error, object : AppController.SuccessCallBack{
                        override fun onOk() {
                        }

                    })
                return@setOnClickListener
            }else {
                val bundle =Bundle()
                bundle.putString("SelectedCustomerUserID",actorID)
                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                bundle.putSerializable("CustomerProfileData", _lstCustomerJson)
//                bundle.putSerializable("CartDataList2", catalogueSaveCartDetailListResponse as Serializable)
                _lstCustomerJson.firstName = binding.NameTxt.text.toString()
                _lstCustomerJson.mobile = binding.mobileEdt.text.toString()
                _lstCustomerJson.email = binding.emailEdt.text.toString()
                _lstCustomerJson.address1 = binding.addressEdt.text.toString()
                _lstCustomerJson.zip = binding.pinEdt.text.toString()
                _lstCustomerJson.stateId = mSelectedState!!.stateId
                _lstCustomerJson.stateName = mSelectedState!!.stateName
                _lstCustomerJson.districtId = districtId.toInt()
                _lstCustomerJson.districtName = districtName

                findNavController().navigate(R.id.action_editAddressFragment_to_addressFragment, bundle)
            }

        }

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

                    stateList.forEach { stateDetail ->
                        if (stateDetail.stateId == _lstCustomerJson.stateId) {
                            binding.stateSpinner.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }

                }else{
                    stateList.add(0, State(
                        stateName = "Select State",
                        stateId = -1
                    )
                    )

                    binding.stateSpinner.adapter = StateAdapter(requireContext(), android.R.layout.simple_spinner_item,stateList)
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

                    if (isFirstLoad){
                        _districtList.forEachIndexed { index, districtDetails ->
                            if (districtDetails.districtId == _lstCustomerJson.districtId) {
                                binding.districtSpinner.setSelection(index)
                                isFirstLoad = false
                            }
                        }
                    }


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

    }


    /* Validation for process btn  */
    fun validate(): Boolean {
        var valid = true
        error = ""
        val email: String = binding.emailEdt.text.toString()
        var mobile: String = binding.mobileEdt.text.toString()
        val zip: String = binding.pinEdt.text.toString()

        Log.d("asdfjkllasdf",_lstCustomerJson.stateId!!.toString())
        if (mSelectedState == null || mSelectedState?.stateId == -1){
            error += """
            
            ${requireContext().resources.getString(R.string.state_is_req)}
            """.trimIndent()
            valid = false
        }

        if (districtId == "-1"){

            error += """
            
            ${requireContext().resources.getString(R.string.district_is_req)}
            """.trimIndent()
            valid = false
        }


        if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            binding.emailEdt.error = requireContext().resources.getString(R.string.enter_valid_emailid)
            Toast.makeText(requireContext(), getString(R.string.enter_valid_emailid), Toast.LENGTH_SHORT).show()

            valid = false
        } else {
            binding.emailEdt.error = null
        }
        if (!mobile.isEmpty() && mobile.length < 10) {
//            binding.mobileEdt.error = getString(R.string.enter_valid_mobile_no)
            Toast.makeText(requireContext(), getString(R.string.enter_valid_mobile_no), Toast.LENGTH_SHORT).show()
            valid = false
        } else {
            binding.mobileEdt.error = null
        }
        if (mobile.isEmpty()) {
//            binding.mobileEdt.error = requireContext().resources.getString(R.string.enter_mobile_number)
            Toast.makeText(requireContext(), getString(R.string.enter_mobile_number), Toast.LENGTH_SHORT).show()
            valid = false
        } else {
            binding.mobileEdt.error = null
        }
        if (TextUtils.isEmpty(binding.NameTxt.text.toString()) || binding.NameTxt.text.toString()
                .trim { it <= ' ' }.isEmpty()
        ) {
//            binding.NameTxt.error = requireContext().resources.getString(R.string.enter_your_name)
            Toast.makeText(requireContext(), getString(R.string.enter_your_name), Toast.LENGTH_SHORT).show()
            valid = false
        } else {
            binding.NameTxt.error = null
        }
        if (TextUtils.isEmpty(binding.addressEdt.text.toString()) || binding.addressEdt.text.toString().trim { it <= ' ' }.isEmpty()
        ) {
//            binding.addressEdt.error = requireContext().resources.getString(R.string.address_shouldnt_be_empty)
            Toast.makeText(requireContext(), getString(R.string.address_shouldnt_be_empty), Toast.LENGTH_SHORT).show()
            valid = false
        }
        if (TextUtils.isEmpty(zip) || zip.length < 6) {
//            binding.pinEdt.error = requireContext().resources.getString(R.string.invalid_zip_code)
            Toast.makeText(requireContext(), getString(R.string.invalid_zip_code), Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
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
                districtName = _districtList[position].districtName.toString()
                Log.d("bhbrfhrb","district ID : " + districtId)
                Log.d("bhbrfhrb","district Name : " + districtName)

            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}