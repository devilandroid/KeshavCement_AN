package com.loyaltyworks.keshavcement.ui.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentLoginBinding


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    var customerType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerType = arguments?.getSerializable("customerType") as String

        if (customerType == "supportExecutive"){
            binding.activateLayout.visibility = View.GONE
            binding.forgotPassword.visibility = View.GONE
            binding.registerLayout.visibility = View.GONE
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }


}