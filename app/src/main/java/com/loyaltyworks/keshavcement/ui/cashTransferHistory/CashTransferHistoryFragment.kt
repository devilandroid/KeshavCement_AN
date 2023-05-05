package com.loyaltyworks.keshavcement.ui.cashTransferHistory

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
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferHistoryBinding
import com.loyaltyworks.keshavcement.model.CashTransferHistoryRequest
import com.loyaltyworks.keshavcement.model.LstCustomerCashTransferedDetailTransferHistory
import com.loyaltyworks.keshavcement.ui.cashTransferHistory.adapter.CashTransferHistoryAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import kotlinx.android.synthetic.main.appbar_main.*
import java.util.ArrayList


class CashTransferHistoryFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentCashTransferHistoryBinding
    private lateinit var viewModel: CashTransferHistoryViewModel


    var selectedStatusId = ""
    var selectedCustTypeId = ""
    var FromDate = ""
    var ToDate = ""

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempCashTransferList = ArrayList<LstCustomerCashTransferedDetailTransferHistory>()
    var currentList = ArrayList<LstCustomerCashTransferedDetailTransferHistory>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mCashTransferLayoutManager: LinearLayoutManager? = null
    var cashTransferAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(CashTransferHistoryViewModel::class.java)
        binding = FragmentCashTransferHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        HandleOnBackPressed()

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CashTransferHistoryView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CashTransferHistoryFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.filterSubDealer.visibility = View.GONE
        }


        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
        binding.filterApproved.setOnClickListener(this)
        binding.filterRejected.setOnClickListener(this)
        binding.filterEngineer.setOnClickListener(this)
        binding.filterMason.setOnClickListener(this)
        binding.filterSubDealer.setOnClickListener(this)
        binding.clearBtn.setOnClickListener(this)
        binding.filterOkBtn.setOnClickListener(this)
        binding.fromDate.setOnClickListener(this)
        binding.toDate.setOnClickListener(this)



        // use a linear layout manager
        mCashTransferLayoutManager = LinearLayoutManager(requireContext())
        mCashTransferLayoutManager!!.isAutoMeasureEnabled = true
        mCashTransferLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.cashTransferHistoryRecycler.layoutManager = mCashTransferLayoutManager
        binding.cashTransferHistoryRecycler.isNestedScrollingEnabled = true
        binding.cashTransferHistoryRecycler.setHasFixedSize(false)
        binding.cashTransferHistoryRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.cashTransferHistoryRecycler.itemAnimator = DefaultItemAnimator()

        loadCashTransferHistoryObserver()
        setFileterSpinners()
    }



    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mCashTransferLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.cashTransferHistoryRecycler.addOnScrollListener(scrollListener!!)
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
        viewModel.getCashTransferHistoryListData(
            CashTransferHistoryRequest(
                actionType = 3,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                startIndex = startIndex,
                pageSize = limit,
                isTranHistory = "1",
                status = selectedStatusId,
                fromDate = AppController.dateAPIFormats(FromDate),
                todate = AppController.dateAPIFormats(ToDate),
                customerTypeId = selectedCustTypeId
            )
        )
    }


    private fun loadCashTransferHistoryObserver() {

        /*** Cash Transfer History Observer ***/
        viewModel.cashbackTransferHistoryListLiveData.observe(viewLifecycleOwner, Observer{
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstCustomerCashTransferedDetails.isNullOrEmpty()) {
                    binding.cashTransferHistoryRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempCashTransferList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        cashTransferAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (redemption in it.lstCustomerCashTransferedDetails){
                        tempCashTransferList.add(tempCashTransferList.size,redemption)
                    }

                    currentList.clear()
                    currentList.addAll(tempCashTransferList)

                    if (!isLoaded) {
                        isLoaded = true
                        cashTransferAdapter = CashTransferHistoryAdapter(currentList)
                        binding.cashTransferHistoryRecycler.adapter = cashTransferAdapter
                    }else{
                        cashTransferAdapter!!.notifyDataSetChanged()
                    }



                } else {
                    if (page == 1){
                        binding.cashTransferHistoryRecycler.visibility = View.GONE
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
            R.id.filterOpen ->{
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

            R.id.filter_engineer ->{
                binding.filterEngineer.setBackgroundResource(R.drawable.selected_filter)
                binding.filterMason.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterSubDealer.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterEngineer.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterMason.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterSubDealer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedCustTypeId = BuildConfig.Engineer
            }

            R.id.filter_Mason ->{
                binding.filterMason.setBackgroundResource(R.drawable.selected_filter)
                binding.filterEngineer.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterSubDealer.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterMason.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterEngineer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterSubDealer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedCustTypeId =  BuildConfig.Mason
            }

            R.id.filter_subDealer ->{
                binding.filterSubDealer.setBackgroundResource(R.drawable.selected_filter)
                binding.filterEngineer.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterMason.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterSubDealer.setTextColor(requireContext().resources.getColor(R.color.dark))
                binding.filterEngineer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterMason.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedCustTypeId =  BuildConfig.SubDealer
            }

            R.id.clear_btn ->{
                binding.filterRejected.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterApproved.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterSubDealer.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterEngineer.setBackgroundResource(R.drawable.unselected_filter2)
                binding.filterMason.setBackgroundResource(R.drawable.unselected_filter2)

                binding.filterRejected.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterApproved.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                binding.filterSubDealer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterEngineer.setTextColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.filterMason.setTextColor(requireContext().resources.getColor(R.color.colorAccent))

                selectedStatusId = ""
                selectedCustTypeId = ""
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