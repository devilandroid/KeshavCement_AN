package com.loyaltyworks.keshavcement.ui.wishlist

import android.os.Bundle
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
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentWishlistDetailsBinding
import com.loyaltyworks.keshavcement.model.AddToCartRequest
import com.loyaltyworks.keshavcement.model.CatalogueSaveCartDetailRequest
import com.loyaltyworks.keshavcement.model.ObjCatalogues
import com.loyaltyworks.keshavcement.model.RemovePlannerRequest
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue

class WishlistDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding:FragmentWishlistDetailsBinding
    private lateinit var viewModel: WishlistViewModel
    private lateinit var viewModel2: ProductCatalogueViewModel

    private lateinit var objCatalogues: ObjCatalogues

    var catalogueid: Int = 0
    var redemptionPlannerId: Int = 0

    var catalogueIdExist: String = "0"

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentWishlistDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = requireArguments().getString("SelectedCustomerPartyLoyaltyID").toString()
            objCatalogues = arguments?.getSerializable("plannerDetails") as ObjCatalogues
        }

        binding.plannerRemoveBtn.setOnClickListener(this)
        binding.plannerRedeemBtn.setOnClickListener(this)

        catalogueid = objCatalogues.catalogueId!!

        binding.plannerProdName.text = objCatalogues.productName.toString()
        binding.plannerProdPnts.text =  objCatalogues.pointsRequired.toString()
        binding.plannerRedeemableProdPnts.text = this.resources.getString(R.string.redeemable_points) +" "+ objCatalogues.redeemablePointBalance.toString()
        binding.plannerBalanceMsg.text = objCatalogues.redeemablePointBalance.toString()
        binding.plannerAvgMsg.text =  objCatalogues.redeemableAverageEarning.toString()

        Glide.with(this)
            .load(
                BuildConfig.CATALOGUE_IMAGE_BASE + objCatalogues.productImage.toString()
            )
            .placeholder(R.drawable.ic_default_img)
            .fitCenter()
            .into(binding.plannerImage)

        redemptionPlannerId = objCatalogues.redemptionPlannerId!!

        val points = objCatalogues.pointsRequired!! - objCatalogues.redeemablePointBalance!!
        if (objCatalogues.pointReqToAcheiveProduct!! <= 0 && objCatalogues.redeemablePointBalance!! >= objCatalogues.pointsRequired!!){

            binding.plannerHintText.text = this.resources.getString(R.string.congratulations_you_are_eligible_to_redeem_your_redemption_planner_product)
            binding.plannerRedemMsg.text = this.resources.getString(R.string.you_are_eligible_to_redeem_this_product)

            binding.plannerRedeemBtn.isEnabled = true
            binding.plannerRedeemBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
        }else{
            binding.plannerHintText.text = this.resources.getString(R.string.you_need) +" "+ points + " " + this.resources.getString(R.string.more_redeemable_points_to_redeem_this_product)
            binding.plannerRedemMsg.text = this.resources.getString(R.string.your_redeemable_points_to_redeem_this_product)+" "+ objCatalogues.productName +" "+this.resources.getString(R.string.isin)+" "+ objCatalogues.actualRedemptionDate.toString()
            binding.plannerRedeemBtn.isEnabled = false
            binding.plannerRedeemBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
        }

        if (objCatalogues.isRedeemable == 1){
            binding.plannerRedeemBtn.visibility = View.VISIBLE
        }else{
            binding.plannerRedeemBtn.visibility = View.GONE
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Planner Remove Observer  ***/
        viewModel.plannerRemoveLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue ==1){
                    Toast.makeText(requireContext(), this.getString(R.string.planner_has_been_removed_successfully), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(requireContext(), this.getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }

        })

        /***  Planner Redeem Observer  ***/
        viewModel2.addToCartListingLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue == 1) {
//                Toast.makeText(this,"Planner redeem successfully !", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,CartActivity::class.java))
                    detailsRemovePlannerApi()
                    LoadingDialogue.dismissDialog()
                    val bundle = Bundle()
                    bundle.putString("SelectedCustomerUserID",actorID)
                    bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                    bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                    findNavController().navigate(R.id.action_wishlistDetailsFragment_to_cartFragment,bundle)
                }else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }

        })

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.planner_remove_btn -> {
                if(BlockMultipleClick.click()) return
                detailsRemovePlannerApi()
            }

            R.id.planner_redeem_btn -> {
                if(BlockMultipleClick.click()) return
                catalogueIdExist = objCatalogues.productCode.toString().split("~")[1]

                if (catalogueIdExist == "0"){
                    catalogueIdExist = "1"
                    detailsRedeem()
                }else{
                    Toast.makeText(requireContext(),getString(R.string.already_added_in_cart), Toast.LENGTH_SHORT).show()
                }


            }
        }
    }

    private fun detailsRedeem() {
        val catalogueSaveCartDetialsList: MutableList<CatalogueSaveCartDetailRequest> = ArrayList()
        catalogueSaveCartDetialsList.clear()

        val catalogueSaveCartDetailsRequest = CatalogueSaveCartDetailRequest()

        catalogueSaveCartDetailsRequest.catalogueId = catalogueid.toString()
        catalogueSaveCartDetailsRequest.noOfQuantity = "1"
        catalogueSaveCartDetailsRequest.deliveryType = "1"
        catalogueSaveCartDetialsList.add(catalogueSaveCartDetailsRequest)

        redeemApi(catalogueSaveCartDetialsList)
    }

    private fun redeemApi(catalogueSaveCartDetialsList: MutableList<CatalogueSaveCartDetailRequest>) {
        viewModel2.getAddToCartData(
            AddToCartRequest(
                actionType = "1",
                actorId = actorID,
                merchantId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.merchantId!!.toString(),
                loyaltyID = loyaltyId,
                partyLoyaltyID = partyLoyaltyID,
                catalogueSaveCartDetailListRequest = catalogueSaveCartDetialsList

            )
        )
    }

    private fun detailsRemovePlannerApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getPlannerRemoveData(
            RemovePlannerRequest(
                actionType = 17,
                actorId = actorID,
                redemptionPlannerId = redemptionPlannerId,
                partyLoyaltyID = partyLoyaltyID
            )
        )
    }

}