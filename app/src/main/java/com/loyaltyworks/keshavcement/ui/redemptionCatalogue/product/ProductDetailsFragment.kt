package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProductDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener(this)
        binding.cartButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.cart_button ->{
                findNavController().navigate(R.id.action_productDetailsFragment_to_cartFragment)
            }

            R.id.back_button ->{
                findNavController().popBackStack()
            }
        }
    }

}