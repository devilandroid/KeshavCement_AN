package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.voucher

import androidx.lifecycle.*
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.RedeemGiftRequest
import com.loyaltyworks.keshavcement.model.RedeemGiftResponse
import com.loyaltyworks.keshavcement.model.RedeemGiftVoucherRequest
import com.loyaltyworks.keshavcement.model.RedeemGiftVoucherResponse
import kotlinx.coroutines.launch

class EVoucherViewModel : BaseViewModel() {


    /*Product Category Listing */
/*
    private val _productCategoryResponse = MutableLiveData<ProductCategoryResponse>()
    val productCategoryResponse: LiveData<ProductCategoryResponse> = _productCategoryResponse

    fun setProductCategoryRequest(productCategoryRequest: ProductCategoryRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _productCategoryResponse.postValue(apiRepository.getProductCategory(
                productCategoryRequest))
        }
    }*/

    /*Product Catalogue and Voucher Listing */
    private val _redeemGiftLiveData = MutableLiveData<RedeemGiftResponse>()
    val redeemGiftLiveData: LiveData<RedeemGiftResponse> = _redeemGiftLiveData

    fun setRedeemGiftRequest(redeemGiftRequest: RedeemGiftRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftLiveData.postValue(apiRepository.getRedeemGiftData(redeemGiftRequest))
        }
    }


    /*Product Catalogue and Voucher Listing */
    private val _redeemGiftVoucherResponse = MutableLiveData<RedeemGiftVoucherResponse>()
    val redeemGiftVoucherResponse: LiveData<RedeemGiftVoucherResponse> = _redeemGiftVoucherResponse


    fun setRedeemGiftVoucherRequest(redeemGiftVoucherRequest: RedeemGiftVoucherRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftVoucherResponse.postValue(apiRepository.getRedeemGiftVoucherRequest(
                redeemGiftVoucherRequest))
        }
    }


    /*Redeem Catalogue Product Request*/

   /* private val _redeemGiftCatalogueResponse = MutableLiveData<RedeemGiftCatalogueResponse>()
    val redeemGiftCatalogueResponse: LiveData<RedeemGiftCatalogueResponse> =
        _redeemGiftCatalogueResponse

    fun redeemCatalogueRequest(redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _redeemGiftCatalogueResponse.postValue(apiRepository.getRedeemGiftCatalogueRequest(
                redeemGiftCatalogueRequest))
        }
    }
*/

}