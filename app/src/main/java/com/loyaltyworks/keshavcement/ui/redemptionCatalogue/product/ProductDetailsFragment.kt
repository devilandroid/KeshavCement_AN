package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductDetailsBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class ProductDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: ProductCatalogueViewModel

    private lateinit var objCataloguee: ObjCataloguee

    var actorID = ""
    var loyaltyId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ProductDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ProductDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        val bundle1 = this.arguments
        if (bundle1 != null) {
            objCataloguee = arguments?.getSerializable("CatalogueProduct") as ObjCataloguee
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
        }

        binding.addCartBtn.setOnClickListener(this)
//        binding.viewCartBtn.setOnClickListener(this)
        binding.cart.setOnClickListener(this)
        binding.backBtn.setOnClickListener(this)

        CartCountApi()
        SetUpUi()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Cart Count Observer ***/
        viewModel.cartCountLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null){

                binding.cartCount.text = it.totalCartCatalogue.toString()
            }
        })

        /***  Add to Cart Response  ***/
        viewModel.addToCartListingLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue == 1) {
                    CartCountApi()
//                    binding.viewCartLayout.visibility = View.VISIBLE
                    Toast.makeText(requireContext(),getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(requireContext(), getString(R.string.failed_to_add_cart), Toast.LENGTH_SHORT).show()
                }
            }

        })

        /***  Add Planner Observer  ***/
        viewModel.addPlannerLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()

                if (it != null && it.returnValue == 1){
                    Toast.makeText(requireContext(),getString(R.string.already_added_to_planner), Toast.LENGTH_SHORT).show()
                }else if (it.returnValue!! > 0){
                    Toast.makeText(requireContext(),getString(R.string.added_to_planner), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun SetUpUi() {

        Log.d("hbvjhfbv","ggfrhegfjh " + BuildConfig.CATALOGUE_IMAGE_BASE + objCataloguee.productImage)
        Glide.with(this).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + objCataloguee.productImage)
            .into(binding.productImage)


        binding.catetogryType.text = "Category / " + objCataloguee.catogoryName
        binding.productName.text = objCataloguee.productName
        binding.points.text = objCataloguee.pointsRequired.toString()
        binding.descriptionTxt.text = objCataloguee.productDesc
        binding.termTxt.text = objCataloguee.termsCondition

        /*** set View cart layout data ***/
        /* Glide.with(this).asBitmap()
             .error(R.drawable.ic_default_img)
             .load(BuildConfig.CATALOGUE_IMAGE_BASE + objCataloguee.productImage)
             .into(binding.viewCartProdImg)
         binding.viewCartPoints.text = objCataloguee.pointsRequired.toString()
         binding.viewCartQuantity.text = objCataloguee.noOfQuantity.toString()*/


        if (objCataloguee.pointsRequired!!.toInt() <=  PreferenceHelper.getStringValue(requireContext(),BuildConfig.RedeemablePointsBalance).toInt() ) {

            if (objCataloguee.catalogueIdExist == "1") {
                binding.addCartText.text = getString(R.string.added_to_cart)
                binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)

//                binding.viewCartLayout.visibility = View.VISIBLE

            } else if (objCataloguee.catalogueIdExist == "0") {
                binding.addCartText.text = getString(R.string.add_to_cart)
                binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)

            }

        }else {

            if (objCataloguee.isPlanner == true){
                binding.addCartBtn.visibility = View.VISIBLE


                if (objCataloguee.isAddPlanner == true) {
                    binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
                    binding.addCartText.text = getString(R.string.added_to_planner)
                }else{
                    binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
                    binding.addCartText.text = getString(R.string.add_to_planner)
                }

            }else{
                binding.addCartBtn.visibility = View.INVISIBLE
            }

        }
    }

    private fun CartCountApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCartCountData(
            CartCountRequest(
                actionType = "1",
                loyaltyID = loyaltyId

            )
        )
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_cart_btn ->{
                if(BlockMultipleClick.click()) return
                onAddToCartClick()

            }

            R.id.cart ->{
                val bundle = Bundle()
                bundle.putString("SelectedCustomerUserID",actorID)
                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                findNavController().navigate(R.id.action_productDetailsFragment_to_cartFragment,bundle)
            }

            R.id.back_btn ->{
                findNavController().popBackStack()
            }
        }
    }

    private fun onAddToCartClick() {
        if (objCataloguee.pointsRequired!!.toInt() <=  PreferenceHelper.getStringValue(requireContext(),BuildConfig.RedeemablePointsBalance).toInt() ) {


            if (objCataloguee.catalogueIdExist == "0") {
                if (PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].verificationStatus != 6) {

                    addToCartApi()
                    objCataloguee.catalogueIdExist = "1"
                    binding.addCartText.text = getString(R.string.added_to_cart)
                    binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)

                }else {
                    onHoldAddtoCart()

                }

            } else {
                Toast.makeText(requireContext(), getString(R.string.already_added_in_cart), Toast.LENGTH_SHORT).show()
            }

        }else {
            onAddPlannerListener()
            binding.addCartText.text = getString(R.string.added_to_planner)
            binding.addCartBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)

        }
    }

    private fun addToCartApi() {

        LoadingDialogue.showDialog(requireContext())

        val catalogueSaveCartDetialsList: MutableList<CatalogueSaveCartDetailRequest> = ArrayList()

        catalogueSaveCartDetialsList.clear()

        val catalogueSaveCartDetailsRequest = CatalogueSaveCartDetailRequest()

        catalogueSaveCartDetailsRequest.catalogueId = objCataloguee.catalogueId.toString()
        catalogueSaveCartDetailsRequest.comments = objCataloguee.comments
        catalogueSaveCartDetailsRequest.noOfQuantity = "1"
        catalogueSaveCartDetailsRequest.deliveryType = "1"

        catalogueSaveCartDetialsList.add(catalogueSaveCartDetailsRequest)

        viewModel.getAddToCartData(
            AddToCartRequest(
                actionType = "1",
                actorId = actorID,
                merchantId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.merchantId!!.toString(),
                loyaltyID = loyaltyId,

                catalogueSaveCartDetailListRequest = catalogueSaveCartDetialsList
            )
        )

    }

    private fun onAddPlannerListener() {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getPlannerAddData(
            PlannerAddRequest(
                actionType = 0,
                actorId = actorID,

                ObjCatalogueDetailsy(
                    catalogueId =objCataloguee.catalogueId
                )

            )
        )
    }

    private fun onHoldAddtoCart() {
        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.not_allowed_to_redeem_contact_administrator), object :AppController.SuccessCallBack{
            override fun onOk() {

            }

        })
    }

}