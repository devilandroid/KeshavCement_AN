package com.loyaltyworks.keshavcement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class CommonViewModel: BaseViewModel() {

    /*** State List View Model  ***/
    private val _stateLiveData = MutableLiveData<StateListResponse>()
    val stateLiveData: LiveData<StateListResponse> = _stateLiveData

    fun getStateData(stateListRequest: StateListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _stateLiveData.postValue(apiRepository.getStateListData(stateListRequest))
        }
    }


    /*** District List View Model  ***/
    private val _districtLiveData = MutableLiveData<DistrictListResponse>()
    val districtLiveData: LiveData<DistrictListResponse> = _districtLiveData

    fun getDistrictData(districtListRequest: DistrictListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _districtLiveData.postValue(apiRepository.getDistrictListData(districtListRequest))
        }
    }

    /*** Taluk List View Model  ***/
    private val _talukLiveData = MutableLiveData<TalukListResponse>()
    val talukLiveData: LiveData<TalukListResponse> = _talukLiveData

    fun getTalukData(talukListRequest: TalukListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _talukLiveData.postValue(apiRepository.getTalukListData(talukListRequest))
        }
    }

}