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
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentReferBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick


class ReferFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReferBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        //  copy codd from clipboard
        binding.copyCode.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied", binding.refralCodeTxt.text.toString())
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()
        }
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
        Toast.makeText(requireContext(), "Api not working", Toast.LENGTH_SHORT).show()
    }

}