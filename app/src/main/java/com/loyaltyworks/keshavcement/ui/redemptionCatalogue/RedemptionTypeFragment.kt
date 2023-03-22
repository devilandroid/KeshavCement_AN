package com.loyaltyworks.keshavcement.ui.redemptionCatalogue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentRedemptionTypeBinding
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.DeliveryTypeDialog


class RedemptionTypeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding:FragmentRedemptionTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRedemptionTypeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_RedemptionTypeView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_RedemptionTypeFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.redeemPoints.text = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance.toString()

        binding.products.setOnClickListener(this)
        binding.wishlist.setOnClickListener(this)
        binding.evouchers.setOnClickListener(this)
        binding.cashVoucher.setOnClickListener(this)
        binding.cashTransfer.setOnClickListener(this)

        Log.d("hbdfhbfhb","customer type : " + PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType))

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.cashVoucher.visibility = View.VISIBLE
            binding.cashTransfer.visibility = View.GONE
        }else{
            binding.cashVoucher.visibility = View.GONE
            binding.cashTransfer.visibility = View.VISIBLE
        }

        /*** Set PointBalance & Mobile & Name to Preference ***/
        PreferenceHelper.setStringValue(requireContext(),BuildConfig.RedeemablePointsBalance,
            PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance.toString())

        PreferenceHelper.setStringValue(requireContext(),BuildConfig.SelectedCustomerMobile,
            PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].customerMobile.toString())

        PreferenceHelper.setStringValue(requireContext(),BuildConfig.SelectedCustomerName,
            PreferenceHelper.getDashboardDetails(requireContext())!!.lstCustomerFeedBackJsonApi!![0].firstName.toString())

    }


    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.products -> {

                if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer ||
                    PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){

                    DeliveryTypeDialog.showDeliveryTypeDialog(requireContext(),object :DeliveryTypeDialog.DeliveryTypeDialogCallBack{
                        override fun forSelfClick() {
                            findNavController().navigate(R.id.productFragment)
                        }

                        override fun forOthersClick() {
                            val bundle = Bundle()
                            bundle.putSerializable("directedFrom", "ProductClick")
                            findNavController().navigate(R.id.customerSelectionFragment,bundle)
                        }

                    })

                }else{
                    findNavController().navigate(R.id.productFragment)
                }
            }

            R.id.wishlist -> {

                if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer ||
                    PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){

                    DeliveryTypeDialog.showDeliveryTypeDialog(requireContext(),object :DeliveryTypeDialog.DeliveryTypeDialogCallBack{
                        override fun forSelfClick() {
                            findNavController().navigate(R.id.wishlistFragment)
                        }

                        override fun forOthersClick() {
                            val bundle = Bundle()
                            bundle.putSerializable("directedFrom", "WishlistClick")
                            findNavController().navigate(R.id.customerSelectionFragment,bundle)
                        }

                    })

                }else{
                    findNavController().navigate(R.id.wishlistFragment)
                }
            }

            R.id.evouchers -> {
                if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer ||
                    PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){

                    DeliveryTypeDialog.showDeliveryTypeDialog(requireContext(),object :DeliveryTypeDialog.DeliveryTypeDialogCallBack{
                        override fun forSelfClick() {
                            findNavController().navigate(R.id.evouchersFragment)
                        }

                        override fun forOthersClick() {
                            val bundle = Bundle()
                            bundle.putSerializable("directedFrom", "VoucherClick")
                            findNavController().navigate(R.id.customerSelectionFragment,bundle)
                        }

                    })

                }else{
                    findNavController().navigate(R.id.evouchersFragment)
                }

            }

            R.id.cashVoucher -> {
                findNavController().navigate(R.id.cashVoucherFragment)
            }

            R.id.cashTransfer -> {
                findNavController().navigate(R.id.cashTransfersFragment)
            }
        }

    }


}