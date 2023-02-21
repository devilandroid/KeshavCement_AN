package com.loyaltyworks.keshavcement.ui.referAndEarn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.ReferRequest
import com.loyaltyworks.keshavcement.model.ReferResponse
import kotlinx.coroutines.launch

class ReferViewModel: BaseViewModel() {

    /* Refer View Model  */
    private val _referLiveData = MutableLiveData<ReferResponse>()
    val referLiveData: LiveData<ReferResponse> = _referLiveData

    fun getReferData(referRequest: ReferRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _referLiveData.postValue(apiRepository.getReferData(referRequest))
        }
    }
}