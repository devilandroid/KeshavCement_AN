package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentEvouchersBinding
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.VoucherAdapter
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog


class EvouchersFragment : Fragment(), VoucherAdapter.voucherListAdpaterCallback {
    private lateinit var binding: FragmentEvouchersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEvouchersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "VouchersView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "VouchersFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.voucherRecycler.adapter = VoucherAdapter(this)
    }

    override fun onDetailVoucherFromAdapter(itemView: View, position: Int) {
        findNavController().navigate(R.id.voucherDialogFragment, /*bundle*/)
    }

    override fun onRedeemVoucherFromAdapter(itemView: View, position: Int) {
        RedeemOTPDialog.showRedeemOTPDialog(requireContext(),"123456",object :RedeemOTPDialog.RedeemOTPDialogCallBack{
            override fun onOk() {

            }

            override fun onRedeemClick() {
                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","You have redeemed your product"
                ,object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                        override fun onOk() {

                        }

                    })
            }

        })

    }

}