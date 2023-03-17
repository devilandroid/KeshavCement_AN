package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentCashTransferDetailsBinding
import com.loyaltyworks.keshavcement.model.LstCustomerJson
import com.loyaltyworks.keshavcement.model.ObjCataloguee
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import kotlinx.android.synthetic.main.appbar_main.*
import java.io.Serializable


class CashTransferDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCashTransferDetailsBinding

    private lateinit var objCataloguee: ObjCataloguee
    lateinit var  _lstCustomerJson: List<LstCustomerJson>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCashTransferDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "AD_CUS_CashVoucherDetailsView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AD_CUS_CashVoucherDetailsFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (arguments != null) {
            objCataloguee = arguments?.getSerializable("CashVoucherData") as ObjCataloguee
            _lstCustomerJson = arguments?.getSerializable("CustomerAddress") as List<LstCustomerJson>
        }

//        if (PreferenceHelper.getStringValue(requireContext(), BuildConfig.CustomerType) == BuildConfig.Dealer){
//            requireActivity().toolbar.title = getString(R.string.cash_voucher)
//        }else{
//            requireActivity().toolbar.title = getString(R.string.cash_transfer)
//        }

        binding.redeemBtn.setOnClickListener(this)

        SetUpUi()
    }


    private fun SetUpUi() {

        if (objCataloguee.isRedeemable == 1){
            binding.redeemBtn.visibility = View.VISIBLE
            if (objCataloguee.pointsRequired!!.toInt() <=  PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt()) {
                binding.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
            }else{
                binding.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
            }

        }else{
            binding.redeemBtn.visibility = View.GONE
        }


        Glide.with(this).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + objCataloguee.productImage)
            .into(binding.productImage)

        binding.catetogryType.text = objCataloguee.catogoryName + " / " + objCataloguee.productName
        binding.productName.text = objCataloguee.productName
        binding.pointsRequired.text = objCataloguee.pointsRequired.toString()
        binding.descriptionTxt.text = objCataloguee.productDesc
        binding.termTxt.text = objCataloguee.termsCondition
        binding.value.text = "₹ " + objCataloguee.mrp


    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.redeem_btn ->{
                if(BlockMultipleClick.click()) return

                if (objCataloguee.isRedeemable == 1){
                    if (objCataloguee.pointsRequired!!.toInt() <=  PreferenceHelper.getDashboardDetails(requireContext())?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt()) {
                        if (PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 1){

                            if (!_lstCustomerJson.isEmpty()){
                                val bundle = Bundle()
                                bundle.putSerializable("CashVoucherData", objCataloguee)
                                bundle.putSerializable("CustomerAddress", _lstCustomerJson as Serializable)
                                findNavController().navigate(R.id.cashTransferDialogFragment, bundle)

                            }else{
                                Toast.makeText(requireContext(), getString(R.string.address_not_found), Toast.LENGTH_SHORT).show()
                            }

                        }else{
                            AppController.showSuccessPopUpDialog(requireContext(),getString(R.string.not_allowed_to_redeem_contact_administrator), object :
                                AppController.SuccessCallBack{
                                override fun onOk() {}
                            })
                        }
                    }else{
                        Toast.makeText(requireContext(), getString(R.string.insufficient_point_balance_to_redeem), Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(requireContext(), getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
                }


            }
        }
    }

}