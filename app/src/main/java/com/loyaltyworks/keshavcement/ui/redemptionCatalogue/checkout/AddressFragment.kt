package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

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
import com.loyaltyworks.keshavcement.databinding.FragmentAddressBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.profile.ProfileViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CartAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.OrderConfirmAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var viewModel: LoginRegistrationViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var viewModelProductCataloge: ProductCatalogueViewModel

//    lateinit var catalogueSaveCartDetailListResponse: List<CatalogueSaveCartDetailResponse>
    lateinit var  _lstCustomerJson: List<LstCustomerJson>

    lateinit var _catalogueSaveCartDetailListResponse: List<CatalogueSaveCartDetailResponse>

    val catalogueList : MutableList<ObjCatalogueList> = ArrayList()

    var sumofTotalPointsRequired : Int = 0

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    private lateinit var OTPNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModelProductCataloge = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_AddressView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_AddressFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        val bundle1 = this.arguments
        if (bundle1 != null) {
//            catalogueSaveCartDetailListResponse = arguments?.getSerializable("CartDataList") as  List<CatalogueSaveCartDetailResponse>

            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = requireArguments().getString("SelectedCustomerPartyLoyaltyID").toString()

            if( requireArguments().getSerializable("CustomerProfileData") != null){
                _lstCustomerJson = listOf(requireArguments().getSerializable("CustomerProfileData") as LstCustomerJson)

                binding.addressInfo.text = _lstCustomerJson[0].firstName + "\n" + _lstCustomerJson[0].address1 + "\n" + _lstCustomerJson[0].districtName + "\n" + _lstCustomerJson[0].stateName + "\n" + _lstCustomerJson[0].countryName + " - " + _lstCustomerJson[0].zip + "\n" + "PH - " + _lstCustomerJson[0].mobile+ "\n" + "Email - " + _lstCustomerJson[0].email
            }else{
                callAddressApi()
            }
        }




        CartListApi()
        SetUpObserver()

        binding.editBtn.setOnClickListener {
            if (!_lstCustomerJson.isNullOrEmpty()) {
                val bundle = Bundle()
                bundle.putString("SelectedCustomerUserID",actorID)
                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                bundle.putSerializable("CustomerProfileData", _lstCustomerJson[0])
                findNavController().navigate(R.id.action_addressFragment_to_editAddressFragment, bundle)
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
                        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),getString(R.string.redemption),getString(R.string.enter_otp_to_complete_the_redemption),PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile)
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

        /*** Cart List Observer ***/
        cartViewModel.cartListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (!it.catalogueSaveCartDetailListResponse.isNullOrEmpty()) {
                    binding.orderConfirmRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE


                    _catalogueSaveCartDetailListResponse = it.catalogueSaveCartDetailListResponse
                    binding.orderConfirmRecycler.visibility = View.VISIBLE
                    binding.orderConfirmRecycler.adapter = OrderConfirmAdapter(it.catalogueSaveCartDetailListResponse)

                    sumofTotalPointsRequired = it.catalogueSaveCartDetailListResponse[0].sumofTotalPointsRequired!!.floatToInt()

                    binding.totalPoints.setText(sumofTotalPointsRequired.toString())
                    var remainBalance = PreferenceHelper.getStringValue(requireContext(), BuildConfig.RedeemablePointsBalance).toInt() - sumofTotalPointsRequired
                    binding.remainingBalance.text = "Point balance " + remainBalance +" after this purchase"


                } else {
                    binding.orderConfirmRecycler.visibility = View.GONE
                    binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    binding.totalPoints.text = "-"
                }
            }
        })

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
                        getString(R.string.your_account_has_been_deactivated),object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.redeemSwapBtn.changeState(false,true)
                            }
                        })

                }
                if (message.split("-").toTypedArray()[1].toInt() > 0)  {


                    sendSuccessSMSToUser()

//                    Handler(Looper.getMainLooper()).postDelayed({
                    LoadingDialogue.dismissDialog()
//                    }, 3000)

                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",
                        getString(R.string.you_have_succefully_redeemed_product),object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
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
                        getString(R.string.something_went_wrong_please_try_again_later),object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.redeemSwapBtn.changeState(false,true)
                            }
                        })
                }

            }else{
                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
                    getString(R.string.something_went_wrong_please_try_again_later),object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
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

    }

    private fun callAddressApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.setProfileRequest(
            ProfileRequest(
                ActionType = "6",
                CustomerId = actorID
            )
        )
    }

    private fun CartListApi() {
        cartViewModel.getCartListData(
            CartRequest(
                actionType = "2",
                loyaltyID = loyaltyId,
                domain = "KESHAV_CEMENT",
                partyLoyaltyID = partyLoyaltyID
            )
        )
    }

    private fun SendOtpRequest() {

        viewModel.setOTPRequest(
            SaveAndGetOTPDetailsRequest(
                merchantUserName = BuildConfig.MerchantName,
                mobileNo = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerMobile),
                userId = actorID,
                userName = loyaltyId,
                name = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName)
            )
        )

    }

    private fun SubmitReddemProcess() {

        LoadingDialogue.showDialog(requireContext())

        viewModelProductCataloge.getUserActiveOrNotData(UserActiveOrNotRequest(
            actorID
        ))
    }

    private fun setRedeemCatalogue() {
        _catalogueSaveCartDetailListResponse.forEachIndexed { index, catalogueSaveCart ->
            val catalogue = ObjCatalogueList()

            catalogue.catalogueId = catalogueSaveCart.catalogueId
            catalogue.deliveryType = "In Store"
            catalogue.hasPartialPayment = false
            catalogue.noOfPointsDebit = catalogueSaveCart.pointsRequired
            catalogue.noOfQuantity = catalogueSaveCart.noOfQuantity
            catalogue.pointsRequired = catalogueSaveCart.pointsRequired
            catalogue.productCode = catalogueSaveCart.productCode
            catalogue.productImage = catalogueSaveCart.productImage
            catalogue.productName = catalogueSaveCart.productName
            catalogue.redemptionDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
            catalogue.redemptionId = 0
            catalogue.redemptionRefno = catalogueSaveCart.redemptionRefno
            catalogue.redemptionTypeId = 1
            catalogue.status = 0
            catalogue.customerCartId = catalogueSaveCart.customerCartId
            catalogue.catogoryId = catalogueSaveCart.categoryID
            catalogue.termsCondition = catalogueSaveCart.termsCondition
            catalogue.totalCash = catalogueSaveCart.totalCash
            catalogue.vendorId = catalogueSaveCart.vendorId
            catalogue.vendorName = catalogueSaveCart.vendorName

            catalogueList.add(catalogue)
        }

        viewModelProductCataloge.getSaveCatalogueRedeem(SaveCatalogueRedemptionRequest(
            actionType = 51,
            actorId = actorID.toInt(),
            memberName = PreferenceHelper.getStringValue(requireContext(),BuildConfig.SelectedCustomerName),
            dealerLoyaltyId = partyLoyaltyID,
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
                loyaltyID = loyaltyId,
                mobile = loyaltyId,
                pointBalance = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance,
                redeemedPoint = _catalogueSaveCartDetailListResponse[0].sumofTotalPointsRequired,
            )
        )
    }

}