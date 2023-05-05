package com.loyaltyworks.keshavcement.ui.cashTransferApproval

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.CashTransferApprovalListRequest
import com.loyaltyworks.keshavcement.model.CashTransferApprovalListResponse
import com.loyaltyworks.keshavcement.model.CashTransferApproveRejectRequest
import com.loyaltyworks.keshavcement.model.CashTransferApproveRejectResponse
import kotlinx.coroutines.launch

class CashTransferApprovalViewModel: BaseViewModel() {

    /* Cash Transfer Approval List View Model */
    private val _cashTransferApprovalListLiveData = MutableLiveData<CashTransferApprovalListResponse>()
    val cashTransferApprovalListLiveData: LiveData<CashTransferApprovalListResponse> = _cashTransferApprovalListLiveData

    fun getCashTransferApprovalListData(cashTransferApprovalListRequest: CashTransferApprovalListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cashTransferApprovalListLiveData.postValue(apiRepository.getCashTransferApprovalListData(cashTransferApprovalListRequest))
        }
    }

     /*** Cash Transfer Approve/Reject View Model ***/
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