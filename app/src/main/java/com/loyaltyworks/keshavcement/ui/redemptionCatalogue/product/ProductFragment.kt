package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CategoryAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.PointRangeAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.ProductAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class ProductFragment : Fragment(), View.OnClickListener, PointRangeAdapter.OnItemClickCallBack, ProductAdapter.OnItemClickCallBack,
    CategoryAdapter.OnItemClickCallBack {

    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ProductCatalogueViewModel

    var mSelectedCategory = -1
    var mSelectedCategoryName = ""

    var pointRange = ""
    var LowToHigh = ""

    var isCategoryButtonClicked: Boolean = false
    var isPointRangeButtonClicked: Boolean = false
    var isHighToLowButtonClicked: Boolean = false

    var poitrangeList: ArrayList<PointRange> = ArrayList<PointRange>()
    var mSelectedPointRange = -1

    var categoryList = mutableListOf<ObjCatalogueCategoryJson>()

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempProductList = java.util.ArrayList<ObjCataloguee>()
    var currentList = java.util.ArrayList<ObjCataloguee>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mProductLayoutManager: LinearLayoutManager? = null
    var productAdapter: RecyclerView.Adapter<*>? = null

    var actorID = ""
    var loyaltyId = ""
    var partyLoyaltyID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ProductView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ProductFragment")
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

        if (isHighToLowButtonClicked){
            binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.selected_filter)
            binding.hightoLowLowtoHight.setTextColor(requireContext().resources.getColor(R.color.dark))

            if (!LowToHigh.isNullOrEmpty()){
                if (LowToHigh == "0"){
                    binding.hightoLowLowtoHight.text = "High to Low"
                }else if (LowToHigh == "1"){
                    binding.hightoLowLowtoHight.text = "Low to High"
                }
            }

        }

        binding.redeemablePoints.text = PreferenceHelper.getStringValue(requireContext(),BuildConfig.RedeemablePointsBalance)

        initialButtonSetup()


        binding.searchButton.setOnClickListener(this)
        binding.categoryButton.setOnClickListener(this)
        binding.pointsRangeButton.setOnClickListener(this)
        binding.hightoLowLowtoHight.setOnClickListener(this)

        binding.backButton.setOnClickListener(this)
        binding.cartButton.setOnClickListener(this)

        CartCountApi()
        CategoryApiCall()
        PointRangeSetUp()

        binding.searchFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {

                if (binding.searchFilter.hasFocus()){
                    ProductListing(1,s.toString(), mSelectedCategory,pointRange,LowToHigh)
                    Log.d("rbfhrbfhr","call 1")
                }



            }
        })

        // use a linear layout manager
        mProductLayoutManager = GridLayoutManager(requireContext(),2)
        mProductLayoutManager!!.isAutoMeasureEnabled = true
        mProductLayoutManager!!.orientation = GridLayoutManager.VERTICAL
        binding.productRecycler.layoutManager = mProductLayoutManager
        binding.productRecycler.isNestedScrollingEnabled = true
        binding.productRecycler.setHasFixedSize(false)
        binding.productRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.productRecycler.itemAnimator = DefaultItemAnimator()

        loadProductListObserver()
        setFileterSpinners()
    }

    private fun PointRangeSetUp() {
        poitrangeList.clear()

        val defaultstatus = PointRange()
        defaultstatus.id = -1
        defaultstatus.name = "All Points"

        val defaultstatus1 = PointRange()
        defaultstatus1.id = 1
        defaultstatus1.name = "Under 1000 pts"

        val defaultstatus2 = PointRange()
        defaultstatus2.id = 2
        defaultstatus2.name = "1000 - 4999 pts"

        val defaultstatus3 = PointRange()
        defaultstatus3.id = 3
        defaultstatus3.name = "5000 - 24999 pts"

        val defaultstatus4 = PointRange()
        defaultstatus4.id = 4
        defaultstatus4.name = "25000 pts & Above"

        poitrangeList.add(defaultstatus)
        poitrangeList.add(defaultstatus1)
        poitrangeList.add(defaultstatus2)
        poitrangeList.add(defaultstatus3)
        poitrangeList.add(defaultstatus4)

        binding.pointRangeRecycler.adapter = PointRangeAdapter(poitrangeList,mSelectedPointRange,this)
    }

    private fun initialButtonSetup() {

        if (isCategoryButtonClicked){
            binding.searchLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.categoryLayout.visibility = View.VISIBLE
            binding.searchFilter.text.clear()
            binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

        }else if (isPointRangeButtonClicked){
            binding.searchLayout.visibility = View.GONE
            binding.categoryLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.VISIBLE
            binding.searchFilter.text.clear()
            binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)

            if (mSelectedCategory != -1){
                binding.selectedCategoryLayout.visibility = View.VISIBLE
                binding.selectedCategory.text = mSelectedCategoryName
            }else{
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
            }
        }else{
            binding.searchLayout.visibility = View.VISIBLE
            binding.categoryLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.searchFilter.text.clear()
            binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

        }
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mProductLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                LoadingDialogue.showDialog(requireContext())
                ProductListing(page + 1,binding.searchFilter.text.toString(), mSelectedCategory,pointRange,LowToHigh)
                Log.d("rbfhrbfhr","call 2")
            }
        }
        binding.productRecycler.addOnScrollListener(scrollListener!!)
    }

    private fun loadProductListObserver() {
        /***   Get Prodcut list Observer ***/
        viewModel.catalogueLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                LoadingDialogue.dismissDialog()
                if (it != null && !it.objCatalogueList.isNullOrEmpty()) {
                    binding.productRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempProductList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        productAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (prod in it.objCatalogueList){
                        tempProductList.add(tempProductList.size,prod)
                    }
                    currentList.clear()
                    currentList.addAll(tempProductList)

                    if (!isLoaded) {
                        isLoaded = true

                        productAdapter = ProductAdapter(currentList, this)
                        binding.productRecycler.adapter = productAdapter
                    }else{
                        productAdapter!!.notifyDataSetChanged()
                    }

                } else {
                    if (page == 1){
                        binding.productRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }
//                LoadingDialogue.dismissDialog()
                }
            }

        })

    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        LoadingDialogue.showDialog(requireContext())
        ProductListing(1,binding.searchFilter.text.toString(), mSelectedCategory,pointRange,LowToHigh)
        Log.d("rbfhrbfhr","call 3")
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Get Product Catagory Observer ***/
        viewModel.catalogueCategoryLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (!it.objCatalogueCategoryListJson.isNullOrEmpty()) {
                    categoryList.clear()

                    categoryList.add(0,ObjCatalogueCategoryJson(
                        catogoryName = "All",
                        catogoryId = -1
                    ) )
                    categoryList.addAll(it.objCatalogueCategoryListJson!!)


                    binding.categoryRecycler.visibility = View.VISIBLE
                    binding.categoryRecycler.adapter = CategoryAdapter(categoryList,mSelectedCategory,this)

                }
            }


        })


        /***  Add to Cart Response  ***/
        viewModel.addToCartListingLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && it.returnValue == 1) {
                    Toast.makeText(requireContext(),getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
                    CartCountApi()
//                    CartCountApi()
                }else {
                    Toast.makeText(requireContext(), getString(R.string.failed_to_add_cart), Toast.LENGTH_SHORT).show()
                }
            }

        })

        /***  Add Planner Observer  ***/
        viewModel.addPlannerLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null){
                    if (it.returnValue == 1){
                        Toast.makeText(requireContext(),getString(R.string.already_added_to_planner), Toast.LENGTH_SHORT).show()
                    }else if (it.returnValue!! > 0){
                        Toast.makeText(requireContext(),getString(R.string.added_to_planner), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        /*** Cart Count Observer ***/
        viewModel.cartCountLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null){

                binding.cartCount.text = it.totalCartCatalogue.toString()
            }
        })
    }

    private fun CartCountApi() {
        viewModel.getCartCountData(
            CartCountRequest(
                actionType = "1",
                loyaltyID = loyaltyId,
                partyLoyaltyID = partyLoyaltyID

            )
        )
    }

    private fun CategoryApiCall() {

        /* Get Product Category list*/
        viewModel.getCatalogueCategoryData(
            CatalogueCategoryRequest(
                actionType = "1",
                actorId = actorID,
                isActive = 1
            )
        )
    }

    private fun ProductListing(startIndex: Int,src: String, categoryID: Int,pointRange: String, sort: String) {

        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()

        /* Get Product details list  */
        viewModel.getCatalogueData(
            GatCatalogueRequest(
                actionType = "6",
                actorId = actorID,
                ObjCatalogueDetailss(
                    merchantId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.merchantId!!,
                    catogoryId = categoryID,
                    catalogueType = 1,
                    multipleRedIds = pointRange
                ),
                searchText = src,
                startIndex = startIndex,
                noOfRows = limit,
                sort = sort,
                domain = "KESHAV_CEMENT"
            )
        )


    }

    override fun onProductListItemClickResponse(
        itemView: View,
        position: Int,
        objCatalogue: ObjCataloguee
    ) {
        currentList.clear()
//        mSelectedCategory =-1
        val bundle = Bundle()
        bundle.putSerializable("CatalogueProduct", objCatalogue)
        bundle.putString("SelectedCustomerUserID",actorID)
        bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
        bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
        itemView.findNavController().navigate(R.id.productDetailsFragment, bundle)
    }

    override fun onAddToCart(objCatalogue: ObjCataloguee) {

        LoadingDialogue.showDialog(requireContext())

        val catalogueSaveCartDetialsList: MutableList<CatalogueSaveCartDetailRequest> = ArrayList()

        catalogueSaveCartDetialsList.clear()

        val catalogueSaveCartDetailsRequest = CatalogueSaveCartDetailRequest()

        catalogueSaveCartDetailsRequest.catalogueId = objCatalogue.catalogueId.toString()
        catalogueSaveCartDetailsRequest.comments = objCatalogue.comments
        catalogueSaveCartDetailsRequest.noOfQuantity = "1"
        catalogueSaveCartDetailsRequest.deliveryType = "1"

        catalogueSaveCartDetialsList.add(catalogueSaveCartDetailsRequest)

        viewModel.getAddToCartData(
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

    override fun onAddPlannerListener(catalogueId: Int) {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getPlannerAddData(
            PlannerAddRequest(
                actionType = 0,
                actorId = actorID,
                partyLoyaltyID =partyLoyaltyID,
                ObjCatalogueDetailsy(
                    catalogueId =catalogueId
                )

            )
        )
    }

    override fun onHoldAddtoCart() {
        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.not_allowed_to_redeem_contact_administrator), object : AppController.SuccessCallBack {
            override fun onOk() {
            }

        })
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            R.id.cart_button ->{
                val bundle = Bundle()
                bundle.putString("SelectedCustomerUserID",actorID)
                bundle.putString("SelectedCustomerLoyltyID",loyaltyId)
                bundle.putString("SelectedCustomerPartyLoyaltyID",partyLoyaltyID)
                findNavController().navigate(R.id.action_productFragment_to_cartFragment,bundle)
            }

            R.id.back_button ->{
                findNavController().popBackStack()
            }

            R.id.search_button -> {
                LowToHigh = ""
                pointRange = ""
                mSelectedCategory = -1
                mSelectedPointRange = -1
                isCategoryButtonClicked = false
                isPointRangeButtonClicked = false
                isHighToLowButtonClicked = false

                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.unselected_category)

                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.searchLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.GONE
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
                binding.searchFilter.text.clear()

                binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

                listFull = false
                isLoaded = false
                CategoryApiCall()
                LoadingDialogue.showDialog(requireContext())
                ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
                Log.d("rbfhrbfhr","call 4")

            }

            R.id.category_button -> {
                LowToHigh = ""
                pointRange = ""

                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.unselected_category)
                isCategoryButtonClicked = true
                isPointRangeButtonClicked = false
                isHighToLowButtonClicked = false

                binding.searchLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.GONE
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
                binding.searchFilter.text.clear()
                binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

                LoadingDialogue.showDialog(requireContext())
                ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
                Log.d("rbfhrbfhr","call 5")
            }

            R.id.points_range_button -> {
                isPointRangeButtonClicked = true
                isCategoryButtonClicked = false

                binding.searchLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.VISIBLE
                binding.searchFilter.text.clear()
                binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)

                if (mSelectedCategory != -1){
                    binding.selectedCategoryLayout.visibility = View.VISIBLE
                    binding.selectedCategory.text = mSelectedCategoryName
                }else{
                    binding.selectedCategoryLayout.visibility = View.INVISIBLE
                }

                mSelectedPointRange = -1
                PointRangeSetUp()

                LoadingDialogue.showDialog(requireContext())
                ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
                Log.d("rbfhrbfhr","call 6")
            }

            R.id.hightoLowLowtoHight -> {
                isHighToLowButtonClicked = true
                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.selected_filter)
                binding.hightoLowLowtoHight.setTextColor(requireContext().resources.getColor(R.color.dark))
                if (binding.hightoLowLowtoHight.text.toString() == "Low to High") {
                    binding.hightoLowLowtoHight.text = "High to Low"
                    LowToHigh = "0"
                } else {
                    binding.hightoLowLowtoHight.text = "Low to High"
                    LowToHigh = "1"
                }

                ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
                Log.d("rbfhrbfhr","call 7")
            }

        }

    }



    override fun onCatagoryClickResponse(position: Int, objCategory: ObjCatalogueCategoryJson) {
        mSelectedCategory = objCategory.catogoryId!!
        mSelectedCategoryName = objCategory.catogoryName!!
        listFull = false
        isLoaded = false
        currentList.clear()
        LoadingDialogue.showDialog(requireContext())
        ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
        Log.d("rbfhrbfhr","call 8")
    }


    override fun onPointRangeClickResponse(position: Int, pointRangess: PointRange) {
        mSelectedPointRange = pointRangess.id!!

        if (mSelectedPointRange == -1){
            pointRange = ""
        }else if (mSelectedPointRange == 1){
            pointRange = "0-999"
        }else if (mSelectedPointRange == 2){
            pointRange = "1000-4999"
        }else if (mSelectedPointRange == 3){
            pointRange = "5000-24999"
        }else if (mSelectedPointRange == 4){
            pointRange = "25000-9999999"
        }

        listFull = false
        isLoaded = false

        LoadingDialogue.showDialog(requireContext())
        ProductListing(1,"", mSelectedCategory,pointRange,LowToHigh)
        Log.d("rbfhrbfhr","call 9")
    }




}