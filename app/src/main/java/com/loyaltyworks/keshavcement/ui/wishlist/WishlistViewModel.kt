package com.loyaltyworks.keshavcement.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.PlannerRequest
import com.loyaltyworks.keshavcement.model.PlannerResponse
import com.loyaltyworks.keshavcement.model.RemovePlannerRequest
import com.loyaltyworks.keshavcement.model.RemovePlannerResponse
import kotlinx.coroutines.launch


class WishlistViewModel: BaseViewModel() {

    /*  Redemption Planner  View Model */
    private val _plannerLiveData = MutableLiveData<PlannerResponse>()
    val plannerLiveData: LiveData<PlannerResponse> = _plannerLiveData

    fun getPlannerData(plannerRequest: PlannerRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _plannerLiveData.postValue(apiRepository.getPlannerData(plannerRequest))
        }
    }

    /*   Planner Remove View Model */
    private val _plannerRemoveLiveData = MutableLiveData<RemovePlannerResponse>()
    val plannerRemoveLiveData: LiveData<RemovePlannerResponse> = _plannerRemoveLiveData

    fun getPlannerRemoveData(removePlannerRequest: RemovePlannerRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _plannerRemoveLiveData.postValue(apiRepository.getPlannerRemove(removePlannerRequest))
        }
    }

}