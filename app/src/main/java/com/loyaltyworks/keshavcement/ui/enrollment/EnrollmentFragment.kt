package com.loyaltyworks.keshavcement.ui.enrollment

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
import com.loyaltyworks.keshavcement.databinding.FragmentEnrollmentBinding
import com.loyaltyworks.keshavcement.model.CustomerListingRequest
import com.loyaltyworks.keshavcement.model.LstCustParentChildMappingCustList
import com.loyaltyworks.keshavcement.ui.enrollment.adapter.EnrollmentAdapter
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.adapter.MyPurchaseClaimAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class EnrollmentFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentEnrollmentBinding
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

    var actorID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(EnrollmentViewModel::class.java)
        binding = FragmentEnrollmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "EnrollmentView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "EnrollmentFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            actorID = PreferenceHelper.getStringValue(requireContext(), BuildConfig.MappedCustomerIdSE)
        }else{
            actorID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
        }

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


        binding.addCustomer.setOnClickListener(this)

        // use a linear layout manager
        mCustomerLayoutManager = LinearLayoutManager(requireContext())
        mCustomerLayoutManager!!.isAutoMeasureEnabled = true
        mCustomerLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.enrollmentRecycler.layoutManager = mCustomerLayoutManager
        binding.enrollmentRecycler.isNestedScrollingEnabled = true
        binding.enrollmentRecycler.setHasFixedSize(false)
        binding.enrollmentRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.enrollmentRecycler.itemAnimator = DefaultItemAnimator()

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
        binding.enrollmentRecycler.addOnScrollListener(scrollListener!!)
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
                actorId = actorID,
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
                    binding.enrollmentRecycler.visibility = View.VISIBLE
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
                        customerAdapter = EnrollmentAdapter(currentList)
                        binding.enrollmentRecycler.adapter = customerAdapter
                    }else{
                        customerAdapter!!.notifyDataSetChanged()
                    }


                }else {
                    if (page == 1){
                        binding.enrollmentRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_customer ->{
                if (BlockMultipleClick.click())return
                binding.searchFilter.text.clear()
                findNavController().navigate(R.id.action_enrollmentFragment_to_newEnrollmentFragment)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

        }
    }


}