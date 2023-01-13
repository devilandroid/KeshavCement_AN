package com.loyaltyworks.keshavcement.ui.myRedemption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMyRedemptionBinding
import com.loyaltyworks.keshavcement.ui.myRedemption.adapter.MyRedemptionAdapter


class MyRedemptionFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: FragmentMyRedemptionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMyRedemptionBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)

        HandleOnBackPressed()

        binding.myRedemptionRV.adapter = MyRedemptionAdapter()

        binding.filterOpen.setOnClickListener(this)
        binding.filterClose.setOnClickListener(this)
    }

    /**This is required here for handling action bar navigate up button*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){

            if (binding.filterLayout.visibility == View.VISIBLE) {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)

            } else {

                requireActivity().onBackPressed()

            }

            return true


        }else{

            return super.onOptionsItemSelected(item)
        }

    }

    private fun HandleOnBackPressed() {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (binding.filterLayout.visibility == View.VISIBLE) {

                    binding.filterLayout.visibility = View.GONE
                    binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)

                } else {

                    isEnabled = false
                    findNavController().popBackStack()

                }

            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.filterOpen -> {

                binding.filterLayout.visibility = View.VISIBLE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_up_dialog)
            }

            R.id.filterClose -> {

                binding.filterLayout.visibility = View.GONE
                binding.filterLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down_dialog)
            }
        }

    }

}