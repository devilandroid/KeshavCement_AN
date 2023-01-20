package com.loyaltyworks.keshavcement.ui.mySupportExecutive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewSupportExecutiveBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog


class NewSupportExecutiveFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNewSupportExecutiveBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewSupportExecutiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submit_btn ->{
                if (BlockMultipleClick.click())return

                ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","Created support executive",
                object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                    override fun onOk() {
                        findNavController().popBackStack()
                    }

                })
            }
        }
    }

}