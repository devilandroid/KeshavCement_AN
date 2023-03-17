package com.loyaltyworks.keshavcement.ui.dreamGift

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentDreamGiftBinding
import com.loyaltyworks.keshavcement.model.DreamGiftRemoveRequest
import com.loyaltyworks.keshavcement.model.DreamGiftRequest
import com.loyaltyworks.keshavcement.model.LstDreamGift
import com.loyaltyworks.keshavcement.ui.dreamGift.adapter.DreamGiftAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class DreamGiftFragment : Fragment() ,DreamGiftAdapter.OnGiftItemCallBack{
    private lateinit var binding: FragmentDreamGiftBinding
    private lateinit var viewModel: DreamGiftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(DreamGiftViewModel::class.java)
        binding = FragmentDreamGiftBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_DreamGiftView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_DreamGiftFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        CallApi()


    }

    private fun CallApi() {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getDreamGiftData(
            DreamGiftRequest(
                actionType = "1",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                status = "2",
                loyaltyId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userName.toString(),
                domain = "KESHAV_CEMENT"
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Dream Gift List Observer  ***/
        viewModel.dreamGiftLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if ( it != null && !it.lstDreamGift.isNullOrEmpty()){

                binding.dreamGiftRv.adapter = DreamGiftAdapter(it.lstDreamGift, this)
                binding.dreamGiftRv.visibility = View.VISIBLE
                binding.noDataFount.noDataFoundLayout.visibility = View.GONE

            }else{

                binding.dreamGiftRv.visibility = View.GONE
                binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE

            }
        })

        /*** Dream Gift remove Observer ***/
        viewModel.dreamGiftRemoveLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.returnValue == 1){
                    CallApi()
                    Toast.makeText(requireContext(), getString(R.string.dream_gift_removed_successfully), Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(requireContext(),getString(R.string.dream_gift_remove_failed), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onGiftDetailClickResponse(itemView: View, lstDreamGift: LstDreamGift) {
        val bundle = Bundle()
        bundle.putSerializable("DreamGift",lstDreamGift)
        findNavController().navigate(R.id.action_dreamGiftFragment_to_dreamGiftDetailsFragment,bundle)

    }

    override fun onGiftRemoveClickResponse(itemView: View, lstDreamGift: LstDreamGift) {
        viewModel.getDreamGiftRemoveData(
            DreamGiftRemoveRequest(
                actionType = 4,
                dreamGiftId = lstDreamGift.dreamGiftId!!.toString(),
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                giftStatusId = 4
            )
        )
    }

    override fun onRedeemCallback(lstDreamGift: LstDreamGift) {
        val bundle = Bundle()
        bundle.putSerializable("DreamGift",lstDreamGift)
        findNavController().navigate(R.id.dreamGiftAddressFragment,bundle)
    }

    override fun onHoldAddtoCart() {
        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
            getString(R.string.not_allowed_to_redeem_contact_administrator),object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                override fun onOk() {
                }
            })
    }

}