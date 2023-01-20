package com.loyaltyworks.keshavcement.ui.enrollment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentEnrollmentBinding
import com.loyaltyworks.keshavcement.ui.enrollment.adapter.EnrollmentAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick


class EnrollmentFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentEnrollmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEnrollmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enrollmentRecycler.adapter = EnrollmentAdapter()

        binding.addCustomer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_customer ->{
                if (BlockMultipleClick.click())return
                findNavController().navigate(R.id.action_enrollmentFragment_to_newEnrollmentFragment)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

        }
    }


}