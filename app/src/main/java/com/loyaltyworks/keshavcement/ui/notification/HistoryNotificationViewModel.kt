package com.loyaltyworks.keshavcement.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.HistoryNotificationDeleteResponse
import com.loyaltyworks.keshavcement.model.HistoryNotificationDetailsRequest
import com.loyaltyworks.keshavcement.model.HistoryNotificationRequest
import com.loyaltyworks.keshavcement.model.HistoryNotificationResponse
import kotlinx.coroutines.launch


class HistoryNotificationViewModel: BaseViewModel() {
    /*TransactionHistory Listing */
    private val _historyNotificationtLiveData = MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtLiveData

    fun getNotificationHistoryResponse(historyNotificationRequest: HistoryNotificationRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtLiveData.postValue(apiRepository.getHistoryNotificationList(historyNotificationRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDetailByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDetailByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDetailByIDLiveData

    fun getHistoryNotificationDetailById(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtLiveData.postValue( apiRepository.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDeleteByIDLiveData = MutableLiveData<HistoryNotificationDeleteResponse>()
    val historyNotificationtDeleteByIDLiveData : LiveData<HistoryNotificationDeleteResponse> = _historyNotificationtDeleteByIDLiveData

    fun getDeleteHistoryNotificationResponse(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //post the value inside live data
            _historyNotificationtDeleteByIDLiveData.postValue(apiRepository.getHistoryNotificationDeleteByIdList(historyNotificationDetailsRequest))
        }
    }
}