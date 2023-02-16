package com.loyaltyworks.keshavcement.ui.worksiteDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.SaveWorksiteRequest
import com.loyaltyworks.keshavcement.model.SaveWorksiteResponse
import com.loyaltyworks.keshavcement.model.WorksiteListRequest
import com.loyaltyworks.keshavcement.model.WorksiteListResponse
import kotlinx.coroutines.launch

class WorksiteViewModel: BaseViewModel() {

    /*** Worksite List viewModel ***/
    private val _worksiteListLiveData = MutableLiveData<WorksiteListResponse>()
    val worksiteListLiveData: LiveData<WorksiteListResponse> = _worksiteListLiveData

    fun getWorksiteListData(worksiteListRequest: WorksiteListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _worksiteListLiveData.postValue(apiRepository.getWorksiteListData(worksiteListRequest))
        }
    }

    /*** Save Worksite List viewModel ***/
    private val _saveWorksiteLiveData = MutableLiveData<SaveWorksiteResponse>()
    val saveWorksiteLiveData: LiveData<SaveWorksiteResponse> = _saveWorksiteLiveData

    fun getSaveWorksiteData(saveWorksiteRequest: SaveWorksiteRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _saveWorksiteLiveData.postValue(apiRepository.getSaveWorksiteData(saveWorksiteRequest))
        }
    }
}