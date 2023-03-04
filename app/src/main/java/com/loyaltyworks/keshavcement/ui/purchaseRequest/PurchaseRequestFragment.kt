package com.loyaltyworks.keshavcement.ui.purchaseRequest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPurchaseRequestBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.TimeChangeDialog
import java.time.LocalDate


class PurchaseRequestFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    private lateinit var binding: FragmentPurchaseRequestBinding
    private lateinit var viewModel: PurchaseRequestViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType:CommonSpinner
    var userTypeId: Int = -1

    var _dealerSubDealerList = mutableListOf<LstCustParentChildMappingDealer>()
    var dealerSubDealerListAdapter: ArrayAdapter<String>? = null
    var dealerSubDealerId: String = "-1"

    var _productList = mutableListOf<LstAttributesDetailProduct>()
    var productListAdapter: ArrayAdapter<String>? = null
    var productId: String = "-1"
    var productCode: String = ""

    var plusMinusBtn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        binding = FragmentPurchaseRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.custTypeSpinner.onItemSelectedListener = this
        binding.dealerSpinner.onItemSelectedListener = this
        binding.productSpinner.onItemSelectedListener = this

        binding.qtyPlus.setOnClickListener(this)
        binding.qtyMinus.setOnClickListener(this)

        userTypeSpinner()

        binding.qtyTextview.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().equals("", ignoreCase = true)) return

                if (s.toString().equals("0", ignoreCase = true) || s.toString().isEmpty()) {
                    binding.qtyTextview.setText("")
                    return
                }

            }

        })


        /*** Submit Claim by Swap ***/
        binding.claimBtn.setOnStateChangeListener {
            if (it){

                if (userTypeId == -1){
                    Toast.makeText(requireContext(), getString(R.string.please_select_user_type), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else if (dealerSubDealerId == "-1"){
                    Toast.makeText(requireContext(), getString(R.string.please_select_name), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else if (productId == "-1"){
                    Toast.makeText(requireContext(), getString(R.string.please_select_product), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else if (binding.qtyTextview.text.toString().isNullOrEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.please_enter_quantity), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else if (binding.qtyTextview.text.toString().equals("0")){
                    Toast.makeText(requireContext(), getString(R.string.quantity_should_be_greater_than_0), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else{
                    if(Settings.Global.getInt(requireActivity().contentResolver, Settings.Global.AUTO_TIME) == 1)
                    {
                        submitClaimApi()
                    }else {
                        // Disabed
                        Log.d("jhryfgry","changed")
                        TimeChangeDialog.showTimeChangeDialog(requireContext(), object: TimeChangeDialog.TimeChangeDialogCallBack{
                            override fun onOk() {
                                binding.claimBtn.changeState(false,true)
                                startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
                            }

                        })
                    }

                }

                /*Handler().postDelayed({

                },3000L)*/
            }
        }

    }

    @SuppressLint("NewApi")
    private fun submitClaimApi() {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getPurchaseSubmitData(
            SubmitPurchaseRequest(
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ritailerId = dealerSubDealerId,
                sourceDevice = 1,
                tranDate = LocalDate.now().toString(),
                productSaveDetailList = listOf(
                    ProductSaveDetail(
                    productCode = productCode,
                    quantity = binding.qtyTextview.text.toString()
                )
                )

            )
        )
    }

    private fun userTypeSpinner() {

        userTypeList.clear()
        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            userTypeList.add( CommonSpinner(name = "Select Dealer", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))
        }else{
            userTypeList.add( CommonSpinner(name = "Select Dealer / Sub Dealer", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))
            userTypeList.add( CommonSpinner(name = "Sub Dealer", id = 4))
        }


        binding.custTypeSpinner.adapter = SpinnerCommonWhiteTextAdapter(requireActivity(), R.layout.spinner_popup_row,userTypeList)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.cust_type_spinner -> {
                mSelecteduserType = parent.getItemAtPosition(position) as CommonSpinner
                userTypeId = mSelecteduserType.id!!
                Log.d("fdsfsdf", mSelecteduserType.name!!)

                if (userTypeId > 0){
                    /*** Dealer Sub-Dealer Api call ***/
                    viewModel.getDealerSubDealerListData(
                        DealerSubDealerListRequest(
                            actionType = 16,
                            actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                            searchText = userTypeId.toString()
                        )
                    )

                }else{
                    val dealerListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.dealerSpinner.adapter = dealerSubDealerListAdapter
                }

            }

            R.id.dealer_spinner ->{
                dealerSubDealerId = _dealerSubDealerList[position].userID.toString()
                Log.d("bhbrfhrb","dealer Sub-Dealer ID : " + dealerSubDealerId)

                if (dealerSubDealerId != "-1"){
                    /*** Product List Api call ***/
                    viewModel.getProductDropdownData(
                        ProductDropdownRequest(
                            actionType = "105"
                        )
                    )

                }else{
                    val productListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListNames)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter
                }
            }

            R.id.product_spinner ->{
                productId = _productList[position].attributeId.toString()
                productCode = _productList[position].attributeValue.toString()
                Log.d("bhbrfhrb","product ID : " + productId)
            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Dealer Sub-Dealer List Observer ***/
        viewModel.dealerSubDealerListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstCustParentChildMapping.isNullOrEmpty()){
                    val dealerSubDealerLists: MutableList<LstCustParentChildMappingDealer> = it.lstCustParentChildMapping!!.toMutableList()
                    _dealerSubDealerList = dealerSubDealerLists

                    val dealerSubDealerListName = ArrayList<String>()

                    for (commonSpinner in dealerSubDealerLists) {
                        dealerSubDealerListName.add(commonSpinner.firstName!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerSubDealerListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerSubDealerListName)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.dealerSpinner.adapter = dealerSubDealerListAdapter


                }else{
                    val dealerListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Name"
                    commonSpinner.id = -1
                    dealerListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstCustParentChildMappingDealer()
                    custlist1.firstName = "Select Name"
                    custlist1.userID = -1
                    _dealerSubDealerList.add(0,custlist1)

                    dealerSubDealerListAdapter = ArrayAdapter(requireContext(), R.layout.spinner_row_white_text, dealerListNames)
                    dealerSubDealerListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.dealerSpinner.adapter = dealerSubDealerListAdapter
                }
            }

        })

        /*** Product List Observer ***/
        viewModel.productDropdownLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()){
                    val productLists: MutableList<LstAttributesDetailProduct> = it.lstAttributesDetails!!.toMutableList()
                    _productList = productLists

                    val productListName = ArrayList<String>()

                    for (commonSpinner in productLists) {
                        productListName.add(commonSpinner.attributeValue!!)
                    }

                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListName.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListName)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter



                }else{
                    val productListNames = ArrayList<String>()
                    val commonSpinner = CommonSpinners()
                    commonSpinner.name = "Select Product"
                    commonSpinner.id = -1
                    productListNames.add(0,commonSpinner.name!!)

                    val custlist1 =  LstAttributesDetailProduct()
                    custlist1.attributeValue = "Select Product"
                    custlist1.attributeId = -1
                    _productList.add(0,custlist1)

                    productListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productListNames)
                    productListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.productSpinner.adapter = productListAdapter
                }
            }

        })

        /*** Submit Purchase Request Observer ***/
        viewModel.purchaseSubmitLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,
                        "Successfully!","Submitted your purchase request", object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.claimBtn.changeState(false,true)

                                binding.qtyTextview.text.clear()
                                userTypeSpinner()
                            }

                        })
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                binding.claimBtn.changeState(false,true)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.qty_plus ->{
                plusMinusBtn = true
                var currentCount: Int

                if (binding.qtyTextview.text.toString().isEmpty()){
                    currentCount = 0
                }else{
                    currentCount = binding.qtyTextview.getText().toString().toInt()
                }
                ++currentCount

                binding.qtyTextview.setText(currentCount.toString())
            }

            R.id.qty_minus ->{
                plusMinusBtn = true

                if (binding.qtyTextview.text.toString().isEmpty() || binding.qtyTextview.text.toString().toInt() == 1)
                    return
                var currCountMin: Int = binding.qtyTextview.text.toString().toInt()
                --currCountMin

                binding.qtyTextview.setText(currCountMin.toString())
            }
        }
    }

}