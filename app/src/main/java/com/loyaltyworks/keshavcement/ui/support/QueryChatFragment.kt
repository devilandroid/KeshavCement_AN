package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.databinding.FragmentQueryChatBinding
import com.loyaltyworks.keshavcement.ui.support.adapter.QueryChatAdapter


class QueryChatFragment : Fragment() {

    private lateinit var binding: FragmentQueryChatBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQueryChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "QueryChatView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "QueryChatFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.queryChatRecycler.adapter = QueryChatAdapter()
    }


}