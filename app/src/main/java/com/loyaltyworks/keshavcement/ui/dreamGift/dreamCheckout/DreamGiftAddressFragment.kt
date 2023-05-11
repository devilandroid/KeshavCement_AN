package com.loyaltyworks.keshavcement.ui.dreamGift.dreamCheckout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentDreamGiftAddressBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog


class DreamGiftAddressFragment : Fragment() {
    private lateinit var binding: FragmentDreamGiftAddressBinding
    private lateinit var viewModel: LoginRegistrationViewModel
    private lateinit var viewModelProductCataloge: ProductCatalogueViewModel

    lateinit var  _lstCustomerJson: List<LstCustomerJson>
    lateinit var lstDreamGift: LstDreamGift
    val catalogueList: MutableList<ObjCatalogueList> = ArrayList()

    private lateinit var OTPNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        viewModelProductCataloge = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentDreamGiftAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_DreamGiftAddressView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_DreamGiftAddressFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (arguments != null) {
            lstDreamGift = requireArguments().getSerializable("DreamGift") as LstDreamGift

            if( requireArguments().getSerializable("CustomerProfileData") != null){
                _lstCustomerJson = listOf(requireArguments().getSerializable("CustomerProfileData") as LstCustomerJson)
                binding.addressInfo.text = _lstCustomerJson[0].firstName + "\n" + _lstCustomerJson[0].address1 + "\n" + _lstCustomerJson[0].districtName + "\n" + _lstCustomerJson[0].stateName + "\n" + _lstCustomerJson[0].countryName + " - " + _lstCustomerJson[0].zip + "\n" + "PH - " + _lstCustomerJson[0].mobile+ "\n" + "Email - " + _lstCustomerJson[0].email
            }else{
                callAddressApi()
            }

        }

        Log.d("bfhrbfuhr","redeemable balance : " + PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt())
        Log.d("bfhrbfuhr","points required : " + lstDreamGift.pointsRequired)
        binding.totalPoints.text = lstDreamGift.pointsRequired.toString()
        var remainBalance = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt() - lstDreamGift.pointsRequired!!
        binding.remainingBalance.text = "Point balance " + remainBalance +" after this purchase"


        SetUpObserver()

        binding.editBtn.setOnClickListener {
            if (!_lstCustomerJson.isNullOrEmpty()) {
                val bundle = Bundle()
//                bundle.putString("SelectedCustomerUserID",actorID)
//                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
//                bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                bundle.putSerializable("CustomerProfileData", _lstCustomerJson[0])
                bundle.putSerializable("DreamGift", lstDreamGift)
                findNavController().navigate(R.id.action_dreamGiftAddressFragment_to_dreamGiftEditAddressFragment, bundle)
            }else {
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        }

        binding.redeemSwapBtn.setOnStateChangeListener {
            if (it){
                if (!_lstCustomerJson.isNullOrEmpty()) {
                    if (_lstCustomerJson[0].stateName == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].address1 == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].firstName == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].mobile == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].districtName == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].zip == null)  {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }

                    if (_lstCustomerJson[0].address1!!.isEmpty() || _lstCustomerJson[0].firstName!!.isEmpty() || _lstCustomerJson[0].mobile!!
                            .isEmpty() || _lstCustomerJson[0].stateName!!
                            .isEmpty() || _lstCustomerJson[0].districtName!!
                            .isEmpty()|| _lstCustomerJson[0].zip!!
                            .isEmpty()  /*|| savedCustomerDetails.getEmail().isEmpty()*/) {
                        Toast.makeText(context, getString(R.string.shipping_address_required), Toast.LENGTH_SHORT).show()
                        binding.redeemSwapBtn.changeState(false,true)
                        return@setOnStateChangeListener
                    }else{

                        SendOtpRequest()
                        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.redemption),getString(R.string.enter_otp_to_complete_the_redemption),PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString()
                            ,"Redeem",object : RedeemOTPDialog.RedeemOTPDialogCallBack{
                                override fun onOk() {
                                    binding.redeemSwapBtn.changeState(false,true)
                                }

                                override fun onRedeemClick(otp: String) {
                                    if(otp == "123456" /*OTPNumber*/){
                                        RedeemOTPDialog.hideDialog()
                                        SubmitReddemProcess()
                                    }else{
                                        Toast.makeText(requireContext(),getString(R.string.invalid_otp),Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun resendOTP() {
                                    SendOtpRequest()
                                }
                            })
                    }
                }else {
                    Toast.makeText(requireContext(),getString(R.string.please_add_shipping_address), Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    fun String.floatToInt(): Int {
        return this.toFloat().toInt()
    }

    private fun SetUpObserver() {
        viewModel.getProfileResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()
            if (it != null) {
                if (!it.lstCustomerJson.isNullOrEmpty()) {
                    val data = it.lstCustomerJson!![0]

                    _lstCustomerJson = it.lstCustomerJson!!

                    var districtName = ""
                    var stateName = ""

                    if (data.districtName != null && !data.districtName!!.isEmpty()) {
                        districtName = data.districtName!!
                    }else {
                        districtName = ""
                    }

                    if (data.stateName != null && !data.stateName!!.isEmpty()) {
                        stateName = data.stateName!!
                    }else {
                        stateName = ""
                    }
                    Log.d("ebhbfhr","district name : "+ districtName)

                    binding.addressInfo.text = data.firstName + "\n" + data.address1 + "\n" + districtName + "\n" + stateName + "\n" + data.countryName + " - " + data.zip + "\n" + "PH - " + data.mobile+ "\n" + "Email - " + data.email

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  OTP observer ***/
        viewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
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

        /***  user active or not oberser ***/
        viewModelProductCataloge.userActiveOrNotData.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it.isActive == true) {
                    //redeem process
                    setRedeemCatalogue()
                } else {
                    LoadingDialogue.dismissDialog()
                    Toast.makeText(requireContext(), getString(R.string.your_account_has_been_deactivated), Toast.LENGTH_SHORT).show()
                }
            }

        })

        /***   Save Catalogue Redeem  Observer  ***/
        viewModelProductCataloge.saveCatalogueRedeemData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty())  {

                val message = it.returnMessage.toString()
                Log.d("hrfijhrof","jrhfiju "+ message)

                if (message != "-1" && message.split("-").toTypedArray()[1] == "00") {

//                    LoadingDialogue.dismissDialog()

                    // If its 00 , then member is deactivated.
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
                        getString(R.string.your_account_has_been_deactivated),object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.redeemSwapBtn.changeState(false,true)
                            }
                        })

                }
                if (message.split("-").toTypedArray()[1].toInt() > 0)  {


                    sendSuccessSMSToUser()
                    updateDreamGift()
//                    Handler(Looper.getMainLooper()).postDelayed({
                    LoadingDialogue.dismissDialog()
//                    }, 3000)

                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",
                        getString(R.string.you_have_succefully_redeemed_product),object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.redeemSwapBtn.changeState(false,true)
                                requireActivity().finish();
                                requireActivity().overridePendingTransition( 0, 0);
                                startActivity(requireActivity().intent);
                                requireActivity().overridePendingTransition( 0, 0);
                            }

                        })


                } else {
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
                        getString(R.string.something_went_wrong_please_try_again_later),object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.redeemSwapBtn.changeState(false,true)
                            }
                        })
                }

            }else{
                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
                    getString(R.string.something_went_wrong_please_try_again_later),object :
                        ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                        override fun onOk() {
                            binding.redeemSwapBtn.changeState(false,true)
                        }
                    })

            }

        })

        /*** send success sms to user ***/
        viewModelProductCataloge.sendSuccessSMSAlert.observe(viewLifecycleOwner, Observer {
            if (it != null)  {
                if (it == true) {
                    Log.d("UserSendSMS", "Success: ")
                }else {
                    Log.d("UserSendSMS", "Failure: ")
                }
            }
        })

        /***  Update dream gift observer  ***/
        viewModelProductCataloge.dreamGiftRemoveLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it?.returnValue != null) {
                if (it.returnValue > 0) {
                    Log.d("updateDreamGift","Success")
                }else {
                    Log.d("updateDreamGift","Failed")
                }
            }
        })

    }

    private fun updateDreamGift() {
        viewModelProductCataloge.getDreamGiftRemoveData(
            DreamGiftRemoveRequest(
                actionType = 4,
                dreamGiftId = lstDreamGift.dreamGiftId,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                giftStatusId = 5
            )
        )
    }

    private fun callAddressApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.setProfileRequest(
            ProfileRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )
    }

    private fun SendOtpRequest() {

        viewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString(),
                userId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                userName = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId,
                name = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName.toString(),
                oTPType = "OTPForRewardCardsENCashAuthorization"
            )
        )

    }

    private fun SubmitReddemProcess() {

        LoadingDialogue.showDialog(requireContext())

        viewModelProductCataloge.getUserActiveOrNotData(
            UserActiveOrNotRequest(
                PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
        )
        )
    }


    private fun setRedeemCatalogue() {

        LoadingDialogue.showDialog(requireContext())

        val catalogue = ObjCatalogueList()

        catalogue.dreamGiftId = lstDreamGift.dreamGiftId
        catalogue.loyaltyId =
            PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId.toString()
        catalogue.noOfPointsDebit = lstDreamGift.pointsRequired.toString()
        catalogue.noOfQuantity = 1
        catalogue.pointsRequired = lstDreamGift.pointsRequired.toString()
        catalogue.pointBalance = lstDreamGift.pointsBalance.toString()
        catalogue.productName = lstDreamGift.dreamGiftName
        catalogue.redemptionTypeId = 3

        catalogueList.add(catalogue)



        viewModelProductCataloge.getSaveCatalogueRedeem(SaveCatalogueRedemptionRequest(
            actionType = 51,
            actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
            memberName = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName.toString(),
//            dealerLoyaltyId = partyLoyaltyID,
            objCatalogueList = catalogueList,
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


    private fun sendSuccessSMSToUser() {
        viewModelProductCataloge.getSendSucessSMS(
            SendSMSForSucessRedemReq(
                customerName = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName),
                emailID = _lstCustomerJson[0].email,
                loyaltyID = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId,
                mobile = PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].loyaltyId,
                pointBalance = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance,
                redeemedPoint = lstDreamGift.pointsRequired.toString(),
            )
        )
    }

}