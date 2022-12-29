package com.loyaltyworks.keshavcement.ui.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCustomerTypeSelectionBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick


class CustomerTypeSelectionFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCustomerTypeSelectionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerTypeSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.engineerCard.setOnClickListener(this)
        binding.masonCard.setOnClickListener(this)
        binding.dealerCard.setOnClickListener(this)
        binding.subDealerCard.setOnClickListener(this)
        binding.supportExecutiveCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.engineer_card ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", "engineer")
                findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.mason_card ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", "mason")
                findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.dealer_card ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", "dealer")
                findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.sub_dealer_card ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", "subDealer")
                findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.support_executive_card ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", "supportExecutive")
                findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }
    }

}