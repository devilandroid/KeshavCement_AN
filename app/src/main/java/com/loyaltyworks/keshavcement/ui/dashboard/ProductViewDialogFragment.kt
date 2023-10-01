package com.loyaltyworks.keshavcement.ui.dashboard

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductViewDialogBinding
import com.loyaltyworks.keshavcement.model.ProductViewListRequest
import com.loyaltyworks.keshavcement.ui.dashboard.adapter.ProductViewAdapter
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.dialog.RedeemOTPDialog


class ProductViewDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentProductViewDialogBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentProductViewDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okBtn.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        callApi()

    }

    private fun callApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getProductViewData(
            ProductViewListRequest(
                actionType = 4,
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(100)

        /*** Product View List Observer ***/
        viewModel.prodViewListLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstProductListDetails.isNullOrEmpty()){
                binding.productViewRecycler.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE

                binding.productViewRecycler.adapter = ProductViewAdapter(it.lstProductListDetails)

            }else{
                binding.productViewRecycler.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
            }
        })

    }


    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ok_btn ->{
                findNavController().popBackStack()
            }

        }
    }

}