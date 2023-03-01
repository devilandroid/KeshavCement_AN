package com.loyaltyworks.keshavcement.ui.myEarning

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMyEarningBinding
import com.loyaltyworks.keshavcement.model.LstRewardTransJsonDetail
import com.loyaltyworks.keshavcement.model.MyEarningRequest
import com.loyaltyworks.keshavcement.ui.myEarning.adapter.MyEarningAdapter
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.DatePickerBox
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.util.ArrayList


class MyEarningFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMyEarningBinding
    private lateinit var viewModel: MyEarningViewModel

    private var FromDate: String = ""
    private var ToDate: String =""

    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempEarningList = ArrayList<LstRewardTransJsonDetail>()
    var currentList = ArrayList<LstRewardTransJsonDetail>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mEarningLayoutManager: LinearLayoutManager? = null
    var earningAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MyEarningViewModel::class.java)
        binding =  FragmentMyEarningBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MyEarningView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MyEarningFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)

        HandleOnBackPressed()

        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
        binding.clearBtn.setOnClickListener(this)
        binding.filterOkBtn.setOnClickListener(this)
        binding.fromDate.setOnClickListener(this)
        binding.toDate.setOnClickListener(this)

        // use a linear layout manager
        mEarningLayoutManager = LinearLayoutManager(requireContext())
        mEarningLayoutManager!!.isAutoMeasureEnabled = true
        mEarningLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.earningRecycler.layoutManager = mEarningLayoutManager
        binding.earningRecycler.isNestedScrollingEnabled = true
        binding.earningRecycler.setHasFixedSize(false)
        binding.earningRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.earningRecycler.itemAnimator = DefaultItemAnimator()

        loadEarningListObserver()
        setFileterSpinners()
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mEarningLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                LoadingDialogue.showDialog(requireContext())
                callApi(page + 1)
            }
        }
        binding.earningRecycler.addOnScrollListener(scrollListener!!)
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

    private fun loadEarningListObserver() {
        /*** My Earning List Observer ***/
        viewModel.myEarningListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstRewardTransJsonDetails.isNullOrEmpty()){
                    binding.filterLayout.visibility = View.GONE
                    binding.earningRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE
                    if (page == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempEarningList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        earningAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }

                    for (earning in it.lstRewardTransJsonDetails!!){
                        tempEarningList.add(tempEarningList.size,earning)
                    }

                    currentList.clear()
                    currentList.addAll(tempEarningList)

                    if (!isLoaded) {
                        isLoaded = true

                        earningAdapter = MyEarningAdapter(currentList)
                        binding.earningRecycler.adapter = earningAdapter
                    }else{
                        earningAdapter!!.notifyDataSetChanged()
                    }

                }else{
                    if (page == 1){
                        binding.earningRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }

                }
            }

        })
    }

    private fun callApi(startIndex: Int) {
        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()

        LoadingDialogue.showDialog(requireContext())

        val myEarningRequest = MyEarningRequest()
//        myEarningRequest.actionType = "52"
        myEarningRequest.actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!
        myEarningRequest.startIndex = startIndex
        myEarningRequest.pageSize = limit


        if (FromDate.isNotEmpty() && ToDate.isNotEmpty()){
            myEarningRequest.jFromDate = AppController.dateAPIFormats(FromDate)
            myEarningRequest.jToDate = AppController.dateAPIFormats(ToDate)
        }

        viewModel.getMyEarningListData(myEarningRequest)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

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

            R.id.clear_btn ->{
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

            R.id.filter_ok_btn -> {
                if (FromDate.isNotEmpty() && ToDate.isEmpty()) {
                    Toast.makeText(requireContext(), "To date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                } else if (FromDate.isEmpty() && ToDate.isNotEmpty()) {
                    Toast.makeText(requireContext(), "From date should not be empty", Toast.LENGTH_SHORT).show()
                    return
                }

                listFull = false
                currentList.clear()
                callApi(1)

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
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