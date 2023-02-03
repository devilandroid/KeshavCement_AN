package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.customerSelection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCustomerSelectionBinding
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CustomerSelectionAdapter


class CustomerSelectionFragment : Fragment(), CustomerSelectionAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCustomerSelectionBinding

    var directedFrom: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CatalogueCustomerSelectionView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CatalogueCustomerSelectionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)


        directedFrom = arguments?.getSerializable("directedFrom") as String

        binding.customerRecycler.adapter = CustomerSelectionAdapter(this)
    }

    override fun onCustListItemClickResponse(itemView: View, position: Int) {
        if (directedFrom == "ProductClick"){
            findNavController().navigate(R.id.productFragment)
        }else if (directedFrom == "VoucherClick"){
            findNavController().navigate(R.id.evouchersFragment)
        }


    }

}