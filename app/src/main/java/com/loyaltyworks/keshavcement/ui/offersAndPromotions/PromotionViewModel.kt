package com.loyaltyworks.keshavcement.ui.offersAndPromotions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.PromotionDetailsRequest
import com.loyaltyworks.keshavcement.model.PromotionDetailsResponse
import com.loyaltyworks.keshavcement.model.PromotionListRequest
import com.loyaltyworks.keshavcement.model.PromotionListResponse
import kotlinx.coroutines.launch

class PromotionViewModel: BaseViewModel() {

    /* Promotion List View Model */
    private val _promotionListLiveData = MutableLiveData<PromotionListResponse>()
    val promotionListLiveData: LiveData<PromotionListResponse> = _promotionListLiveData

    fun getPromotionListData(promotionListRequest: PromotionListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _promotionListLiveData.postValue(apiRepository.getPromotionListData(promotionListRequest))
        }
    }

    /* My Redemption Details View Model */
    private val _promotionDetailsLiveData = MutableLiveData<PromotionDetailsResponse>()
    val promotionDetailsLiveData: LiveData<PromotionDetailsResponse> = _promotionDetailsLiveData

    fun getPromotionDetailData(promotionDetailsRequest: PromotionDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _promotionDetailsLiveData.postValue(apiRepository.getPromotionDetailData(promotionDetailsRequest))
        }
    }


}