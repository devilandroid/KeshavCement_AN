package com.loyaltyworks.keshavcement.ui.cashTransferApproval

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.CashTransferApproveRejectRequest
import com.loyaltyworks.keshavcement.model.CashTransferApproveRejectResponse
import com.loyaltyworks.keshavcement.model.MyRedemptionRequest
import com.loyaltyworks.keshavcement.model.MyRedemptionResponse
import kotlinx.coroutines.launch

class CashTransferApprovalViewModel: BaseViewModel() {

    /* Cash Transfer Approve/Reject View Model */
    private val _cashTransferApproveRejectLiveData = MutableLiveData<CashTransferApproveRejectResponse>()
    val cashTransferApproveRejectLiveData: LiveData<CashTransferApproveRejectResponse> = _cashTransferApproveRejectLiveData

    fun getCashTransferApproveReject(cashTransferApproveRejectRequest: CashTransferApproveRejectRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cashTransferApproveRejectLiveData.postValue(apiRepository.getCashTransferApproveReject(cashTransferApproveRejectRequest))
        }
    }

}