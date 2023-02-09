package com.loyaltyworks.keshavcement.ui.customerType

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.CustomerTypeRequest
import com.loyaltyworks.keshavcement.model.CustomerTypeResponse
import kotlinx.coroutines.launch

class CustomerTypeViewModel: BaseViewModel() {

    /*** Customer Type ViewModel ***/
    private val _getCustomerTypeResponse = MutableLiveData<CustomerTypeResponse>()
    val getCustomerTypeResponse: LiveData<CustomerTypeResponse> = _getCustomerTypeResponse

    fun getCustomerTypeRequest(customerTypeRequest: CustomerTypeRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getCustomerTypeResponse.postValue(apiRepository.getCustomerTypeData(customerTypeRequest))
        }
    }
}