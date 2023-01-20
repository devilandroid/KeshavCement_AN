package com.loyaltyworks.keshavcement.ui.mySupportExecutive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentMySupportExecutiveBinding
import com.loyaltyworks.keshavcement.ui.mySupportExecutive.adapter.MySupportExecutiveAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class MySupportExecutiveFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMySupportExecutiveBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMySupportExecutiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mySupportExecutiveRecycler.adapter = MySupportExecutiveAdapter()

        binding.createNew.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.create_new ->{
            if (BlockMultipleClick.click())return
            findNavController().navigate(R.id.action_mySupportExecutiveFragment_to_newSupportExecutiveFragment)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        }
    }
}