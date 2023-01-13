package com.loyaltyworks.keshavcement.ui.newSale

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewSaleBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog


class NewSaleFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNewSaleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewSaleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendOtpBtn.setOnClickListener(this)
        binding.saveProceedBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send_otp_btn ->{
                if (BlockMultipleClick.click())return

                if (binding.sendOtpBtn.text == "Send OTP"){
                    binding.saveProceedBtn.visibility = View.GONE
                    binding.otpLayout.visibility = View.VISIBLE
                    binding.sendOtpBtn.text = "Submit"
                }else{
                    Toast.makeText(requireContext(), "code not implemented", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.save_proceed_btn ->{
                if (BlockMultipleClick.click())return
                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully !","Saved. Check pending claims.",object :ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                    override fun onOk() {
                        findNavController().popBackStack()

                    }

                })
            }
        }
    }

}