package com.loyaltyworks.keshavcement.ui.profile

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProfileBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.permissionx.guolindev.PermissionX
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType


class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    private var mProfileImagePath = ""

    var stateID: Int = -1
    var districtID: Int = -1
    var talukID: Int = -1

    var _lstCustomerJson: List<LstCustomerJson>? = null
    var _lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJson>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ProfileView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ProfileFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.aadharLayout.visibility = View.VISIBLE
            binding.gstLayout.visibility = View.GONE
        }else {
            binding.aadharLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.VISIBLE
        }

        binding.changeProfileImage.setOnClickListener(this)
        binding.updateProfile.setOnClickListener(this)

        callApi()
    }

    private fun callApi() {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.setProfileRequest(
            ProfileRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.change_profile_image ->{
                LoadingDialogue.dismissDialog()
                askPermission()
                // Browse Image or Files

            }

            R.id.update_profile ->{
                if (BlockMultipleClick.click())return

                if (binding.customerType.text.toString().isNullOrBlank()){
                    Toast.makeText(requireContext(), getString(R.string.customer_type_mandatory), Toast.LENGTH_SHORT).show()
                }else if (binding.nameEdt.text.toString().isNullOrBlank()){
                    binding.nameEdt.error = resources.getString(R.string.enter_your_name)
                    binding.nameEdt.requestFocus()
                }else if (binding.firmNameEdt.text.toString().isNullOrBlank()){
                    binding.firmNameEdt.error = resources.getString(R.string.enter_firm_name)
                    binding.firmNameEdt.requestFocus()
                }else if (binding.firmNameEdt.text.toString().isNullOrBlank()){
                    Toast.makeText(requireContext(), getString(R.string.mobile_number_mandatory), Toast.LENGTH_SHORT).show()

                }/*else if (binding.emailEdt.text.toString().isNullOrBlank()){
                    binding.emailEdt.error = resources.getString(R.string.enter_valid_emailid)
                    binding.emailEdt.requestFocus()
                }*/else if (binding.addressEdt.text.toString().isNullOrBlank()){
                    binding.addressEdt.error = resources.getString(R.string.enter_your_address)
                    binding.addressEdt.requestFocus()

                }else if (binding.pincodeEdt.text.toString().isNullOrBlank()){
                    binding.pincodeEdt.error = resources.getString(R.string.enter_pin_code)
                    binding.pincodeEdt.requestFocus()

                }else if (!binding.pincodeEdt.text.toString().isNullOrBlank() && binding.pincodeEdt.text.toString().length < 6){
                    binding.pincodeEdt.error = resources.getString(R.string.invalid_pin_code)
                    binding.pincodeEdt.requestFocus()

                }else if (binding.state.text.toString().isNullOrBlank()){
                    binding.state.error = resources.getString(R.string.state_mandatory)
                    binding.state.requestFocus()

                }else if (binding.district.text.toString().isNullOrBlank()){
                    binding.district.error = resources.getString(R.string.district_mandatory)
                    binding.district.requestFocus()

                }else if (binding.taluka.text.toString().isNullOrBlank()){
                    binding.taluka.error = resources.getString(R.string.taluka_mandatory)
                    binding.taluka.requestFocus()

                }else{

                    UpdateProfile()
                }
            }

        }
    }



    private fun UpdateProfile() {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.activateCustomerData(
            ActivateCustomerRequest(
                actionType = "262",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ObjCustomerJsonActivate(
                    address1 = binding.addressEdt.text.toString(),
                    stateId = _lstCustomerJson!![0].stateId.toString(),
                    customerId = _lstCustomerJson!![0].customerId.toString(),
                    firstName = binding.nameEdt.text.toString(),
                    mobile = _lstCustomerJson!![0].mobile,
                    zip = _lstCustomerJson!![0].zip,
                    districtId = _lstCustomerJson!![0].districtId.toString(),
                    talukId = _lstCustomerJson!![0].talukId.toString(),
                    email = binding.emailEdt.text.toString(),
                    rELATEDPROJECTTYPE = "KESHAV_CEMENT",
                    addressId = _lstCustomerJson!![0].addressId.toString(),
                    aadharNumber = binding.aadharNOEdt.text.toString(),
                    dob = AppController.dateAPIFormats(binding.birthDate.text.toString())
                ),
                ObjCustomerOfficalInfoActivate(
                    companyName = binding.firmNameEdt.text.toString(),
                    sapNo = _lstCustomerOfficalInfoJson!![0].sapCode.toString(),
                    gSTNumber = binding.gstNoEdt.text.toString()
                )
            )
        )
    }

    private fun profileImageUpdateApi(mProfileImagePath: String) {

        LoadingDialogue.showDialog(requireContext())
        viewModel.setProfileImageData(
            ProfileImageUpdateRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                objCustomerJson = ObjCustomerJsonss(
                    displayImage = mProfileImagePath,
                    loyaltyId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId
                )
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Update Profile Image Observer ***/
        viewModel.profileImageUpdateLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.returnMessage == "1"){
                Toast.makeText(requireContext(), resources.getString(R.string.your_profile_image_update_successfully), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), resources.getString(R.string.failure_to_update_your_profile_image), Toast.LENGTH_SHORT).show()
            }
        })

        /*** Profile Observer ***/
        loginViewModel.getProfileResponse.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null) {

                if (!it.lstCustomerJson.isNullOrEmpty()) {
                    val data = it.lstCustomerJson!![0]

                    _lstCustomerJson = it.lstCustomerJson
                    _lstCustomerOfficalInfoJson = it.lstCustomerOfficalInfoJson

                    stateID = data.stateId!!
                    districtID = data.districtId!!
                    talukID = data.talukId!!

                    if (!data.customerType.isNullOrEmpty()){
                        binding.customerType.text = data.customerType.toString()
                    }else{
                        binding.customerType.text = ""
                    }

                    if (!data.firstName.isNullOrEmpty()){
                        binding.nameEdt.setText(data.firstName.toString())
                    }else{
                        binding.nameEdt.setText("")
                    }

                    if (!data.mobile.isNullOrEmpty()){
                        binding.mobileEdt.text = data.mobile.toString()
                    }else{
                        binding.mobileEdt.text = ""
                    }

                    if (!data.email.isNullOrEmpty()){
                        binding.emailEdt.setText(data.email.toString())
                    }else{
                        binding.emailEdt.setText("")
                    }

                    if (!data.address1.isNullOrEmpty()){
                        binding.addressEdt.setText(data.address1.toString())
                    }else{
                        binding.addressEdt.setText("")
                    }

                    if (!data.zip.isNullOrEmpty()){
                        binding.pincodeEdt.setText(data.zip.toString())
                    }else{
                        binding.pincodeEdt.setText("")
                    }

                    if (!data.stateName.isNullOrEmpty()){
                        binding.state.text = data.stateName.toString()
                    }else{
                        binding.state.text = ""
                    }

                    if (!data.districtName.isNullOrEmpty()){
                        binding.district.text = data.districtName.toString()
                    }else{
                        binding.district.text = ""
                    }

                    if (!data.talukName.isNullOrEmpty()){
                        binding.taluka.text = data.talukName.toString()
                    }else{
                        binding.taluka.text = ""
                    }

                    if (!data.jdob.isNullOrEmpty()){
                        binding.birthDate.text = AppController.dateAPIFormat(data.jdob.split(" ")[0])
                    }else{
                        binding.birthDate.text = ""
                    }

                    if (!data.anniversary.isNullOrEmpty()){
                        binding.anniversaryDate.text = AppController.dateAPIFormat(data.anniversary.split(" ")[0])
                    }else{
                        binding.anniversaryDate.text = ""
                    }

                    if (!it.lstCustomerIdentityInfo.isNullOrEmpty()){
                        if (!it.lstCustomerIdentityInfo[0].identityNo.isNullOrEmpty()){
                            binding.aadharNOEdt.setText(it.lstCustomerIdentityInfo[0].identityNo)
                        }else{
                            binding.aadharNOEdt.setText("")
                        }
                    }


                    Log.d("hijhgbifdbgub", "hifbi " + BuildConfig.PROMO_IMAGE_BASE + data.profilePicture.toString().replace("~", ""))

                    Glide.with(this).load(BuildConfig.PROMO_IMAGE_BASE + data.profilePicture.toString().replace("~", ""))
                        .centerCrop()
                        .optionalCircleCrop()
                        .placeholder(R.drawable.ic_default_img)
                        .into(binding.mProfileImage)

                    if (!it.lstCustomerOfficalInfoJson.isNullOrEmpty()) {

                        if (!it.lstCustomerOfficalInfoJson[0].companyName.isNullOrEmpty()){
                            binding.firmNameEdt.setText(it.lstCustomerOfficalInfoJson[0].companyName.toString())
                        }else{
                            binding.firmNameEdt.setText("")
                        }

                        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer ||
                            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer ){
                            if (!it.lstCustomerOfficalInfoJson[0].gstNumber.isNullOrEmpty()){
                                binding.gstNoEdt.setText(it.lstCustomerOfficalInfoJson[0].gstNumber.toString())
                            }else{
                                binding.gstNoEdt.setText("")
                            }
                        }

                    }
                }else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

        /*** Update Profile Observer ***/
        loginViewModel.activateCustomerLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.returnMessage.equals("1")){

                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",getString(R.string.your_profile_has_been_updated),
                    object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                        override fun onOk() {
                            callApi()
                        }

                    })

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun askPermission() {

        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList,
                    "Allow permission to access the location and camera to Keshav Cement",
                    "OK",
                    "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                        FileSelectorCallBack {
                        override fun onResponse(fileSelectorData: FileSelectorData) {
                            mProfileImagePath = fileSelectorData.responseInBase64!!
//                        fileExtenstion = fileSelectorData.extension!!

                            Log.d("gfdhrgfi", "jf " + mProfileImagePath.toString())

                            binding.mProfileImage.setImageBitmap(fileSelectorData.thumbnail)

                            profileImageUpdateApi(mProfileImagePath)
                        }
                    })

                } else {
                    // when permission denied
//                    findNavController().popBackStack()
                }
            }

    }

}