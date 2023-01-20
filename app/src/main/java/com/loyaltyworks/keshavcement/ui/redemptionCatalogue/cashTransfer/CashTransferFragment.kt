package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferBinding
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CashTransferAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.InstructionDialog
import kotlinx.android.synthetic.main.appbar_main.*


class CashTransferFragment : Fragment(), View.OnClickListener, CashTransferAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCashTransferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCashTransferBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            requireActivity().toolbar.title = getString(R.string.cash_voucher)
        }else{
            requireActivity().toolbar.title = getString(R.string.cash_transfer)
        }

        binding.helpInstruction.setOnClickListener(this)

        binding.cashTransferRecycler.adapter = CashTransferAdapter(this)
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

    override fun onListItemClickResponse(itemView: View, position: Int) {
        findNavController().navigate(R.id.action_cashTransferFragment_to_cashTransferDetailsFragment)
    }

    override fun onRedeemBtnClickResponse(itemView: View, position: Int) {
        CashTransferDialog.showCashTransferDialog(requireContext(),PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType), object : CashTransferDialog.CashTransferDialogCallBack{
            override fun onOk() {
//                findNavController().popBackStack()
            }
            override fun onClose() {

            }

        })
    }

}