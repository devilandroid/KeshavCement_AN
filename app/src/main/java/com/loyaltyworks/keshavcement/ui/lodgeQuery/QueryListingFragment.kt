package com.loyaltyworks.keshavcement.ui.lodgeQuery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentQueryListingBinding
import com.loyaltyworks.keshavcement.ui.offersAndPromotions.PromotionsAdapter
import com.loyaltyworks.keshavcement.utils.dialog.HelpTopicsDialog


class QueryListingFragment : Fragment(), PromotionsAdapter.OnItemClick, View.OnClickListener,
    HelpTopicsDialog.OnItemClickCallback {


    private lateinit var binding: FragmentQueryListingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentQueryListingBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.queryListingRV.adapter = QueryListingAdapter(this)

        binding.newTicketLl.setOnClickListener(this)


    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.queryChatFragment)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.new_ticket_ll -> {

                 HelpTopicsDialog.showDialog(requireContext(),this)

            }
        }
    }

    override fun onHelpTopicsItemClicked() {
        findNavController().navigate(R.id.newQueryFragment)
    }


}