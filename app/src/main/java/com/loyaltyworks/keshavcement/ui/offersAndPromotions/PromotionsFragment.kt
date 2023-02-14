package com.loyaltyworks.keshavcement.ui.offersAndPromotions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPromotionsBinding
import com.loyaltyworks.keshavcement.model.LstPromotionJson
import com.loyaltyworks.keshavcement.model.PromotionListRequest
import com.loyaltyworks.keshavcement.ui.offersAndPromotions.adapter.PromotionsAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class PromotionsFragment : Fragment(), PromotionsAdapter.OnOfferDetailsCallback {
    private lateinit var binding: FragmentPromotionsBinding
    private lateinit var viewModel: PromotionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)
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

        GetPromotionsRequest()

    }

    private fun GetPromotionsRequest() {

        LoadingDialogue.showDialog(requireContext())

        viewModel.getPromotionListData(
            PromotionListRequest(
                actionType = "99",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.promotionListLiveData.observe(viewLifecycleOwner){

            LoadingDialogue.dismissDialog()
            if(it != null && !it.lstPromotionJsonList.isNullOrEmpty()){

                binding.promotionsRV.visibility = View.VISIBLE
                binding.noDataFount.noDataFoundLayout.visibility = View.GONE
                binding.promotionsRV.adapter = PromotionsAdapter(it.lstPromotionJsonList,this)

            }else{

                binding.promotionsRV.visibility = View.GONE
                binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE

            }
        }
    }

    override fun onOfferDetailsItemClickResponse(
        itemView: View,
        lstPromotionJson: LstPromotionJson
    ) {

        val bundle = Bundle()
        bundle.putSerializable("SelectedPromo", lstPromotionJson)

        findNavController().navigate(R.id.action_promotionsFragment_to_promotionsDetailsFragment,bundle)

    }




}