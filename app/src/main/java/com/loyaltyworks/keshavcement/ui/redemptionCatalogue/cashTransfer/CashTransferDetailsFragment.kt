package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferDetailsBinding
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class CashTransferDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCashTransferDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCashTransferDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.redeemBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.redeem_btn ->{

                CashTransferDialog.showCashTransferDialog(requireContext(),PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType), object : CashTransferDialog.CashTransferDialogCallBack{
                    override fun onOk() {
                        findNavController().popBackStack()
                    }
                    override fun onClose() {

                    }

                })
            }
        }
    }

}