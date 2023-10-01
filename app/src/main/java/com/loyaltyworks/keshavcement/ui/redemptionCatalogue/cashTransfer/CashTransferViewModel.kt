package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class CashTransferViewModel: BaseViewModel() {

    /*** CashBackPoints List viewModel ***/
    private val _cashbackPointsListLiveData = MutableLiveData<CashbackPointsListResponse>()
    val cashbackPointsListLiveData: LiveData<CashbackPointsListResponse> = _cashbackPointsListLiveData

    fun getCashbackPointsListData(cashbackPointsListRequest: CashbackPointsListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cashbackPointsListLiveData.postValue(apiRepository.getCashbackPointsListData(cashbackPointsListRequest))
        }
    }


    /*** Cash Transfer Submit viewModel ***/
    private val _cashTransferSubmitLiveData = MutableLiveData<CashTransferSubmitResponse>()
    val cashTransferSubmitLiveData: LiveData<CashTransferSubmitResponse> = _cashTransferSubmitLiveData

    fun getCashTransferSubmitData(cashTransferSubmitRequest: CashTransferSubmitRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cashTransferSubmitLiveData.postValue(apiRepository.getCashTransferSubmitData(cashTransferSubmitRequest))
        }
    }


}