package com.loyaltyworks.keshavcement.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProfileBinding
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.aadharLayout.visibility = View.VISIBLE
            binding.gstLayout.visibility = View.GONE
        }else {
            binding.aadharLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.VISIBLE
        }

    }

}