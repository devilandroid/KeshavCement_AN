package com.loyaltyworks.keshavcement.ui.dreamGift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class DreamGiftViewModel: BaseViewModel() {

    /* Dream Gift List View Model */
    private val _dreamGiftLiveData = MutableLiveData<DreamGiftResponse>()
    val dreamGiftLiveData: LiveData<DreamGiftResponse> = _dreamGiftLiveData

    fun getDreamGiftData(dreamGiftRequest: DreamGiftRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dreamGiftLiveData.postValue(apiRepository.getDreamGiftData(dreamGiftRequest))
        }
    }

    /* Dream Gift Details View Model */
    private val _dreamGiftDetailLiveData = MutableLiveData<DreamGiftDetailResponse>()
    val dreamGiftDetailLiveData: LiveData<DreamGiftDetailResponse> = _dreamGiftDetailLiveData

    fun getDreamGiftDetailData(dreamGiftDetailRequest: DreamGiftDetailRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dreamGiftDetailLiveData.postValue(apiRepository.getDreamGiftDetailsData(dreamGiftDetailRequest))
        }
    }

    /* Dream Gift Remove View Model */
    private val _dreamGiftRemoveLiveData = MutableLiveData<DreamGiftRemoveResponse>()
    val dreamGiftRemoveLiveData: LiveData<DreamGiftRemoveResponse> = _dreamGiftRemoveLiveData

    fun getDreamGiftRemoveData(dreamGiftRemoveRequest: DreamGiftRemoveRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dreamGiftRemoveLiveData.postValue(apiRepository.getDreamGiftRemove(dreamGiftRemoveRequest))
        }
    }




}