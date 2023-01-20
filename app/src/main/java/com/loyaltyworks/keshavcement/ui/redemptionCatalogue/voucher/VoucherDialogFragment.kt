package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.voucher


import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loyaltyworks.keshavcement.R

class VoucherDialogFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voucher_dialog_list_dialog, container, false)
    }


}