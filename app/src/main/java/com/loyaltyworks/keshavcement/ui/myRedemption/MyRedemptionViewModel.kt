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


}