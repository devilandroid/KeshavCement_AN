package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentAddressBinding
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.OrderConfirmAdapter
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog


class AddressFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddressBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AddressView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AddressFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.orderConfirmRecycler.adapter = OrderConfirmAdapter()

        binding.editBtn.setOnClickListener(this)


        binding.redeemSwapBtn.setOnStateChangeListener {
            if (it){
                RedeemOTPDialog.showRedeemOTPDialog(requireContext(),"123456",object :
                    RedeemOTPDialog.RedeemOTPDialogCallBack{
                    override fun onOk() {
                        binding.redeemSwapBtn.changeState(false,true)
                    }

                    override fun onRedeemClick() {
                        binding.redeemSwapBtn.changeState(false,true)
                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!",
                            "You have successfully redeemed a product",object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                    requireActivity().finish();
                                    requireActivity().overridePendingTransition( 0, 0);
                                    startActivity(requireActivity().intent);
                                    requireActivity().overridePendingTransition( 0, 0);
                                }

                            })
                    }

                })

            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.edit_btn ->{
                findNavController().navigate(R.id.action_addressFragment_to_editAddressFragment)
            }

        }
    }

}