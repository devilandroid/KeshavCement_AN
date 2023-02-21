package com.loyaltyworks.keshavcement.ui.pendingClaimRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.PendingClaimApproveRejectRequest
import com.loyaltyworks.keshavcement.model.PendingClaimApproveRejectResponse
import com.loyaltyworks.keshavcement.model.PendingClaimListRequest
import com.loyaltyworks.keshavcement.model.PendingClaimListResponse
import kotlinx.coroutines.launch

class PendingClaimViewModel: BaseViewModel() {

    /*** Pending Claim List viewModel ***/
    private val _pendingClaimListLiveData = MutableLiveData<PendingClaimListResponse>()
    val pendingClaimListLiveData: LiveData<PendingClaimListResponse> = _pendingClaimListLiveData

    fun getPendingClaimListData(pendingClaimListRequest: PendingClaimListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _pendingClaimListLiveData.postValue(apiRepository.getPendingClaimListData(pendingClaimListRequest))
        }
    }

    /*** Pending Claim Approve/Reject viewModel ***/
    private val _pendingClaimApproveRejectLiveData = MutableLiveData<PendingClaimApproveRejectResponse>()
    val pendingClaimApproveRejectLiveData: LiveData<PendingClaimApproveRejectResponse> = _pendingClaimApproveRejectLiveData

    fun getApproveRejectPendingClaimData(pendingClaimApproveRejectRequest: PendingClaimApproveRejectRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _pendingClaimApproveRejectLiveData.postValue(apiRepository.getApproveRejectPendingClaimData(pendingClaimApproveRejectRequest))
        }
    }

}