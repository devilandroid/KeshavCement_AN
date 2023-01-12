package com.loyaltyworks.keshavcement.ui.redemptionCatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentRedemptionTypeBinding


class RedemptionTypeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding:FragmentRedemptionTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRedemptionTypeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.products.setOnClickListener(this)
        binding.evouchers.setOnClickListener(this)
        binding.cashtransfer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.products -> {

                findNavController().navigate(R.id.productFragment)
            }

            R.id.evouchers -> {

                findNavController().navigate(R.id.evouchersFragment)
            }

            R.id.cashtransfer -> {

                findNavController().navigate(R.id.cashTransferFragment)
            }
        }

    }


}