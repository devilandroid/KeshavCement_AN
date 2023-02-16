package com.loyaltyworks.keshavcement.ui.purchaseRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class PurchaseRequestViewModel: BaseViewModel() {

    /*** Product Dropdown viewModel ***/
    private val _productDropdownLiveData = MutableLiveData<ProductDropdownResponse>()
    val productDropdownLiveData: LiveData<ProductDropdownResponse> = _productDropdownLiveData

    fun getProductDropdownData(productDropdownRequest: ProductDropdownRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _productDropdownLiveData.postValue(apiRepository.getProductDropdownData(productDropdownRequest))
        }
    }

    /*** Dealer SubDealer List viewModel ***/
    private val _dealerSubDealerListLiveData = MutableLiveData<DealerSubDealerListResponse>()
    val dealerSubDealerListLiveData: LiveData<DealerSubDealerListResponse> = _dealerSubDealerListLiveData

    fun getDealerSubDealerListData(dealerSubDealerListRequest: DealerSubDealerListRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dealerSubDealerListLiveData.postValue(apiRepository.getDealerSubDealerListData(dealerSubDealerListRequest))
        }
    }

    /*** Submit Purchase Request viewModel ***/
    private val _purchaseSubmitLiveData = MutableLiveData<SubmitPurchaseResponse>()
    val purchaseSubmitLiveData: LiveData<SubmitPurchaseResponse> = _purchaseSubmitLiveData

    fun getPurchaseSubmitData(submitPurchaseRequest: SubmitPurchaseRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _purchaseSubmitLiveData.postValue(apiRepository.getPurchaseSubmitData(submitPurchaseRequest))
        }
    }

}