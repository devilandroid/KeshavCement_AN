package com.loyaltyworks.keshavcement.ui.myRedemption

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class MyRedemptionViewModel: BaseViewModel() {

   /* My Redemption List View Model */
    private val _myRedemptionLiveData = MutableLiveData<MyRedemptionResponse>()
    val myRedemptionLiveData: LiveData<MyRedemptionResponse> = _myRedemptionLiveData

    fun setMyRedemptionListingRequest(myRedemptionRequest: MyRedemptionRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myRedemptionLiveData.postValue(apiRepository.getRedemptionListRequest(myRedemptionRequest))
        }
    }

    /* My Redemption Details View Model */
    private val _myRedemptionDetailsLiveData = MutableLiveData<MyRedemptionDetailsResponse>()
    val myRedemptionDetailsLiveData: LiveData<MyRedemptionDetailsResponse> = _myRedemptionDetailsLiveData

    fun getMyRedemptionDetailData(myRedemptionDetailsRequest: MyRedemptionDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myRedemptionDetailsLiveData.postValue(apiRepository.getRedemptionDetailData(myRedemptionDetailsRequest))
        }
    }

    /* My Redemption history View Model */
    private val _myRedemptionStatusDetailsLiveData = MutableLiveData<RedemptionHistoryResponse>()
    val myRedemptionStatusDetailsLiveData: LiveData<RedemptionHistoryResponse> = _myRedemptionStatusDetailsLiveData

    fun getMyRedemptionStatusDetailData(redemptionHistoryRequest: RedemptionHistoryRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myRedemptionStatusDetailsLiveData.postValue(apiRepository.getRedemptionStatusDetailData(redemptionHistoryRequest))
        }
    }

    /*** Status List View Model ***/
    private val _statusSpinnerLiveData = MutableLiveData<StatusSpinnerResponse>()
    val statusSpinnerLiveData: LiveData<StatusSpinnerResponse> = _statusSpinnerLiveData

    fun getStatusSpinnerData(statusSpinnerRequest: StatusSpinnerRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _statusSpinnerLiveData.postValue(apiRepository.getStatusSpinnerData(statusSpinnerRequest))
        }
    }

}