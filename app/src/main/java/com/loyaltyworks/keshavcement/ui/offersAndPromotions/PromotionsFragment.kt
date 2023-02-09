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
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPromotionsBinding
import com.loyaltyworks.keshavcement.ui.offersAndPromotions.adapter.PromotionsAdapter


class PromotionsFragment : Fragment(), PromotionsAdapter.OnPromotionItemClick {


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
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "PromotionsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PromotionsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.promotionsRV.adapter = PromotionsAdapter(this)


    }

    override fun onOffersItemClicked() {
//        val bundle = Bundle()
//        bundle.putSerializable("offerPromotions", lstPromotionJson)
        findNavController().navigate(R.id.action_promotionsFragment_to_promotionsDetailsFragment, /*bundle*/)
    }


}