package com.loyaltyworks.keshavcement.ui.worksiteDetails

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentWorksiteDetailsBinding
import com.loyaltyworks.keshavcement.model.CustomerBasicInfoJsonPurchaseClaim
import com.loyaltyworks.keshavcement.model.LstWorkSiteInfo
import com.loyaltyworks.keshavcement.model.MyPurchaseClaimRequest
import com.loyaltyworks.keshavcement.model.WorksiteListRequest
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.MyPurchaseClaimViewModel
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.adapter.MyPurchaseClaimAdapter
import com.loyaltyworks.keshavcement.ui.worksiteDetails.adapter.WorksiteDetailsAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.permissionx.guolindev.PermissionX
import java.util.ArrayList

class WorksiteDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentWorksiteDetailsBinding
    private lateinit var viewModel: WorksiteViewModel

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempWorksiteList = ArrayList<LstWorkSiteInfo>()
    var currentList = ArrayList<LstWorkSiteInfo>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mWorksiteLayoutManager: LinearLayoutManager? = null
    var worksiteAdapter: RecyclerView.Adapter<*>? = null

    var selectedStatusId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(WorksiteViewModel::class.java)
        binding = FragmentWorksiteDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "WorksiteDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "WorksiteDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)
        HandleOnBackPressed()

        askPermission()

        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
        binding.createNew.setOnClickListener(this)
        binding.filterApproved.setOnClickListener(this)
        binding.filterPending.setOnClickListener(this)
        binding.filterRejected.setOnClickListener(this)
        binding.clearBtn.setOnClickListener(this)
        binding.filterOkBtn.setOnClickListener(this)

        // use a linear layout manager
        mWorksiteLayoutManager = LinearLayoutManager(requireContext())
        mWorksiteLayoutManager!!.isAutoMeasureEnabled = true
        mWorksiteLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.worksiteRecycler.layoutManager = mWorksiteLayoutManager
        binding.worksiteRecycler.isNestedScrollingEnabled = true
        binding.worksiteRecycler.setHasFixedSize(false)
        binding.worksiteRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.worksiteRecycler.itemAnimator = DefaultItemAnimator()

        loadWorksiteListObserver()
        setFileterSpinners()
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

                    // Start Location access
//                    locationAccess()

                } else {
                    // when permission denied
                    findNavController().popBackStack()
                }
            }

    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mWorksiteLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.worksiteRecycler.addOnScrollListener(scrollListener!!)
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
        viewModel.getWorksiteListData(
            WorksiteListRequest(
                actionType = 2,
                customerID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                verificationStatus = selectedStatusId,
                pageSize = limit,
                startIndex = startIndex
            )
        )
    }

    private fun loadWorksiteListObserver() {
        /*** Worksite List Observer ***/
        viewModel.worksiteListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstWorkSiteInfo.isNullOrEmpty()){
                    binding.worksiteRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempWorksiteList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        worksiteAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (purchaseClaim in it.lstWorkSiteInfo){
                        tempWorksiteList.add(tempWorksiteList.size,purchaseClaim)
                    }

                    currentList.clear()
                    currentList.addAll(tempWorksiteList)

                    if (!isLoaded) {
                        isLoaded = true
                        worksiteAdapter = WorksiteDetailsAdapter(currentList)
                        binding.worksiteRecycler.adapter = worksiteAdapter
                    }else{
                        worksiteAdapter!!.notifyDataSetChanged()
                    }


                }else {
                    if (page == 1){
                        binding.worksiteRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }
        })
    }



    override fun onClick(p0: View?) {

        when (p0!!.id) {

            R.id.filterOpen -> {

                binding.filterLayout.visibility = View.VISIBLE
                binding.filterLayout.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_dialog)
            }

            R.id.filterClose -> {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_dialog)
            }

            R.id.filter_approved ->{
                binding.filterApproved.setBackgroundResource(R.drawable.selected_filter)
                binding.filterPending.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterPending.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "2"
            }

            R.id.filter_pending ->{
                binding.filterPending.setBackgroundResource(R.drawable.selected_filter)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterPending.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "1"
            }

            R.id.filter_rejected ->{
                binding.filterRejected.setBackgroundResource(R.drawable.selected_filter)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterPending.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterPending.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "3"
            }

            R.id.clear_btn ->{
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterPending.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterPending.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = ""

                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.filter_ok_btn ->{
                currentList.clear()
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.createNew -> {

                findNavController().navigate(R.id.newWorksiteFragment)

            }
        }


    }


    /**This is required here for handling action bar navigate up button*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            if (binding.filterLayout.visibility == View.VISIBLE) {
                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            } else {
                requireActivity().onBackPressed()
            }
            return true
        }else{
            return super.onOptionsItemSelected(item)
        }
    }

    private fun HandleOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.filterLayout.visibility == View.VISIBLE) {
                    binding.filterLayout.visibility = View.GONE
                    binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
                } else {
                    isEnabled = false
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}