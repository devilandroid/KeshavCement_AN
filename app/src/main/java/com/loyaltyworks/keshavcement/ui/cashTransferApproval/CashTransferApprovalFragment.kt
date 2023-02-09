package com.loyaltyworks.keshavcement.ui.cashTransferApproval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferApprovalBinding
import com.loyaltyworks.keshavcement.ui.cashTransferApproval.adapter.CashTransferApprovalAdapter


class CashTransferApprovalFragment : Fragment() {
    private lateinit var binding: FragmentCashTransferApprovalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCashTransferApprovalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CashTransferApprovalView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CashTransferApprovalFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.cashTransferApprovalRecycler.adapter = CashTransferApprovalAdapter()

    }

}