package com.loyaltyworks.keshavcement.ui.cashTransferHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.CashTransferHistoryRequest
import com.loyaltyworks.keshavcement.model.CashTransferHistoryResponse
import com.loyaltyworks.keshavcement.model.CashbackPointsListRequest
import kotlinx.coroutines.launch

class CashTransferHistoryViewModel:BaseViewModel() {

    /*** Cash Transfer History List viewModel ***/
    private val _cashbackTransferHistoryListLiveData = MutableLiveData<CashTransferHistoryResponse>()
    val cashbackTransferHistoryListLiveData: LiveData<CashTransferHistoryResponse> = _cashbackTransferHistoryListLiveData

    fun getCashTransferHistoryListData(cashTransferHistoryRequest: CashTransferHistoryRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cashbackTransferHistoryListLiveData.postValue(apiRepository.getCashTransferHistoryListData(cashTransferHistoryRequest))
        }
    }

}