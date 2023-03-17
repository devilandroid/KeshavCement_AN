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
import com.loyaltyworks.keshavcement.databinding.FragmentDreamGiftDetailsBinding
import com.loyaltyworks.keshavcement.model.DreamGiftDetailRequest
import com.loyaltyworks.keshavcement.model.DreamGiftRemoveRequest
import com.loyaltyworks.keshavcement.model.LstDreamGift
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue

class DreamGiftDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentDreamGiftDetailsBinding
    private lateinit var viewModel: DreamGiftViewModel

    private lateinit var lstDreamGift: LstDreamGift
    var isRedeemable: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(DreamGiftViewModel::class.java)
        binding = FragmentDreamGiftDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_DreamGiftDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_DreamGiftDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if(arguments != null){
            lstDreamGift = requireArguments().getSerializable("DreamGift") as LstDreamGift
        }

        binding.removeBtn.setOnClickListener(this)
        binding.redeemBtn.setOnClickListener(this)

        callApi()
        redeemProductUI()

    }

    private fun callApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getDreamGiftDetailData(
            DreamGiftDetailRequest(
                actionType = "243",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                dreamGiftId = lstDreamGift.dreamGiftId.toString(),
                loyaltyId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userName.toString(),
                domain = "KESHAV_CEMENT"
            )
        )
    }

    private fun removeGiftApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getDreamGiftRemoveData(
            DreamGiftRemoveRequest(
                actionType = 4,
                dreamGiftId = lstDreamGift.dreamGiftId!!.toString(),
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!,
                giftStatusId = 4
            )
        )
    }

    private fun redeemProductUI() {

        val points: Int = lstDreamGift.pointsRequired!!.toInt() /*+ lstDreamGift.tdsPoints!!.toDouble().toInt()*/
        val currentPoint: Int = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt()

//        if (currentPoint == 0){
//            binding.redeemBtn.visibility = View.GONE
//        }else{
//            if (isRedeemable == 1 ){
//                binding.redeemBtn.visibility = View.VISIBLE
//            }else{
//                binding.redeemBtn.visibility = View.GONE
//            }
//
//        }

        if (/*lstDreamGift.isRedeemable == 1*/ currentPoint >= points){
            binding.redeemBtn.isEnabled = true
            binding.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
            binding.redeemBtn.setOnClickListener(this)
        }else{
            binding.redeemBtn.isEnabled = false
            binding.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Dream Gift Details observer  ***/
        viewModel.dreamGiftDetailLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (!it.lstDreamGift.isNullOrEmpty()){

                isRedeemable = it.lstDreamGift[0].isRedeemable!!

                val currentPoint: Int = PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt()

                if (currentPoint == 0){
                    binding.redeemBtn.visibility = View.GONE
                }else{
                    if (isRedeemable == 1 ){
                        binding.redeemBtn.visibility = View.VISIBLE
                    }else{
                        binding.redeemBtn.visibility = View.GONE
                    }

                }

                binding.dreamGiftNameTv.text = it.lstDreamGift[0].dreamGiftName.toString()
                binding.createdDate.text = it.lstDreamGift[0].jCreatedDate.toString().split(" ")[0]
                binding.desiredDate.text =  it.lstDreamGift[0].jDesiredDate.toString().split(" ")[0]
                binding.pointsReqTv.text = it.lstDreamGift[0].pointsRequired.toString()
                binding.avergaePointOne.text = it.lstDreamGift[0].avgEarningPoints.toString()
                binding.avergaePointTwo.text = it.lstDreamGift[0].earlyExpectedPoints.toString()
                binding.avergaePointThree.text = it.lstDreamGift[0].lateExpectedPoints.toString()
                binding.PossibelDateOne.text = it.lstDreamGift[0].expectedDate.toString()
                binding.PossibelDateTwo.text = it.lstDreamGift[0].earlyExpectedDate.toString()
                binding.PossibelDateThree.text = it.lstDreamGift[0].lateExpectedDate.toString()

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

        /*** Dream Gift Remove observer  ***/
        viewModel.dreamGiftRemoveLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null){
                if (it.returnValue == 1){
                    Toast.makeText(requireContext(),getString(R.string.dream_gift_removed_successfully),Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }else{
                    Toast.makeText(requireContext(),getString(R.string.dream_gift_remove_failed),Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong_please_try_again_later),Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.remove_btn ->{
                removeGiftApi()
            }

            R.id.redeem_btn -> {

                if (isRedeemable == 1){
                    if (PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 6 ||
                        PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 0 ||
                        PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 2 ||
                        PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 5 ||
                        PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 3 ||
                        PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 4){

                        ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),false,"Failure!",
                            getString(R.string.not_allowed_to_redeem_contact_administrator),object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                                override fun onOk() {
                                }
                            })

                    }else{
                        val bundle = Bundle()
                        bundle.putSerializable("DreamGift",lstDreamGift)
                        findNavController().navigate(R.id.dreamGiftAddressFragment,bundle)

                    }

                }else {
                    Toast.makeText(requireContext(), getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
                }

            }

        }
    }



}