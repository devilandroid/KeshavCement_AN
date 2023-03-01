package com.loyaltyworks.keshavcement.ui.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentDashboardBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.login.LoginActivity
import com.loyaltyworks.keshavcement.ui.profile.ProfileViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.ui.splashScreen.SplashScreenViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.NewPasswordDialog
import com.loyaltyworks.keshavcement.utils.dialog.RegisterSuccessDialog
import com.permissionx.guolindev.PermissionX
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.dashboard_menu.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*


class DashboardFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    private lateinit var viewModelProductCataloge: ProductCatalogueViewModel
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    private lateinit var profileViewModel: ProfileViewModel

    private var mProfileImagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModelProductCataloge = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "DashboardView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "DashboardFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.dashEnrollment.visibility = View.GONE
            binding.dashPendingClaimRequest.visibility = View.GONE
            binding.dashCashTransferApproval.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.startSellingLayout.visibility = View.GONE
            binding.saleAndEarnLayout.visibility = View.GONE
            binding.earnPointClaimPurchaseLayout.visibility = View.VISIBLE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

            binding.pointBalanceLayout.visibility = View.VISIBLE
            binding.createdByLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.VISIBLE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.GONE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

            binding.pointBalanceLayout.visibility = View.VISIBLE
            binding.createdByLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.VISIBLE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.GONE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

            binding.pointBalanceLayout.visibility = View.VISIBLE
            binding.createdByLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashCatalogue.visibility = View.GONE
            binding.dashMyRedemption.visibility = View.GONE
            binding.dashMyEarning.visibility = View.GONE
            binding.dashOffers.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE

            binding.dashPendingClaimRequest.visibility = View.GONE
            binding.dashCashTransferApproval.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.GONE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.VISIBLE

            binding.helpLayout.visibility = View.GONE
            binding.supportExecutiveLayout.visibility = View.VISIBLE

            binding.pointBalanceLayout.visibility = View.GONE
            binding.createdByLayout.visibility = View.VISIBLE

        }

        binding.dashEnrollment.setOnClickListener(this)                 //  Dealer & Sub-Dealer & Support-Executive
        binding.dashPendingClaimRequest.setOnClickListener(this)        //  Dealer & Sub-Dealer
        binding.dashCashTransferApproval.setOnClickListener(this)       //  Dealer & Sub-Dealer
        binding.dashClaimPurchase.setOnClickListener(this)              //  Sub-Dealer
        binding.dashMyPurchaseClaim.setOnClickListener(this)            //  Engineer & Mason
        binding.dashCatalogue.setOnClickListener(this)                  //  All except Support-Executive
        binding.dashMyRedemption.setOnClickListener(this)               //  All except Support-Executive
        binding.dashMyEarning.setOnClickListener(this)                  //  All except Support-Executive
        binding.dashOffers.setOnClickListener(this)                     //  All except Support-Executive
        binding.dashWorksite.setOnClickListener(this)                   //  Engineer & Mason
        binding.dashReferEarn.setOnClickListener(this)                  //  Engineer & Mason
        binding.dashPendingRequest.setOnClickListener(this)             //  Support-Executive
        binding.dashMyActivity.setOnClickListener(this)                 //  Support-Executive

        binding.dashEarnPointClaimPurchase.setOnClickListener(this)     //  Engineer & Mason
        binding.saleAndEarn.setOnClickListener(this)                    //  Dealer & Sub-Dealer
        binding.newSale.setOnClickListener(this)                        //  Support Executive

        binding.dashRaiseTicket.setOnClickListener(this)                //  All except Support-Executive
        binding.dProfileImage.setOnClickListener(this)                //  All Type

        /*** Change Password ***/
        if (PreferenceHelper.getBooleanValue(requireContext(),BuildConfig.ForgotPasswordClicked)){
            NewPasswordDialog.showNewPasswordDialog(requireContext(),object : NewPasswordDialog.NewPasswordDialogCallBack{
                override fun onSubmit(newPassword: String, confirmPassword: String) {
                    if (!newPassword.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()){
                        LoadingDialogue.showDialog(requireContext())
                        changePasswordApi(confirmPassword)
                    }
                }

            })
        }


        getBanner()
    }

    private fun changePasswordApi(confirmPassword: String) {
        viewModel.getChangePasswordData(
            ChangePasswordRequest(
                password = confirmPassword,
                userName = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userName!!.toString(),
                userActionType = "UpdateChangedPassword"
            )
        )
    }

    private fun callApi() {
        // call API
        LoadingDialogue.showDialog(requireContext())
        viewModel.getDashboardData(
            UpdatedDashboardSingleApiRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )
    }

    private fun getBanner() {
        // call this api for banner image
        viewModel.getBannerImage(
            BannerImageRequest(
                ObjImageGallery(
                    AlbumCategoryID = "1"
                )
            )
        )
    }

    override fun onResume() {
        super.onResume()

        /*** User Active/Deactive Check ***/
        viewModelProductCataloge.getUserActiveOrNotData(UserActiveOrNotRequest(
            PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
        ))

        val packageInfo: PackageInfo = requireActivity().packageManager!!.getPackageInfo(requireActivity().packageName, 0)
        Log.d("hfjshjrf", packageInfo.versionCode.toString())

        splashScreenViewModel.checkIfUpdateAvailabe(requireContext(), packageInfo.versionCode)

        splashScreenViewModel.get_isUpdateAvailable()!!.observe(this, Observer {

            if (it) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PLAY_STORE_LINK))
                startActivity(browserIntent)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  user active or not Observer ***/
        viewModelProductCataloge.userActiveOrNotData.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it.isActive == false) {
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Account Deactivated!",
                        getString(R.string.your_account_has_been_deactivated),object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                PreferenceHelper.clear(requireContext())
                                startActivity(Intent(context, LoginActivity::class.java))
                                requireActivity().finish()
                            }
                        })
                }else{
                    callApi()
                }
            }

        })


        /*** Change Password Request ***/
        viewModel.changePasswordLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.userList.isNullOrEmpty()) {
                    val result: String = when (it.userList?.get(0)?.result) {

                        1 -> {

                            NewPasswordDialog.dismissChangePasswDialog()
                            PreferenceHelper.setBooleanValue(requireContext(),BuildConfig.ForgotPasswordClicked,false)
                            // Change password message display
                            getString(R.string.password_changed_successfully)

                        }

                        else -> {
                            getString(R.string.something_went_wrong_please_try_again_later)
                        }
                    }
                    (activity as DashboardActivity).snackBar(result, R.color.dark_blue)
                }else{
                    (activity as DashboardActivity).snackBar(getString(R.string.something_went_wrong_please_try_again_later), R.color.dark_blue)

                }
            }

        })


        /*** Dashboard Observer ***/
        viewModel.dashboardLiveData.observe(viewLifecycleOwner) {

            if (it != null) {
                LoadingDialogue.dismissDialog()
//                binding.root.dash_recycler.adapter = DashHeaderImageAdapter(it)


                if (!it.lstCustomerFeedBackJsonApi.isNullOrEmpty()) {

                    if (it.lstCustomerFeedBackJsonApi[0].customerStatus != 1) {
                        RegisterSuccessDialog.showRegisterSuccessDialog(requireContext(),false,"",
                            "Your account has been deactivated! Kindly contact your administrator.",  object :RegisterSuccessDialog.RegisterSuccessDialogCallBack{
                                override fun onOk() {
                                    PreferenceHelper.clear(requireContext())
                                    startActivity(Intent(context, LoginActivity::class.java))
                                    requireActivity().finish()
                                }

                            })

                    }

                    PreferenceHelper.setDashboardDetails(requireContext(), it)

                    binding.custType.text = it.lstCustomerFeedBackJsonApi[0].customerType.toString()
                    binding.custName.text = it.lstCustomerFeedBackJsonApi[0].firstName
                    binding.memId.text = it.lstCustomerFeedBackJsonApi[0].loyaltyId

                    /*** setting dashboard menu data ***/
                    (activity as DashboardActivity).binding.root.menu_memberType.text = it.lstCustomerFeedBackJsonApi[0].customerType
                    (activity as DashboardActivity).binding.root.menu_memberName.text = it.lstCustomerFeedBackJsonApi[0].firstName
                    (activity as DashboardActivity).binding.root.menu_membershipId.text = it.lstCustomerFeedBackJsonApi[0].loyaltyId

                    if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
                        binding.createdByType.text = "(" +  it.lstCustomerFeedBackJsonApi[0].mappedCustomerType + ")"
                        binding.createdByName.text = it.lstCustomerFeedBackJsonApi[0].mappedCustomerName!!.split("~")[1]
                        /*** Support Executive Mapped ID ***/
                        PreferenceHelper.setStringValue(requireContext(), BuildConfig.MappedCustomerIdSE, it.lstCustomerFeedBackJsonApi[0].mappedCustomerId!!)
                        PreferenceHelper.setStringValue(requireContext(), BuildConfig.MappedCustomerNameSE, it.lstCustomerFeedBackJsonApi[0].mappedCustomerType!!)
                    }

                    if (it.lstCustomerFeedBackJsonApi[0].customerImage.toString() != "null") {

                    Log.d("customerPics", "gfihgf " +BuildConfig.PROMO_IMAGE_BASE + it.lstCustomerFeedBackJsonApi[0].customerImage.toString()
                        .replace("~", "") )

                        Glide
                            .with(this)
                            .load(BuildConfig.PROMO_IMAGE_BASE + it.lstCustomerFeedBackJsonApi[0].customerImage.toString().replace("~", ""))
                            .centerCrop()
                            .optionalCircleCrop()
                            .placeholder(R.drawable.ic_default_img)
                            .into(binding.dProfileImage)

                        /*** setting dashboard menu data ***/
                        Glide
                            .with(this)
                            .load(BuildConfig.PROMO_IMAGE_BASE + it.lstCustomerFeedBackJsonApi[0].customerImage.toString().replace("~", ""))
                            .centerCrop()
                            .optionalCircleCrop()
                            .placeholder(R.drawable.ic_default_img)
                            .into((activity as DashboardActivity).binding.root.menu_profileImage)
                        }

                }

                if (!it.objCustomerDashboardList.isNullOrEmpty()) {
                    binding.pointBalance.text = it.objCustomerDashboardList[0].totalRedeemed.toString()

                    /*** setting dashboard menu data ***/
                    (activity as DashboardActivity).binding.root.menu_point_balance.text = it.objCustomerDashboardList[0].totalRedeemed.toString()

                    Log.d("fhsdhfjsd","Redeemable Point Balance : " + it.objCustomerDashboardList[0].redeemablePointsBalance.toString())
                }

            }

        }


        /*** Banner Image Observer ***/
        viewModel.bannerImageLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.objImageGalleryList.isNullOrEmpty()) {

                val imageList = ArrayList<SlideModel>() // Create image list
                imageList.clear()

                it.objImageGalleryList.forEachIndexed { index, objImageGalleryList ->
                    Log.d("jfkdsjfk",BuildConfig.PROMO_IMAGE_BASE + it.objImageGalleryList[index].imageGalleryUrl?.replace("~", ""))
                    imageList.add(SlideModel(BuildConfig.PROMO_IMAGE_BASE + it.objImageGalleryList[index].imageGalleryUrl?.replace("~", ""),
                        "", ScaleTypes.FIT))
                }

                binding.imageSlider.setImageList(imageList)

                binding.imageSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        imagePopUp(position,it.objImageGalleryList)
                    }
                })

            }
        })

        /*** Update Profile Image Observer ***/
        profileViewModel.profileImageUpdateLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnMessage == "1"){
                    callApi()
                    Toast.makeText(requireContext(), resources.getString(R.string.your_profile_image_update_successfully), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), resources.getString(R.string.failure_to_update_your_profile_image), Toast.LENGTH_SHORT).show()
                }
            }

        })


    }

    private fun imagePopUp(position: Int, objImageGalleryList: List<ObjImageGalleryList>) {
        AppController.popUpPromotionDialogue(context, position, objImageGalleryList)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.dash_enrollment ->{
                findNavController().navigate(R.id.enrollmentFragment)
            }

            R.id.dash_pending_request,
            R.id.dash_pending_claim_request ->{
                findNavController().navigate(R.id.pendingClaimRequestFragment)
            }

            R.id.dash_cash_transfer_approval ->{
                findNavController().navigate(R.id.cashTransferApprovalFragment)
            }

            R.id.dash_my_purchase_claim ->{
                findNavController().navigate(R.id.myPurchaseClaimFragment)
            }

            R.id.dash_claim_purchase ->{
                findNavController().navigate(R.id.purchaseRequestFragment)
            }

            R.id.dash_catalogue ->{
                findNavController().navigate(R.id.redemptionTypeFragment)
            }

            R.id.dash_my_redemption ->{
                findNavController().navigate(R.id.myRedemptionFragment)
            }

            R.id.dash_my_earning ->{
                findNavController().navigate(R.id.myEarningFragment)
            }

            R.id.dash_offers ->{
                findNavController().navigate(R.id.promotionsFragment)
            }

            R.id.dash_worksite ->{
                findNavController().navigate(R.id.worksiteDetailsFragment)
            }

            R.id.dash_refer_earn ->{
                findNavController().navigate(R.id.referFragment)
            }

            R.id.dash_my_activity ->{
                findNavController().navigate(R.id.myActivityFragment)
            }

            R.id.sale_and_earn ->{
                findNavController().navigate(R.id.claimFragment)
            }

            R.id.new_sale ->{
                findNavController().navigate(R.id.claimFragment)
            }

            R.id.dash_earn_point_claim_purchase ->{
                findNavController().navigate(R.id.purchaseRequestFragment)
            }

            R.id.dash_raise_ticket ->{
                findNavController().navigate(R.id.supportFragment)
            }

            R.id.d_profile_image ->{
                LoadingDialogue.dismissDialog()
                askPermission()
            }

        }
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

                            binding.dProfileImage.setImageBitmap(fileSelectorData.thumbnail)

                            profileImageUpdateApi(mProfileImagePath)
                        }
                    })

                } else {
                    // when permission denied
//                    findNavController().popBackStack()
                }
            }

    }


    private fun profileImageUpdateApi(mProfileImagePath: String) {

        LoadingDialogue.showDialog(requireContext())
        profileViewModel.setProfileImageData(
            ProfileImageUpdateRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                objCustomerJson = ObjCustomerJsonss(
                    displayImage = mProfileImagePath,
                    loyaltyId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId
                )
            )
        )
    }

}