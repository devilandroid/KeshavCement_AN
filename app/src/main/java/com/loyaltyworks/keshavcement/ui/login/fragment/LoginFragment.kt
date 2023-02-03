package com.loyaltyworks.keshavcement.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentLoginBinding
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    var customerType: String = ""

    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 0L
    var time_in_milli_seconds = 0L

    var token: String? = null
    var pwd = "123456"
    var OTP: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "LoginView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        customerType = arguments?.getSerializable("customerType") as String

        if (customerType == "supportExecutive"){
            binding.activateLayout.visibility = View.GONE
            binding.forgotPassword.visibility = View.GONE
            binding.registerLayout.visibility = View.GONE
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("jfkdjskf","fjkdsjfkds")
                backButtonClicked()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        binding.loginButton.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
        binding.activateNowButton.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)
        binding.forgotSubmitOtp.setOnClickListener(this)
        binding.resendOtp.setOnClickListener(this)
        binding.forgotBackButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

    }

    private fun backButtonClicked() {
        if (binding.forgotLayout.visibility == View.VISIBLE){
            if (binding.otpLayout.visibility == View.GONE) {
                binding.forgotHeader.visibility = View.GONE
                binding.forgotLayout.visibility = View.GONE

                binding.loginLayout.visibility = View.VISIBLE
                binding.loginHeader.visibility = View.VISIBLE
                binding.activateLayout.visibility = View.VISIBLE
            }else{
                timers.cancel()
                binding.timer.text = ""
                binding.forgotUserName.isEnabled = true
                OTP = ""

                binding.otpView.setOTP("")
                binding.forgotUserName.setText("")
                binding.forgotSubmitTxt.text = "Generate OTP"

                binding.otpLayout.visibility = View.GONE
                binding.forgotHeader.visibility = View.GONE
                binding.forgotLayout.visibility = View.GONE

                binding.loginLayout.visibility = View.VISIBLE
                binding.loginHeader.visibility = View.VISIBLE
                binding.activateLayout.visibility = View.VISIBLE
            }

        }else{
            findNavController().popBackStack()
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.forgot_password ->{
                if (BlockMultipleClick.click())return
                binding.loginLayout.visibility = View.GONE
                binding.loginHeader.visibility = View.GONE
                binding.activateLayout.visibility = View.GONE

                binding.forgotHeader.visibility = View.VISIBLE
                binding.forgotLayout.visibility = View.VISIBLE
            }

            R.id.forgot_back_button ->{
                if (BlockMultipleClick.click())return
                backButtonClicked()
            }

            R.id.forgot_submit_otp ->{
                if (binding.forgotUserName.text.toString().isNullOrEmpty()) {
                    binding.forgotUserName.requestFocus()
                    binding.forgotUserName.error = getString(R.string.enter_mobile_number)
                    return
                }

                if (binding.forgotUserName.text.toString().length != 10) {
                    binding.forgotUserName.text.clear()
                    binding.forgotUserName.requestFocus()
                    binding.forgotUserName.error = getString(R.string.enter_valid_mobile_no)
                    return
                }

                if (binding.forgotSubmitTxt.text.toString() == "Generate OTP") {
                    if (::timers.isInitialized){
                        timers.cancel()
                    }
                }else {

                }

            }

            R.id.activate_now_button ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", customerType)
                findNavController().navigate(R.id.action_loginFragment_to_activateFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.register_btn ->{
                if (BlockMultipleClick.click())return
                val bundle = Bundle()
                bundle.putSerializable("customerType", customerType)
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment,bundle)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.login_button ->{
                if (BlockMultipleClick.click())return
                if (customerType == "engineer"){
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,"1")
                }else if (customerType == "mason"){
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,"2")
                }else if (customerType == "dealer"){
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,"3")
                }else if (customerType == "subDealer"){
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,"4")
                }else if (customerType == "supportExecutive"){
                    PreferenceHelper.setStringValue(requireContext(), BuildConfig.CustomerType,"5")
                }

                PreferenceHelper.setBooleanValue(requireContext(), BuildConfig.IsLoggedIn, true)

                val intent = Intent(requireContext(), DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }
    }


}