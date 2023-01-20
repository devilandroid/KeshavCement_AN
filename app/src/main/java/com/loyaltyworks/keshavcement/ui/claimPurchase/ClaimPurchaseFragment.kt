package com.loyaltyworks.keshavcement.ui.claimPurchase

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentClaimPurchaseBinding
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog


class ClaimPurchaseFragment : Fragment() {
    private lateinit var binding: FragmentClaimPurchaseBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClaimPurchaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.claimBtn.setOnStateChangeListener {
            if (it){

                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,
                "Successfully!","Submitted your purchase request", object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                        override fun onOk() {
                            binding.claimBtn.changeState(false,true)
                        }

                    })
                /*Handler().postDelayed({

                },3000L)*/
            }
        }
    }

}