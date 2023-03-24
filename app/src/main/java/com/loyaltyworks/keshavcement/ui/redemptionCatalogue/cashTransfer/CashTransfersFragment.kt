package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransfersBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.model.adapter.CashbackAdapter
import com.loyaltyworks.keshavcement.model.adapter.SpinnerCommonWhiteTextAdapter
import com.loyaltyworks.keshavcement.model.adapter.StateAdapter
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.purchaseRequest.PurchaseRequestViewModel
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.InstructionDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.TimeChangeDialog

class CashTransfersFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentCashTransfersBinding
    private lateinit var purchaseViewModel: PurchaseRequestViewModel
    private lateinit var viewModel: CashTransferViewModel

    var userTypeList = mutableListOf<CommonSpinner>()
    private lateinit var mSelecteduserType: CommonSpinner
    var userTypeId: Int = -1

    var _dealerSubDealerList = mutableListOf<LstCustParentChildMappingDealer>()
    var dealerSubDealerListAdapter: ArrayAdapter<String>? = null
    var dealerSubDealerId: String = "-1"
    var dealerSubDealerLoyltyId: String = "-1"

    var cashbackPointsList = mutableListOf<LstCashTransfer>()
    var mSelectedCashback: LstCashTransfer? = null
    var selectedAmount = ""

    var customerTypeIds = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        purchaseViewModel = ViewModelProvider(this).get(PurchaseRequestViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CashTransferViewModel::class.java)
        binding = FragmentCashTransfersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helpInstruction.setOnClickListener(this)

        binding.userTypeSpinner.onItemSelectedListener = this
        binding.customerNameSpinner.onItemSelectedListener = this
        binding.pointsSpinner.onItemSelectedListener = this



//        binding.redeemPoints.text = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance.toString()

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer){
            customerTypeIds = "1"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            customerTypeIds = "2"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            customerTypeIds = "4"
        }

        userTypeSpinner()
        cashbackPointsApi()

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
                }else if (selectedAmount == "Select Points"){
                    Toast.makeText(requireContext(), getString(R.string.please_select_points), Toast.LENGTH_SHORT).show()
                    binding.claimBtn.changeState(false,true)
                    return@setOnStateChangeListener
                }else{
                    if(Settings.Global.getInt(requireActivity().contentResolver, Settings.Global.AUTO_TIME) == 1)
                    {
                        submitCashTransferApi()
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

    private fun submitCashTransferApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCashTransferSubmitData(
            CashTransferSubmitRequest(
                actionType = 2,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                partyLoyaltyId = dealerSubDealerLoyltyId,
                points = selectedAmount,
                customerTypeId = customerTypeIds

            )
        )

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
        purchaseViewModel.dealerSubDealerListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstCustParentChildMapping.isNullOrEmpty()){
                    val dealerSubDealerLists: MutableList<LstCustParentChildMappingDealer> = it.lstCustParentChildMapping!!.toMutableList()
                    _dealerSubDealerList = dealerSubDealerLists

                    val dealerSubDealerListName = ArrayList<String>()

                    for (commonSpinner in dealerSubDealerLists) {
                        dealerSubDealerListName.add(commonSpinner.firstName!! + " ( " + commonSpinner.loyaltyID + " )")
//                        dealerSubDealerListName.add(commonSpinner.firstName!!)
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

        /*** Cashback Points List Observer ***/
        viewModel.cashbackPointsListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null){
                    binding.redeemPoints.text = it.redeemablePointBalance.toString()

                    if (!it.lstCashTransfer.isNullOrEmpty()){
                        cashbackPointsList.clear()

                        cashbackPointsList.addAll(it.lstCashTransfer!!)
                        cashbackPointsList.add(0, LstCashTransfer(
                            amount = "Select Points",
                            cashBackValue = -1
                        ))

                        binding.pointsSpinner.adapter = CashbackAdapter(requireContext(),android.R.layout.simple_spinner_item,cashbackPointsList)

                    }else{
                        cashbackPointsList.clear()
                        cashbackPointsList.add(0, LstCashTransfer(
                            amount = "Select Points",
                            cashBackValue = -1
                        )
                        )

                        binding.pointsSpinner.adapter = CashbackAdapter(requireContext(), android.R.layout.simple_spinner_item,cashbackPointsList)
                    }

                }
            }


        })

        /*** Cash Transfer Submit Observer ***/
        viewModel.cashTransferSubmitLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null){
                if (it.returnValue!! > 0){
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,
                        "Successfull!","Transfer request submitted", object :
                            ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                binding.claimBtn.changeState(false,true)
                                userTypeSpinner()
                                cashbackPointsApi()
                                binding.pointsRupees.text = ""
                            }

                        })
                }else{
                    (activity as DashboardActivity).snackBar(getString(R.string.something_went_wrong_please_try_again_later), R.color.red)
                }

            }else{
                (activity as DashboardActivity).snackBar(getString(R.string.something_went_wrong_please_try_again_later), R.color.red)
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
                    purchaseViewModel.getDealerSubDealerListData(
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
                dealerSubDealerId = _dealerSubDealerList[position].userID.toString()
                dealerSubDealerLoyltyId = _dealerSubDealerList[position].loyaltyID.toString()
                Log.d("bhbrfhrb","dealer Sub-Dealer User ID : " + dealerSubDealerId)
                Log.d("bhbrfhrb","dealer Sub-Dealer Loylty ID : " + dealerSubDealerLoyltyId)
            }

            R.id.points_spinner ->{
                mSelectedCashback = parent.getItemAtPosition(position) as LstCashTransfer
                selectedAmount = mSelectedCashback!!.amount!!
                if (selectedAmount != "Select Points"){
                    binding.pointsRupees.text = mSelectedCashback!!.cashBackValue.toString()
                }

            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun cashbackPointsApi() {
        viewModel.getCashbackPointsListData(
            CashbackPointsListRequest(
                actionType = 1,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                customerTypeId = customerTypeIds
            )
        )
    }



}