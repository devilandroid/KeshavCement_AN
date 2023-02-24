package com.loyaltyworks.keshavcement.ui.referAndEarn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentReferBinding
import com.loyaltyworks.keshavcement.model.ObjContactCenterDetails
import com.loyaltyworks.keshavcement.model.ReferRequest
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.CommonSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue


class ReferFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReferBinding
    private lateinit var viewModel: ReferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(ReferViewModel::class.java)
        binding = FragmentReferBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ReferView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ReferFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.referButton.setOnClickListener(this)
        binding.refralCodeTxt.text = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].referralCode

        //  copy codd from clipboard
        binding.copyCode.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied", binding.refralCodeTxt.text.toString())
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Refer Observer ***/
        viewModel.referLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){
                    binding.mobileNumber.setText("")
                    binding.customerName.setText("")
                    CommonSuccessDialog.showCommonSuccessDialog(requireContext(),false,"Successfully !",
                        getString(R.string.you_have_successfully_referred_to_your_friend),object :
                            CommonSuccessDialog.CommonSuccessDialogCallBack{
                            override fun onOk() {
                            }
                        })
                }else if (it.returnMessage!!.split("~")[0] == "2"){
                    Toast.makeText(requireContext(), getString(R.string.already_refered_to_this_no), Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }


        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.refer_button ->{
                if (BlockMultipleClick.click()) return

                if (binding.customerName.text.toString().trim().isNullOrEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
                }else if (binding.mobileNumber.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.enter_mobile_number), Toast.LENGTH_SHORT).show()
                } else if (binding.mobileNumber.text.toString().length != 10){
                    Toast.makeText(requireContext(), getString(R.string.enter_valid_mobile_no), Toast.LENGTH_SHORT).show()
                }else{
                    referFriendApi()
                }
            }
        }
    }



    private fun referFriendApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getReferData(
            ReferRequest(
                actionType = "2",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                objContactCenterDetails = ObjContactCenterDetails(
                    refereeMobileNo = binding.mobileNumber.text.toString(),
                    refereeName = binding.customerName.text.toString()
                )

            )
        )
    }
}