package com.loyaltyworks.keshavcement.ui.mySupportExecutive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewSupportExecutiveBinding
import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RegisterSuccessDialog


class NewSupportExecutiveFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNewSupportExecutiveBinding
    private lateinit var viewModel: MySupportExecutiveViewModel
    private lateinit var loginViewModel: LoginRegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginViewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MySupportExecutiveViewModel::class.java)
        binding = FragmentNewSupportExecutiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "NewSupportExecutiveView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NewSupportExecutiveFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        binding.submitBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submit_btn ->{
                if (BlockMultipleClick.click())return
                
                if (binding.nameEdt.text.toString().isNullOrEmpty()){
                    binding.nameEdt.error = getString(R.string.enter_name)
                    binding.nameEdt.requestFocus()
                }else if (binding.mobileEdt.text.toString().isNullOrEmpty()){
                    binding.mobileEdt.error = getString(R.string.enter_mobile_number)
                    binding.mobileEdt.requestFocus()
                }else if (binding.passwordEdt.text.toString().isNullOrEmpty()){
                    binding.passwordEdt.error = getString(R.string.enter_password)
                    binding.passwordEdt.requestFocus()
                }else{
                    checkCustomerExistancy(binding.mobileEdt.text.toString())
                }


            }
        }
    }

    private fun checkCustomerExistancy(userName: String) {
        LoadingDialogue.showDialog(requireContext())
        loginViewModel.getMobileEmailExistenceCheck(
            CustomerExistenceRequest(
                actionType = "11",
//                actorId = userTypeId.toString(),
                location = (Location(
                    userName = userName,
                ))

            )
        )
    }

    private fun enrollApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getCreateSupportExecutiveData(
            CreateSupportExecutiveRequest(
                actionType = "0",
                HierarchyMapDetails(
                    customerUserID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
                ),
                ObjCustomerSupportExecutive(
                    firstName = binding.nameEdt.text.toString(),
                    customerMobile = binding.mobileEdt.text.toString(),
                    registrationSource = "3",
                    merchantId = "1",
                    isActive = "1",
                    customerTypeID = "5",
                    password = binding.passwordEdt.text.toString()
                )

            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /***  Mobile Number Existancy Check  Observer ***/
        loginViewModel.mobileNumberExists.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null) {
                    if (it != 1) {
                        enrollApi()
                    }else{
                        AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.your_entered_mobile_already_exist),object:
                            AppController.SuccessCallBack{
                            override fun onOk() {

                            }
                        })
                    }
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }
        })

        /*** Create New SE Observer ***/
        viewModel.createSupportExecutiveLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage.split("~")[0] == "1"){

                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","Created support executive",
                    object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                    override fun onOk() {
                        findNavController().popBackStack()
                    }

                })

                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }

}