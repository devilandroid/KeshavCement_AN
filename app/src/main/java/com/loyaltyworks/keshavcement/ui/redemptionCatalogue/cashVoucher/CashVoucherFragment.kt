package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashVoucher

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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashVoucherBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CashVoucherAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product.ProductCatalogueViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.EndlessRecyclerViewScrollListener
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.InstructionDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import java.io.Serializable
import java.util.*


class CashVoucherFragment : Fragment(), View.OnClickListener, CashVoucherAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCashVoucherBinding
    private lateinit var viewModel: ProductCatalogueViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel


    var page = 1
    var limit = 10

    var isRefresh = true
    var isLoaded = false
    var listFull = false
    var tempProductList = java.util.ArrayList<ObjCataloguee>()
    var currentList = java.util.ArrayList<ObjCataloguee>()
    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mCashTransferLayoutManager: LinearLayoutManager? = null
    var productAdapter: RecyclerView.Adapter<*>? = null

    val catalogueList : MutableList<ObjCatalogueList> = ArrayList()
    lateinit var  _lstCustomerJson: List<LstCustomerJson>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(ProductCatalogueViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        binding = FragmentCashVoucherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CashVoucherView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CashVoucherFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)



        binding.redeemPoints.text = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance.toString()

        binding.helpInstruction.setOnClickListener(this)

        // use a linear layout manager
        mCashTransferLayoutManager = LinearLayoutManager(requireContext())
        mCashTransferLayoutManager!!.isAutoMeasureEnabled = true
        mCashTransferLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.cashTransferRecycler.layoutManager = mCashTransferLayoutManager
        binding.cashTransferRecycler.isNestedScrollingEnabled = true
        binding.cashTransferRecycler.setHasFixedSize(false)
        binding.cashTransferRecycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.cashTransferRecycler.itemAnimator = DefaultItemAnimator()

        loadCashTransferObserver()
        setFileterSpinners()

        callAddressApi()
    }

    private fun setFileterSpinners() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mCashTransferLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                LoadingDialogue.showDialog(requireContext())
                ProductListing(page + 1)
                Log.d("rbfhrbfhr","call 2")
            }
        }
        binding.cashTransferRecycler.addOnScrollListener(scrollListener!!)
    }

    override fun onResume() {
        super.onResume()
        listFull = false
        isLoaded = false
        LoadingDialogue.showDialog(requireContext())
        ProductListing(1)
        Log.d("rbfhrbfhr","call 3")
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
    }

    private fun ProductListing(startIndex: Int) {

        if (listFull) return
        this.page = startIndex
        if (page == 1)
            scrollListener!!.resetState()

        LoadingDialogue.showDialog(requireContext())
        /* Get Product details list  */
        viewModel.getCatalogueData(
            GatCatalogueRequest(
                actionType = "6",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ObjCatalogueDetailss(
                    merchantId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.merchantId!!,
                    catogoryId = 9,
                    catalogueType = 9,
//                    multipleRedIds = pointRange
                ),
//                searchText = src,
                startIndex = startIndex,
                noOfRows = limit,
//                sort = sort,
                domain = "KESHAV_CEMENT"
            )
        )


    }

    private fun callAddressApi() {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.setProfileRequest(
            ProfileRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )
    }

    private fun loadCashTransferObserver() {
        /***   Get Prodcut list Observer ***/
        viewModel.catalogueLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                LoadingDialogue.dismissDialog()
                if (it != null && !it.objCatalogueList.isNullOrEmpty()) {
                    binding.cashTransferRecycler.visibility = View.VISIBLE
                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                    if (it.objCatalogueList[0].isRedeemable == 1){
                        binding.noteText.visibility = View.GONE
                    }else{
                        binding.noteText.visibility = View.VISIBLE
                        binding.noteText.setSelected(true)
                    }

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

                        productAdapter = CashVoucherAdapter(currentList, this)
                        binding.cashTransferRecycler.adapter = productAdapter
                    }else{
                        productAdapter!!.notifyDataSetChanged()
                    }

                } else {
                    if (page == 1){
                        binding.cashTransferRecycler.visibility = View.GONE
                        binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
                    }else{
                        listFull = false
                    }
//                LoadingDialogue.dismissDialog()
                }
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Customer Address Observer ***/
        loginViewModel.getProfileResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()
            if (it != null) {
                if (!it.lstCustomerJson.isNullOrEmpty()) {
                    _lstCustomerJson = it.lstCustomerJson

                } else {
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.help_instruction ->{
                InstructionDialog.showInstructionDialog(requireContext(),object : InstructionDialog.InstructionDialogCallBack{
                    override fun onOk() {

                    }

                })

            }
        }
    }

    override fun onListItemClickResponse(itemView: View, position: Int, objCatalogue: ObjCataloguee) {
        currentList.clear()
        val bundle = Bundle()
        bundle.putSerializable("CashVoucherData", objCatalogue)
        bundle.putSerializable("CustomerAddress", _lstCustomerJson as Serializable)
        findNavController().navigate(R.id.action_cashVoucherFragment_to_cashVoucherDetailsFragment, bundle)
    }

    override fun onRedeemBtnClickResponse(itemView: View, position: Int, objCatalogue: ObjCataloguee) {
        if (!_lstCustomerJson.isEmpty()){
            val bundle = Bundle()
            bundle.putSerializable("CashVoucherData", objCatalogue)
            bundle.putSerializable("CustomerAddress", _lstCustomerJson as Serializable)
            findNavController().navigate(R.id.cashTransferDialogFragment, bundle)
        }else{
            Toast.makeText(requireContext(), getString(R.string.address_not_found), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onHoldRedeemVoucher() {
        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.not_allowed_to_redeem_contact_administrator), object : AppController.SuccessCallBack {
            override fun onOk() {
            }

        })
    }


}