package com.loyaltyworks.keshavcement.ui.offersAndPromotions

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
import com.loyaltyworks.keshavcement.databinding.FragmentMyPurchaseClaimBinding
import com.loyaltyworks.keshavcement.databinding.FragmentPromotionsBinding
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.MyPurchaseClaimAdapter


class PromotionsFragment : Fragment(), PromotionsAdapter.OnItemClick {


    private lateinit var binding: FragmentPromotionsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentPromotionsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)

        HandleOnBackPressed()

        binding.promotionsRV.adapter = PromotionsAdapter(this)


    }

    override fun onItemClicked() {
        binding.promotionsRV.visibility = View.GONE
        binding.promotionsRV.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_to_left)
        binding.promotionsDetailsLayout.visibility = View.VISIBLE
        binding.promotionsDetailsLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_from_right)
    }


    /**This is required here for handling action bar navigate up button*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){

            if (binding.promotionsDetailsLayout.visibility == View.VISIBLE) {

                binding.promotionsDetailsLayout.visibility = View.GONE
                binding.promotionsDetailsLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_to_right)
                binding.promotionsRV.visibility = View.VISIBLE
                binding.promotionsRV.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_from_left)

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

                if (binding.promotionsDetailsLayout.visibility == View.VISIBLE) {

                    binding.promotionsDetailsLayout.visibility = View.GONE
                    binding.promotionsDetailsLayout.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_to_right)
                    binding.promotionsRV.visibility = View.VISIBLE
                    binding.promotionsRV.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_from_left)

                } else {

                    isEnabled = false
                    findNavController().popBackStack()

                }

            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }


}