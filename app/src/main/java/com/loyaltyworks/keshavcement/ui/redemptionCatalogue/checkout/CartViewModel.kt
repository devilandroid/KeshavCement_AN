package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch


class CartViewModel: BaseViewModel() {

    /*** Cart List View Model ***/
    private val _cartListLiveData = MutableLiveData<CartResponse>()
    val cartListLiveData: LiveData<CartResponse> = _cartListLiveData

    fun getCartListData(cartRequest: CartRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cartListLiveData.postValue(apiRepository.getCartData(cartRequest))
        }
    }

    /*** Cart Count Update View Model ***/
    private val _getCartUpdateLiveData = MutableLiveData<UpdateQuantityResponse>()
    val getCartUpdateLiveData: LiveData<UpdateQuantityResponse> = _getCartUpdateLiveData

    fun getCartUpdateListing(cartUpdateRequest: UpdateQuantityRequest) {
        scope.launch {

            //post the value inside live data
            _getCartUpdateLiveData.postValue(apiRepository.getCartUpdate(cartUpdateRequest))

        }
    }

    /*** Cart Item Remove View Model ***/
    private val _getCartRemoveLiveData = MutableLiveData<RemoveCartProductResponse>()
    val getCartRemoveLiveData: LiveData<RemoveCartProductResponse> = _getCartRemoveLiveData

    fun getCartRemoveItem(removeCartProductRequest: RemoveCartProductRequest) {
        scope.launch {

            //post the value inside live data
            _getCartRemoveLiveData.postValue(apiRepository.getCartItemRemove(removeCartProductRequest))

        }
    }


}