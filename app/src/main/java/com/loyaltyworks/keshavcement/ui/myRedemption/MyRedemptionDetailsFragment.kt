package com.loyaltyworks.keshavcement.ui.myRedemption

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMyRedemptionDetailsBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.myRedemption.adapter.RedemptionTrackerAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class MyRedemptionDetailsFragment : Fragment() {
    private lateinit var _binding: FragmentMyRedemptionDetailsBinding
    private val binding get() = _binding!!

    private lateinit var viewModel: MyRedemptionViewModel

    private lateinit var rewardTransDetails: ObjCatalogueRedemReq

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MyRedemptionViewModel::class.java)
        _binding = FragmentMyRedemptionDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            rewardTransDetails = arguments?.getSerializable("myRedemptionDetails") as ObjCatalogueRedemReq
        }

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_MyRedemptionDetailsView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_MyRedemptionDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)

        callApi()
        statusApi()

    }

    private fun callApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getMyRedemptionDetailData(
            MyRedemptionDetailsRequest(
                actionType = "53",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ObjCatalogueDetails(
                    redemptionId = rewardTransDetails.redemptionId.toString()
                )
            )
        )


    }

    private fun statusApi(){
        viewModel.getMyRedemptionStatusDetailData(
            RedemptionHistoryRequest(
                actionType = 263,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                ObjCatalogueDetailsss(
                    redemptionRefno = rewardTransDetails.redemptionRefno.toString()
                )
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Redemption Details Observer ***/
        viewModel.myRedemptionDetailsLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.objCatalogueList.isNullOrEmpty()){

                if (it.objCatalogueList[0].redeemedCatalogueType.equals("Gift Voucher",true) ||
                    it.objCatalogueList[0].redeemedCatalogueType.equals("Voucher",true)){
                    binding.categoryLayout.visibility = View.GONE
                    binding.quantityLayout.visibility = View.GONE
                }else{
                    binding.categoryLayout.visibility = View.VISIBLE
                    binding.quantityLayout.visibility = View.VISIBLE
                }

                if (it.objCatalogueList[0].catogoryName.toString() != "null"){
                    binding.catetogryType.text = it.objCatalogueList[0].catogoryName.toString()
                }else{
                    binding.catetogryType.text = "-"
                }

                binding.prodRefNo.text = it.objCatalogueList[0].redemptionRefno.toString()
                binding.productName.text = it.objCatalogueList[0].productName.toString()
//                binding.pointsPerProduct.text = it.objCatalogueList[0].pointsPerUnit.toString()
                binding.ovrllPoints.text = it.objCatalogueList[0].pointsRequired.toString()
                binding.quantity.text = it.objCatalogueList[0].noOfQuantity.toString()
                binding.otherInformation.text = it.objCatalogueList[0].termsCondition.toString()
                binding.longDesc.text = it.objCatalogueList[0].productDesc.toString()
//                binding.detailsProdCode.text = getString(R.string.product_code) + it.objCatalogueList[0].productCode.toString()
//                binding.detailsDeliveryType.text = getString(R.string.delivery_type) + it.objCatalogueList[0].deliveryType.toString()

                Glide.with(this)
                    .load(
                        BuildConfig.CATALOGUE_IMAGE_BASE + it.objCatalogueList[0].productImage.toString()
                    )
                    .placeholder(R.drawable.ic_default_img)
                    .fitCenter()
                    .into(binding.productImage)

            }
        })

        /*** Redemption Status Observer ***/
        viewModel.myRedemptionStatusDetailsLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null && !it.objCatalogueList.isNullOrEmpty()){
                Log.d("adhjhfjdsk", Gson().toJson(it.objCatalogueList))
                binding.redeemRecycler.adapter = RedemptionTrackerAdapter(it.objCatalogueList)
                binding.redeemRecycler.visibility = View.VISIBLE


            }
        })

    }
}