package com.loyaltyworks.keshavcement.ui.pendingClaimRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPendingClaimRequestBinding
import com.loyaltyworks.keshavcement.ui.myPurchaseClaim.MyPurchaseClaimViewModel
import com.loyaltyworks.keshavcement.ui.pendingClaimRequest.adapter.PendingClaimRequestAdapter


class PendingClaimRequestFragment : Fragment() {
  private lateinit var binding: FragmentPendingClaimRequestBinding
    private lateinit var viewModel: PendingClaimViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PendingClaimViewModel::class.java)
        binding = FragmentPendingClaimRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "PendingClaimRequestView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PendingClaimRequestFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.pendingClaimRecycler.adapter = PendingClaimRequestAdapter()
    }

}