package com.loyaltyworks.keshavcement.ui.myPurchaseClaim

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.MyPurchaseClaimRequest
import com.loyaltyworks.keshavcement.model.MyPurchaseClaimResponse
import kotlinx.coroutines.launch

class MyPurchaseClaimViewModel: BaseViewModel() {

    /*** My Purchase Claim List viewModel ***/
    private val _myPurchaseClaimListLiveData = MutableLiveData<MyPurchaseClaimResponse>()
    val myPurchaseClaimListLiveData: LiveData<MyPurchaseClaimResponse> = _myPurchaseClaimListLiveData

    fun getMyPurchaseClaimListData(myPurchaseClaimRequest: MyPurchaseClaimRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _myPurchaseClaimListLiveData.postValue(apiRepository.getMyPurchaseClaimListData(myPurchaseClaimRequest))
        }
    }
}