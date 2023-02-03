package com.loyaltyworks.keshavcement.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentDashboardBinding
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class DashboardFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "DashboardView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "DashboardFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.dashEnrollment.visibility = View.GONE
            binding.dashPendingClaimRequest.visibility = View.GONE
            binding.dashCashTransferApproval.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.startSellingLayout.visibility = View.GONE
            binding.saleAndEarnLayout.visibility = View.GONE
            binding.earnPointClaimPurchaseLayout.visibility = View.VISIBLE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.VISIBLE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.GONE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE
            binding.dashPendingRequest.visibility = View.GONE
            binding.dashMyActivity.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.VISIBLE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.GONE

            binding.helpLayout.visibility = View.VISIBLE
            binding.supportExecutiveLayout.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            binding.dashMyPurchaseClaim.visibility = View.GONE
            binding.dashCatalogue.visibility = View.GONE
            binding.dashMyRedemption.visibility = View.GONE
            binding.dashMyEarning.visibility = View.GONE
            binding.dashOffers.visibility = View.GONE
            binding.dashWorksite.visibility = View.GONE
            binding.dashReferEarn.visibility = View.GONE

            binding.dashPendingClaimRequest.visibility = View.GONE
            binding.dashCashTransferApproval.visibility = View.GONE
            binding.dashClaimPurchase.visibility = View.GONE

            binding.saleAndEarnLayout.visibility = View.GONE
            binding.earnPointClaimPurchaseLayout.visibility = View.GONE
            binding.startSellingLayout.visibility = View.VISIBLE

            binding.helpLayout.visibility = View.GONE
            binding.supportExecutiveLayout.visibility = View.VISIBLE

        }

        binding.dashEnrollment.setOnClickListener(this)                 //  Dealer & Sub-Dealer & Support-Executive
        binding.dashPendingClaimRequest.setOnClickListener(this)        //  Dealer & Sub-Dealer
        binding.dashCashTransferApproval.setOnClickListener(this)       //  Dealer & Sub-Dealer
        binding.dashClaimPurchase.setOnClickListener(this)              //  Sub-Dealer
        binding.dashMyPurchaseClaim.setOnClickListener(this)            //  Engineer & Mason
        binding.dashCatalogue.setOnClickListener(this)                  //  All except Support-Executive
        binding.dashMyRedemption.setOnClickListener(this)               //  All except Support-Executive
        binding.dashMyEarning.setOnClickListener(this)                  //  All except Support-Executive
        binding.dashOffers.setOnClickListener(this)                     //  All except Support-Executive
        binding.dashWorksite.setOnClickListener(this)                   //  Engineer & Mason
        binding.dashReferEarn.setOnClickListener(this)                  //  Engineer & Mason
        binding.dashPendingRequest.setOnClickListener(this)             //  Support-Executive
        binding.dashMyActivity.setOnClickListener(this)                 //  Support-Executive

        binding.dashEarnPointClaimPurchase.setOnClickListener(this)     //  Engineer & Mason
        binding.saleAndEarn.setOnClickListener(this)                    //  Dealer & Sub-Dealer
        binding.newSale.setOnClickListener(this)                        //  Support Executive

        binding.dashRaiseTicket.setOnClickListener(this)                //  All except Support-Executive

        /*** HardCode Remove this after getting api ***/
        setUi()
    }

    private fun setUi() {
        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Engineer){
            binding.custType.text = "Engineer"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.custType.text = "Mason"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.custType.text = "Dealer"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.custType.text = "SubDealer"
        }else if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
            binding.custType.text = "SupportExecutive"
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.dash_enrollment ->{
                findNavController().navigate(R.id.enrollmentFragment)
            }

            R.id.dash_pending_request,
            R.id.dash_pending_claim_request ->{
                findNavController().navigate(R.id.pendingClaimRequestFragment)
            }

            R.id.dash_cash_transfer_approval ->{
                findNavController().navigate(R.id.cashTransferApprovalFragment)
            }

            R.id.dash_my_purchase_claim ->{
                findNavController().navigate(R.id.myPurchaseClaimFragment)
            }

            R.id.dash_claim_purchase ->{
                findNavController().navigate(R.id.claimPurchaseFragment)
            }

            R.id.dash_catalogue ->{
                findNavController().navigate(R.id.redemptionTypeFragment)
            }

            R.id.dash_my_redemption ->{
                findNavController().navigate(R.id.myRedemptionFragment)
            }

            R.id.dash_my_earning ->{
                findNavController().navigate(R.id.myEarningFragment)
            }

            R.id.dash_offers ->{
                findNavController().navigate(R.id.promotionsFragment)
            }

            R.id.dash_worksite ->{
                findNavController().navigate(R.id.worksiteDetailsFragment)
            }

            R.id.dash_refer_earn ->{
                findNavController().navigate(R.id.referFragment)
            }

            R.id.dash_my_activity ->{
                findNavController().navigate(R.id.myActivityFragment)
            }

            R.id.sale_and_earn ->{
                findNavController().navigate(R.id.newSaleFragment)
            }

            R.id.new_sale ->{
                findNavController().navigate(R.id.newSaleFragment)
            }

            R.id.dash_earn_point_claim_purchase ->{
                findNavController().navigate(R.id.claimPurchaseFragment)
            }

            R.id.dash_raise_ticket ->{
                findNavController().navigate(R.id.supportFragment)
            }

        }
    }


}