package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCartBinding
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CartAdapter


class CartFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartRecycler.adapter = CartAdapter()

        binding.checkoutBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.checkout_btn ->{
                findNavController().navigate(R.id.action_cartFragment_to_addressFragment)
            }
        }
    }


}