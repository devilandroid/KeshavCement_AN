package com.loyaltyworks.keshavcement.ui.enrollment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class EnrollmentViewModel: BaseViewModel() {

    /*** Customer List viewModel ***/
    private val _customerListLiveData = MutableLiveData<CustomerListingResponse>()
    val customerListLiveData: LiveData<CustomerListingResponse> = _customerListLiveData

    fun getCustomerListData(customerListingRequest: CustomerListingRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _customerListLiveData.postValue(apiRepository.getCustomerListData(customerListingRequest))
        }
    }

    /*** Enrollment viewModel ***/
    private val _enrollmentLiveData = MutableLiveData<EnrollmentResponse>()
    val enrollmentLiveData: LiveData<EnrollmentResponse> = _enrollmentLiveData

    fun getEnrollmentData(enrollmentRequest: EnrollmentRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _enrollmentLiveData.postValue(apiRepository.getEnrollmentData(enrollmentRequest))
        }
    }

    /*** Mapping submission viewModel ***/
    private val _mappingLiveData = MutableLiveData<MappingResponse>()
    val mappingLiveData: LiveData<MappingResponse> = _mappingLiveData

    fun getMappingData(mappingRequest: MappingRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _mappingLiveData.postValue(apiRepository.getMappingSubmissionData(mappingRequest))
        }
    }
}