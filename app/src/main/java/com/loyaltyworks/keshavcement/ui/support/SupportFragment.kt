package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentSupportBinding
import com.loyaltyworks.keshavcement.ui.support.adapter.SupportAdapter
import com.loyaltyworks.keshavcement.utils.dialog.HelpTopicsDialog


class SupportFragment : Fragment(), SupportAdapter.OnClickCallBack, View.OnClickListener,
    HelpTopicsDialog.OnItemClickCallback {


    private lateinit var binding: FragmentSupportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSupportBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.queryListingRV.adapter = SupportAdapter(this)

        binding.newTicketLl.setOnClickListener(this)


    }

    override fun onQueryListItemClickResponse(itemView: View, position: Int,/*productList: List<ObjCustomerAllQueryJson?>?*/) {
//        val bundle = Bundle()
//        bundle.putSerializable("SUPPORT_DATA", productList?.get(position))
//        bundle.putInt("ActionId", actionType)
        itemView.findNavController().navigate(R.id.action_supportFragment_to_queryChatFragment, /*bundle*/)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.new_ticket_ll -> {
                 HelpTopicsDialog.showDialog(requireContext(),this)
            }
        }
    }

    override fun onHelpTopicsItemClicked() {
        findNavController().navigate(R.id.newTicketFragment)
    }


}