package com.loyaltyworks.keshavcement.ui.customerType

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCustomerTypeSelectionBinding
import com.loyaltyworks.keshavcement.model.CustomerTypeRequest
import com.loyaltyworks.keshavcement.model.LstAttributesDetail
import com.loyaltyworks.keshavcement.ui.customerType.adapter.CustomerTypeAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class CustomerTypeSelectionFragment : Fragment(), CustomerTypeAdapter.OnItemClickCallBack {
    private lateinit var binding: FragmentCustomerTypeSelectionBinding
    private lateinit var viewModel: CustomerTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(CustomerTypeViewModel::class.java)
        binding = FragmentCustomerTypeSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CustomerTypeSelectionView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CustomerTypeSelectionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        customerTypeApi()
    }

    private fun customerTypeApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCustomerTypeRequest(
            CustomerTypeRequest(
                actionType = 33
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCustomerTypeResponse.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()){
                binding.customerTypeRecycler.visibility = View.VISIBLE
                binding.noDataFount.noDataFoundLayout.visibility = View.GONE

                binding.customerTypeRecycler.adapter = CustomerTypeAdapter(it.lstAttributesDetails,this)

            }else{
                binding.customerTypeRecycler.visibility = View.GONE
                binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
            }
        })
    }

    override fun onCustomerTypeListClickResponse(
        itemView: View,
        lstAttributesDetails: LstAttributesDetail
    ) {
        PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,lstAttributesDetails.attributeId.toString())
        findNavController().navigate(R.id.action_customerTypeSelectionFragment_to_loginFragment)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

}