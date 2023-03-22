package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.customerSelection

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCustomerSelectionBinding
import com.loyaltyworks.keshavcement.model.CustomerListingRequest
import com.loyaltyworks.keshavcement.model.LstCustParentChildMappingCustList
import com.loyaltyworks.keshavcement.ui.enrollment.EnrollmentViewModel
import com.loyaltyworks.keshavcement.ui.enrollment.adapter.EnrollmentAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CustomerSelectionAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class CustomerSelectionFragment : Fragment(), CustomerSelectionAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCustomerSelectionBinding
    private lateinit var viewModel: EnrollmentViewModel

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempCustomerList = ArrayList<LstCustParentChildMappingCustList>()
    var currentList = ArrayList<LstCustParentChildMappingCustList>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mCustomerLayoutManager: LinearLayoutManager? = null
    var customerAdapter: RecyclerView.Adapter<*>? = null

    var directedFrom: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(EnrollmentViewModel::class.java)
        binding = FragmentCustomerSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CatalogueCustomerSelectionView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CatalogueCustomerSelectionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        directedFrom = arguments?.getSerializable("directedFrom") as String

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
                    callApi(1)
                    Log.d("rbfhrbfhr","call 1")
                }

            }
        })



        // use a linear layout manager
        mCustomerLayoutManager = LinearLayoutManager(requireContext())
        mCustomerLayoutManager!!.isAutoMeasureEnabled = true
        mCustomerLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.customerRecycler.layoutManager = mCustomerLayoutManager
        binding.customerRecycler.isNestedScrollingEnabled = true
        binding.customerRecycler.setHasFixedSize(false)
        binding.customerRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.customerRecycler.itemAnimator = DefaultItemAnimator()

        loadCustomerListObserver()
        setFileterSpinners()

    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mCustomerLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.customerRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        LoadingDialogue.showDialog(requireContext())
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

//        LoadingDialogue.showDialog(requireContext())
        viewModel.getCustomerListData(
            CustomerListingRequest(
                actionType = 15,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                searchText = binding.searchFilter.text.toString(),
                pageSize = limit,
                startIndex = startIndex
            )
        )
    }

    private fun loadCustomerListObserver() {
        /*** Customer List Observer ***/
        viewModel.customerListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstCustParentChildMapping.isNullOrEmpty()){
                    binding.customerRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempCustomerList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        customerAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (custList in it.lstCustParentChildMapping){
                        tempCustomerList.add(tempCustomerList.size,custList)
                    }

                    currentList.clear()
                    currentList.addAll(tempCustomerList)

                    if (!isLoaded) {
                        isLoaded = true
                        customerAdapter = CustomerSelectionAdapter(currentList,this)
                        binding.customerRecycler.adapter = customerAdapter
                    }else{
                        customerAdapter!!.notifyDataSetChanged()
                    }


                }else {
                    if (page == 1){
                        binding.customerRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }
        })
    }


    override fun onCustListItemClickResponse(itemView: View, position: Int,selectedCustomer:LstCustParentChildMappingCustList) {
        binding.searchFilter.text.clear()

        /*** Set PointBalance & Mobile & Name to Preference ***/
        PreferenceHelper.setStringValue(requireContext(), BuildConfig.RedeemablePointsBalance, selectedCustomer.totalPointsBalance.toString())
        PreferenceHelper.setStringValue(requireContext(), BuildConfig.SelectedCustomerMobile, selectedCustomer.mobile.toString())
        PreferenceHelper.setStringValue(requireContext(), BuildConfig.SelectedCustomerName, selectedCustomer.firstName.toString())

        val bundle = Bundle()
        bundle.putString("SelectedCustomerUserID",selectedCustomer.userID.toString())
        bundle.putString("SelectedCustomerLoyltyID",selectedCustomer.loyaltyID.toString())

        if (directedFrom == "ProductClick"){
            findNavController().navigate(R.id.productFragment,bundle)
        }else if (directedFrom == "VoucherClick"){
            findNavController().navigate(R.id.evouchersFragment,bundle)
        }else if (directedFrom == "WishlistClick"){
            findNavController().navigate(R.id.wishlistFragment,bundle)
        }


    }

    override fun onHoldCustomer() {
        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.the_account_is_not_allowed_to_redeem_contact_administrator), object : AppController.SuccessCallBack {
            override fun onOk() {
            }

        })
    }

}