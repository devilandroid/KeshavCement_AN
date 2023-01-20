package com.loyaltyworks.keshavcement.ui.pendingClaimRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPendingClaimRequestBinding
import com.loyaltyworks.keshavcement.ui.pendingClaimRequest.adapter.PendingClaimRequestAdapter


class PendingClaimRequestFragment : Fragment() {
  private lateinit var binding: FragmentPendingClaimRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendingClaimRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pendingClaimRecycler.adapter = PendingClaimRequestAdapter()
    }

}