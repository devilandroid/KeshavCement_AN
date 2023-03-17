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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentWishlistBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.ui.wishlist.adapter.WishlistAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue

class WishlistFragment : Fragment(), View.OnClickListener, WishlistAdapter.OnPlannerCallBack {
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var viewModel: WishlistViewModel
    private lateinit var viewModel2: ProductCatalogueViewModel

    var plannerId:Int = 0

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempPlannerList = java.util.ArrayList<ObjCatalogues>()
    var currentList = java.util.ArrayList<ObjCatalogues>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mPlannerLayoutManager: LinearLayoutManager? = null
    var plannerAdapter: RecyclerView.Adapter<*>? = null

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
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_WishlistView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_WishlistFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)


        if (arguments != null){
            actorID = requireArguments().getString("SelectedCustomerUserID").toString()
            loyaltyId = requireArguments().getString("SelectedCustomerLoyltyID").toString()
            partyLoyaltyID = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId.toString()
        }else{
            actorID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            loyaltyId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId.toString()
            partyLoyaltyID = ""
        }

        binding.addPlannerBtn.setOnClickListener(this)

        // use a linear layout manager
        mPlannerLayoutManager = LinearLayoutManager(requireContext())
        mPlannerLayoutManager!!.isAutoMeasureEnabled = true
        mPlannerLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.plannerRecylerView.layoutManager = mPlannerLayoutManager
        binding.plannerRecylerView.isNestedScrollingEnabled = true
        binding.plannerRecylerView.setHasFixedSize(false)
        binding.plannerRecylerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.plannerRecylerView.itemAnimator = DefaultItemAnimator()

        loadPlannerListObserver()
        setFileterSpinners()
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mPlannerLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.plannerRecylerView.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        callApi( 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    private fun callApi(startIndex: Int) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()

        LoadingDialogue.showDialog(requireContext())
        viewModel.getPlannerData(
            PlannerRequest(
                actionType = "6",
                actorId = actorID,
                startIndex = startIndex,
                pageSize = limit,
                partyLoyaltyID = partyLoyaltyID,
                domain = "KESHAV_CEMENT"

            )
        )
    }

    private fun loadPlannerListObserver() {
        /***  Planner List Observer  ***/
        viewModel.plannerLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it!=null && !it.objCatalogueList.isNullOrEmpty()){
                    binding.plannerRecylerView.visibility = View.VISIBLE
                    binding.emptyPlannerHost.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempPlannerList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        plannerAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (planner in it.objCatalogueList){
                        tempPlannerList.add(tempPlannerList.size,planner)
                    }

                    currentList.clear()
                    currentList.addAll(tempPlannerList)

                    if (!isLoaded) {
                        isLoaded = true
                        plannerAdapter = WishlistAdapter(currentList, this)
                        binding.plannerRecylerView.adapter = plannerAdapter
                    }else{
                        plannerAdapter!!.notifyDataSetChanged()
                    }

                }else{
                    if (page == 1){
                        binding.plannerRecylerView.visibility = View.GONE
                        binding.emptyPlannerHost.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        /***  Planner Remove Observer  ***/
        viewModel.plannerRemoveLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue ==1){
                    Toast.makeText(requireContext(), this.getString(R.string.planner_has_been_removed_successfully), Toast.LENGTH_SHORT).show()
                    listFull = false
                    currentList.clear()
                    callApi(1)

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
                    removePlanner(plannerId)
                    LoadingDialogue.dismissDialog()
                    val bundle = Bundle()
                    bundle.putString("SelectedCustomerUserID",actorID)
                    bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                    bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                    findNavController().navigate(R.id.cartFragment,bundle)
                }else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onPlannerDetailsClickResponse(itemView: View, objCatalogues: ObjCatalogues) {
        val bundle = Bundle()
        bundle.putString("SelectedCustomerUserID",actorID)
        bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
        bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
        bundle.putSerializable("plannerDetails", objCatalogues)
        itemView.findNavController().navigate(R.id.action_wishlistFragment_to_wishlistDetailsFragment,bundle)

    }

    override fun onPlannerRemoveClickResponse(itemView: View, objCatalogues: ObjCatalogues) {
        plannerId = objCatalogues.redemptionPlannerId!!
        removePlanner(plannerId)
    }

    private fun removePlanner(plannerId:Int) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getPlannerRemoveData(
            RemovePlannerRequest(
                actionType = 17,
                actorId = actorID,
                redemptionPlannerId =plannerId,
                partyLoyaltyID = partyLoyaltyID
            )
        )
    }

    override fun onPlannerRedeemClickResponse(itemView: View, objCatalogues: ObjCatalogues) {
        plannerId = objCatalogues.redemptionPlannerId!!
        LoadingDialogue.showDialog(requireContext())

        val catalogueSaveCartDetialsList: MutableList<CatalogueSaveCartDetailRequest> = ArrayList()

        catalogueSaveCartDetialsList.clear()

        val catalogueSaveCartDetailsRequest = CatalogueSaveCartDetailRequest()

        catalogueSaveCartDetailsRequest.catalogueId = objCatalogues.catalogueId.toString()
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.addPlanner_btn -> {
                if(BlockMultipleClick.click()) return
                val bundle = Bundle()
                bundle.putString("SelectedCustomerUserID",actorID)
                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                findNavController().navigate(R.id.productFragment,bundle)
            }
        }
    }
}