package com.loyaltyworks.keshavcement.ui.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.QueryListingRequest
import com.loyaltyworks.keshavcement.model.QueryListingResponse
import kotlinx.coroutines.launch

class SupportViewModel: BaseViewModel() {

    /*Query Listing */
    private val _queryListLiveData = MutableLiveData<QueryListingResponse>()
    val queryListLiveData : LiveData<QueryListingResponse> = _queryListLiveData

    fun getQueryListLiveData(queryList: QueryListingRequest){
        scope.launch { _queryListLiveData.postValue(apiRepository.getQueryListData(queryList)) }
    }
}