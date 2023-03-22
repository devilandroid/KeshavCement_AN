package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransfersBinding
import com.loyaltyworks.keshavcement.model.CommonSpinner
import com.loyaltyworks.keshavcement.model.CommonSpinners
import com.loyaltyworks.keshavcement.model.DealerSubDealerListRequest
import com.loyaltyworks.keshavcement.model.LstCustParentChildMappingDealer
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonAdapter
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.InstructionDialog

class CashTransfersFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentCashTransfersBinding
    private lateinit var viewModel: PurchaseRequestViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType: CommonSpinner
    var userTypeId: Int = -1

    var _dealerSubDealerList = mutableListOf<LstCustParentChildMappingDealer>()
    var dealerSubDealerListAdapter: ArrayAdapter<String>? = null
    var dealerSubDealerId: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        binding = FragmentCashTransfersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helpInstruction.setOnClickListener(this)

        binding.userTypeSpinner.onItemSelectedListener = this
        binding.customerNameSpinner.onItemSelectedListener = this

        userTypeSpinner()

    }

    private fun userTypeSpinner() {
        userTypeList.clear()

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            userTypeList.add( CommonSpinner(name = "Select User Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){

            userTypeList.add( CommonSpinner(name = "Select User Type", id = -1))
            userTypeList.add( CommonSpinner(name = "Dealer", id = 3))
            userTypeList.add( CommonSpinner(name = "Sub Dealer", id = 4))

        }

        binding.userTypeSpinner.adapter = SpinnerCommonWhiteTextAdapter(requireActivity(), R.layout.spinner_popup_row,userTypeList)
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
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter


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
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }
            }

        })

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when((parent as Spinner).id){
            R.id.user_type_spinner ->{
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
                    binding.customerNameSpinner.adapter = dealerSubDealerListAdapter
                }
            }

            R.id.customer_name_spinner ->{
                dealerSubDealerId = _dealerSubDealerList[position].loyaltyID.toString()
                Log.d("bhbrfhrb","dealer Sub-Dealer ID : " + dealerSubDealerId)

            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}