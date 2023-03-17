package com.loyaltyworks.keshavcement.ui.offersAndPromotions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentPromotionsDetailsBinding
import com.loyaltyworks.keshavcement.model.LstPromotionJson
import com.loyaltyworks.keshavcement.model.PromotionDetailsRequest
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class PromotionsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPromotionsDetailsBinding
    private lateinit var viewModel: PromotionViewModel

    var lstPromotionJson: LstPromotionJson? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)
        binding = FragmentPromotionsDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_PromotionsDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_PromotionsDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if(arguments != null){
            lstPromotionJson = requireArguments().getSerializable("SelectedPromo") as LstPromotionJson
        }

        GetPromotionsDetailsRequest()
    }

    private fun GetPromotionsDetailsRequest() {
        viewModel.getPromotionDetailData(
            PromotionDetailsRequest(
            actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
            promotionId = lstPromotionJson!!.promotionId.toString()
        ))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.promotionDetailsLiveData.observe(viewLifecycleOwner){
            if(it!=null && !it.lstPromotionJsonList.isNullOrEmpty()){
                binding.promTxtTitle.text = it.lstPromotionJsonList[0].promotionName.toString()
                binding.promTxtDesc.text = it.lstPromotionJsonList[0].proShortDesc.toString()

                Glide.with(this)
                    .load(BuildConfig.PROMO_IMAGE_BASE + it.lstPromotionJsonList[0].proImage.toString())
                    .placeholder(R.drawable.ic_default_img)
                    .fitCenter()
                    .into(binding.promoImage)
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        }
    }
}