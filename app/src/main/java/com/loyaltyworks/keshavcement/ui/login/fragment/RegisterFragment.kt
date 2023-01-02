package com.loyaltyworks.keshavcement.ui.login.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentRegisterBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick


class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 0L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var OTP: String = ""

    var customerType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerType = arguments?.getSerializable("customerType") as String

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("jfkdjskf","fjkdsjfkds")
                backButtonClicked()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        if (customerType == "engineer" || customerType == "mason"){
            binding.memberIdLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.GONE
            binding.aadharLayout.visibility = View.VISIBLE

        }else if (customerType == "dealer"){
            binding.memberIdLayout.visibility = View.VISIBLE
            binding.gstLayout.visibility = View.VISIBLE
            binding.aadharLayout.visibility = View.GONE
        }else if (customerType == "subDealer"){
            binding.memberIdLayout.visibility = View.GONE
            binding.gstLayout.visibility = View.VISIBLE
            binding.aadharLayout.visibility = View.GONE
        }

        binding.otpSubmitButton.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)
        binding.backLoginBtn.setOnClickListener(this)
        binding.activateNowButton.setOnClickListener(this)

        binding.skipBtn.setOnClickListener(this)
        binding.verifyReferBtn.setOnClickListener(this)

        binding.backToLogin.setOnClickListener(this)
        binding.registerSubmitBtn.setOnClickListener(this)

    }

    private fun backButtonClicked() {
        if (binding.otpLayout.visibility == View.GONE) {
            findNavController().popBackStack()
        }else{
            binding.timer.text = ""
            binding.otpMobileNumber.isEnabled = true
            OTP = ""
            binding.otpViewActivate.setOTP("")
            binding.otpMobileNumber.setText("")
            binding.otpSubmitTxt.text = "Generate OTP"

            findNavController().popBackStack()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.otp_submit_button ->{
                if (binding.otpSubmitTxt.text.toString() == "Generate OTP") {
                    if (::timers.isInitialized){
                        timers.cancel()
                    }
                    binding.spinnerLayout.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE
                    binding.otpSubmitTxt.text = "Submit"

                }else{

                    binding.firstLayout.visibility = View.GONE

                    if (customerType == "engineer" || customerType == "mason"){
                        binding.spinnerHeader.visibility = View.GONE
                        binding.skipLayout.visibility = View.VISIBLE
                        binding.referLayout.visibility = View.VISIBLE
                    }else{
                        binding.registerFormLayout.visibility = View.VISIBLE
                    }
                }
            }

            R.id.activate_now_button ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", customerType)
                findNavController().navigate(R.id.action_registerFragment_to_activateFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.back_login_btn ->{
                backButtonClicked()
            }

            R.id.skip_btn ->{
                binding.spinnerHeader.visibility = View.VISIBLE
                binding.skipLayout.visibility = View.GONE
                binding.referLayout.visibility = View.GONE
                binding.registerFormLayout.visibility = View.VISIBLE
            }

            R.id.verify_refer_btn ->{
                binding.spinnerHeader.visibility = View.VISIBLE
                binding.skipLayout.visibility = View.GONE
                binding.referLayout.visibility = View.GONE
                binding.registerFormLayout.visibility = View.VISIBLE
            }

            R.id.back_to_login ->{
                backButtonClicked()
            }

            R.id.register_submit_btn ->{

            }
        }
    }


}