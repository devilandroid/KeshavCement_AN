package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

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
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCartBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CartAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.io.Serializable
import java.util.ArrayList


class CartFragment : Fragment(), View.OnClickListener, CartAdapter.DeleteProductCallback {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    lateinit var _catalogueSaveCartDetailListResponse: List<CatalogueSaveCartDetailResponse>

    var sumofTotalPointsRequired : Int = 0

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    var isRedeemable : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CartView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CartFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (arguments != null){
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = requireArguments().getString("SelectedCustomerPartyLoyaltyID").toString()
        }

        binding.checkoutBtn.setOnClickListener(this)
        CartListApi()
    }

    fun String.floatToInt(): Int {
        return this.toFloat().toInt()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Cart List Observer ***/
        viewModel.cartListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (!it.catalogueSaveCartDetailListResponse.isNullOrEmpty()) {
                    binding.cartRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE
                    binding.checkoutBtn.isEnabled = true
                    binding.checkoutBtn.setBackgroundResource(R.drawable.corner_bg_dark)

                    _catalogueSaveCartDetailListResponse = it.catalogueSaveCartDetailListResponse
                    binding.cartRecycler.visibility = View.VISIBLE
                    binding.cartRecycler.adapter = CartAdapter(it.catalogueSaveCartDetailListResponse, this)

                    sumofTotalPointsRequired = it.catalogueSaveCartDetailListResponse[0].sumofTotalPointsRequired!!.floatToInt()
                    binding.totalPoints.setText(sumofTotalPointsRequired.toString())

                    if (it.catalogueSaveCartDetailListResponse[0].isRedeemable != null){
                        isRedeemable = it.catalogueSaveCartDetailListResponse[0].isRedeemable!!
                    }

                    if (it.catalogueSaveCartDetailListResponse[0].isRedeemable == 1){
                        binding.checkoutBtn.visibility = View.VISIBLE
                    }else{
                        binding.checkoutBtn.visibility = View.GONE
                    }


                } else {
                    binding.cartRecycler.visibility = View.GONE
                    binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    binding.totalPoints.text = "-"
                    binding.checkoutBtn.isEnabled = false
                    binding.checkoutBtn.setBackgroundResource(R.drawable.corner_bg_grey)
//                    Toast.makeText(this, "Cart Empty", Toast.LENGTH_SHORT).show()
                }
            }
        })

        /*** Cart Count Update Observer ***/
        viewModel.getCartUpdateLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()

                if (it != null && it.returnMessage == "1") {
                    Toast.makeText(requireContext(), getString(R.string.product_quantity_updated), Toast.LENGTH_SHORT)
                        .show()
                    CartListApi()

                } else {
                    Toast.makeText(requireContext(), getString(R.string.failed_to_update_quantity), Toast.LENGTH_SHORT).show()
                }
            }


        })

        /*** Cart Item Remove Observer ***/
        viewModel.getCartRemoveLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnMessage == "1") {
                    Toast.makeText(requireContext(), getString(R.string.product_successfully_remove_from_cart), Toast.LENGTH_SHORT)
                        .show()
                    CartListApi()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.failed_to_remove_please_try_again), Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

    }

    private fun CartListApi() {

        viewModel.getCartListData(
            CartRequest(
                actionType = "2",
                loyaltyID = loyaltyId,
                domain = "KESHAV_CEMENT",
                partyLoyaltyID = partyLoyaltyID
            )
        )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.checkout_btn ->{

                if (isRedeemable == 1){
                    Log.d("sdvhiqdi","ehbifhew : " + PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus )
                    if (PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 1) {
                        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.RedeemablePointsBalance).toInt() >= sumofTotalPointsRequired){
                            val bundle = Bundle()
                            bundle.putString("SelectedCustomerUserID",actorID)
                            bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                            bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                            bundle.putSerializable("CartDataList", _catalogueSaveCartDetailListResponse as Serializable)
                            findNavController().navigate(R.id.action_cartFragment_to_addressFragment, bundle)
                        }else{
                            Toast.makeText(requireContext(), getString(R.string.insufficient_point_balance_to_redeem), Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(requireContext(), getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    /***  Cart Increase interface callback ***/
    override fun onQuntityIncDescCallback(
        catalogueCartList: CatalogueSaveCartDetailResponse,
        Qty: Int,
        isClick: Boolean
    ) {

        if (isClick) {
            val customerList: ArrayList<CustomerCart> = ArrayList<CustomerCart>()

            val customerCart = CustomerCart()

            customerCart.CustomerCartId = catalogueCartList.customerCartId.toString()
            customerCart.Quantity = Qty.toString()
            customerList.add(customerCart)

            viewModel.getCartUpdateListing(
                UpdateQuantityRequest(
                    ActionType = "3",
                    ActorId = actorID,
                    PartyLoyaltyID = partyLoyaltyID,

                    CustomerCartId = catalogueCartList.customerCartId.toString(),
                    CustomerCartList = customerList.toList()
                )
            )

        }

    }


    override fun onRemoveCartItemCallback(
        position: Int,
        cartList: CatalogueSaveCartDetailResponse
    ) {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getCartRemoveItem(
            RemoveCartProductRequest(
                ActionType = "4",
                ActorId = actorID,
                CustomerCartId = cartList.customerCartId.toString(),
                PartyLoyaltyID = partyLoyaltyID
            )
        )
    }

}