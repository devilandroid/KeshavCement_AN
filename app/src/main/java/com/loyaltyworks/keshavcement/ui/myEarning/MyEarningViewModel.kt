package com.loyaltyworks.keshavcement.ui.myEarning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.MyEarningRequest
import com.loyaltyworks.keshavcement.model.MyEarningResponse
import com.loyaltyworks.keshavcement.model.StatusListRequest
import com.loyaltyworks.keshavcement.model.StatusListResponse
import com.vmb.fileSelect.FileSelector.scope
import kotlinx.coroutines.launch


class MyEarningViewModel: BaseViewModel() {

    /* My Earning List View Model */
    private val _myEarningListLiveData = MutableLiveData<MyEarningResponse>()
    val myEarningListLiveData: LiveData<MyEarningResponse> = _myEarningListLiveData

    fun getMyEarningListData(myEarningRequest: MyEarningRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myEarningListLiveData.postValue(apiRepository.getEarningListData(myEarningRequest))
        }
    }


    /* My Status List View Model */
   /* private val _statusListLiveData = MutableLiveData<StatusListResponse>()
    val statusListLiveData: LiveData<StatusListResponse> = _statusListLiveData

    fun getStatusListData(statusListRequest: StatusListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _statusListLiveData.postValue(apiRepository.getStatusListData(statusListRequest))
        }
    }*/

}