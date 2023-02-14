package com.loyaltyworks.keshavcement.ui.mySupportExecutive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class MySupportExecutiveViewModel: BaseViewModel() {


    /*** My Support Executive List viewModel ***/
    private val _supportExecutiveListLiveData = MutableLiveData<MySupportExecutiveResponse>()
    val supportExecutiveListLiveData: LiveData<MySupportExecutiveResponse> = _supportExecutiveListLiveData

    fun getSupportExecutiveListData(mySupportExecutiveRequest: MySupportExecutiveRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _supportExecutiveListLiveData.postValue(apiRepository.getSupportExecutiveListData(mySupportExecutiveRequest))
        }
    }


    /*** Create Support Executive viewModel ***/
    private val _createSupportExecutiveLiveData = MutableLiveData<CreateSupportExecutiveResponse>()
    val createSupportExecutiveLiveData: LiveData<CreateSupportExecutiveResponse> = _createSupportExecutiveLiveData

    fun getCreateSupportExecutiveData(createSupportExecutiveRequest: CreateSupportExecutiveRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _createSupportExecutiveLiveData.postValue(apiRepository.getCreateSupportExecutiveData(createSupportExecutiveRequest))
        }
    }

    /*** Activate/Deactivate Support Executive viewModel ***/
    private val _activateDeactivateSupportExecutiveLiveData = MutableLiveData<ActivateDeactivateExecutiveResponse>()
    val activateDeactivateSupportExecutiveLiveData: LiveData<ActivateDeactivateExecutiveResponse> = _activateDeactivateSupportExecutiveLiveData

    fun getActivateDeactivateSupportExecutiveData(activateDeactivateExecutiveRequest: ActivateDeactivateExecutiveRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _activateDeactivateSupportExecutiveLiveData.postValue(apiRepository.getActivateDeactivateSupportExecutiveData(activateDeactivateExecutiveRequest))
        }
    }
}