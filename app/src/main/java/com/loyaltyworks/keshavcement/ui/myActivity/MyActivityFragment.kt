package com.loyaltyworks.keshavcement.ui.myActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
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
import com.loyaltyworks.keshavcement.databinding.FragmentMyActivityBinding
import com.loyaltyworks.keshavcement.model.CustomerBasicInfoJsonPurchaseClaim
import com.loyaltyworks.keshavcement.model.MyPurchaseClaimRequest
import com.loyaltyworks.keshavcement.ui.myActivity.adapter.MyActivityAdapter
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.MyPurchaseClaimViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class MyActivityFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMyActivityBinding
    private lateinit var viewModel: MyPurchaseClaimViewModel

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempMyActivityList = ArrayList<CustomerBasicInfoJsonPurchaseClaim>()
    var currentList = ArrayList<CustomerBasicInfoJsonPurchaseClaim>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mMyActivityLayoutManager: LinearLayoutManager? = null
    var myActivityAdapter: RecyclerView.Adapter<*>? = null

    var selectedStatusId = "-3"
    var FromDate = ""
    var ToDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MyPurchaseClaimViewModel::class.java)
        binding = FragmentMyActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        HandleOnBackPressed()

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_MyActivityView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_MyActivityFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)


        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
        binding.filterApproved.setOnClickListener(this)
        binding.filterRejected.setOnClickListener(this)
        binding.clearBtn.setOnClickListener(this)
        binding.filterOkBtn.setOnClickListener(this)
        binding.fromDate.setOnClickListener(this)
        binding.toDate.setOnClickListener(this)

        // use a linear layout manager
        mMyActivityLayoutManager = LinearLayoutManager(requireContext())
        mMyActivityLayoutManager!!.isAutoMeasureEnabled = true
        mMyActivityLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.myActivityRecycler.layoutManager = mMyActivityLayoutManager
        binding.myActivityRecycler.isNestedScrollingEnabled = true
        binding.myActivityRecycler.setHasFixedSize(false)
        binding.myActivityRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.myActivityRecycler.itemAnimator = DefaultItemAnimator()

        loadMyActivityListObserver()
        setFileterSpinners()
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mMyActivityLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.myActivityRecycler.addOnScrollListener(scrollListener!!)
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
        viewModel.getMyPurchaseClaimListData(
            MyPurchaseClaimRequest(
                actionType = 6,
                salesPersonId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userName!!.toString(),
                pageSize = limit,
                startIndex = startIndex,
                fromDate = AppController.dateAPIFormats(FromDate),
                toDate = AppController.dateAPIFormats(ToDate),
                activeStatus = selectedStatusId

            )
        )
    }

    private fun loadMyActivityListObserver() {
        /*** My Activity List Observer ***/
        viewModel.myPurchaseClaimListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.customerBasicInfoListJson.isNullOrEmpty()){
                    binding.myActivityRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempMyActivityList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        myActivityAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (purchaseClaim in it.customerBasicInfoListJson){
                        tempMyActivityList.add(tempMyActivityList.size,purchaseClaim)
                    }

                    currentList.clear()
                    currentList.addAll(tempMyActivityList)

                    if (!isLoaded) {
                        isLoaded = true
                        myActivityAdapter = MyActivityAdapter(currentList)
                        binding.myActivityRecycler.adapter = myActivityAdapter
                    }else{
                        myActivityAdapter!!.notifyDataSetChanged()
                    }


                }else {
                    if (page == 1){
                        binding.myActivityRecycler.visibility = View.GONE
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
            R.id.filterOpen -> {

                binding.filterLayout.visibility = View.VISIBLE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_up_dialog)
            }

            R.id.filterClose -> {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.from_date ->{
                DatePickerBox.date(1, activity) {
                    binding.fromDateTxt.text = it.toString()
                    FromDate = it
                    try {
                        DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                            if (!it) {
                                FromDate = ""
                                binding.fromDateTxt.text = ""
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            R.id.to_date ->{
                DatePickerBox.date(1, activity) {
                    binding.toDateTxt.text = it.toString()
                    ToDate = it
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            ToDate = ""
                            binding.toDateTxt.text = ""
                        }
                    }
                }
            }

            R.id.filter_approved ->{
                binding.filterApproved.setBackgroundResource(R.drawable.selected_filter)
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "1"
            }

            R.id.filter_rejected ->{
                binding.filterRejected.setBackgroundResource(R.drawable.selected_filter)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "-1"
            }

            R.id.clear_btn ->{
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = "-3"
                FromDate = ""
                ToDate = ""
                binding.fromDateTxt.text = ""
                binding.toDateTxt.text = ""
                binding.fromDateTxt.hint = getString(R.string.select_from_date)
                binding.toDateTxt.hint = getString(R.string.select_to_date)
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

            R.id.filter_ok_btn ->{
                if (FromDate.isNotEmpty() && ToDate.isEmpty()) {
                    Toast.makeText(requireContext(), "To date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                } else if (FromDate.isEmpty() && ToDate.isNotEmpty()) {
                    Toast.makeText(requireContext(), "From date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                }

                currentList.clear()
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }

        }
    }

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

}