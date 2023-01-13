package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.queryChatRecycler.adapter = QueryChatAdapter()
    }


}