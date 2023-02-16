package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class ProductCatalogueViewModel: BaseViewModel() {

    /*** User Activie Or Not View Model ***/
    private val _userActivieOrNotLiveData = MutableLiveData<UserActiveOrNotResponse>()
    val userActiveOrNotData: LiveData<UserActiveOrNotResponse> = _userActivieOrNotLiveData

    fun getUserActiveOrNotData(getUserActivieOrNot: UserActiveOrNotRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _userActivieOrNotLiveData.postValue(apiRepository.getUserActivieOrNot(getUserActivieOrNot))
        }
    }


    /*** Save Catalogue Redemption Request View Model ***/
    private val _saveCatalogueRedeemLiveData = MutableLiveData<SaveCatalogueRedemptionResponse>()
    val saveCatalogueRedeemData: LiveData<SaveCatalogueRedemptionResponse> = _saveCatalogueRedeemLiveData

    fun getSaveCatalogueRedeem(saveCatalogueRedeem: SaveCatalogueRedemptionRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _saveCatalogueRedeemLiveData.postValue(apiRepository.getSaveCatalogueRedemption(saveCatalogueRedeem))
        }
    }


    /***   Send Catalogue Redemption Mobile Alert  sendSuccessSMSToMerchant ***/
    private val _sendCatalogueRedeemAlertLiveData = MutableLiveData<SaveCatalogueRedemptionResponse>()
    val sendCatalogueRedeemAlert: LiveData<SaveCatalogueRedemptionResponse> = _sendCatalogueRedeemAlertLiveData

    fun getSendCatalogueRedeemAlert(saveCatalogueRedeem: SaveCatalogueRedemptionRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _sendCatalogueRedeemAlertLiveData.postValue(apiRepository.getCatalogueMobileAlert(saveCatalogueRedeem))
        }
    }


    /***   Send success sms to user ***/

    private val _sendSuccessSMSLiveData = MutableLiveData<Boolean>()
    val sendSuccessSMSAlert: LiveData<Boolean> = _sendSuccessSMSLiveData

    fun getSendSucessSMS(saveCatalogueRedeem: SendSMSForSucessRedemReq) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _sendSuccessSMSLiveData.postValue(apiRepository.getSendSuccessSMS(saveCatalogueRedeem))
        }
    }


    /*** Catalogue List View Model ***/
    private val _catalogueLiveData = MutableLiveData<GetCatalogueResponse>()
    val catalogueLiveData: LiveData<GetCatalogueResponse> = _catalogueLiveData

    fun getCatalogueData(getCatalogueRequest: GatCatalogueRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _catalogueLiveData.postValue(apiRepository.getCatalogueData(getCatalogueRequest))
        }
    }

    /*** Catalogue Category List View Model ***/
    private val _catalogueCategoryLiveData = MutableLiveData<CatalogueCategoryResponse>()
    val catalogueCategoryLiveData: LiveData<CatalogueCategoryResponse> = _catalogueCategoryLiveData

    fun getCatalogueCategoryData(catalogueCategoryReqest: CatalogueCategoryRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _catalogueCategoryLiveData.postValue(apiRepository.getCatalogueCategoryData(catalogueCategoryReqest))
        }
    }



    /***  Cart Listing View Model ***/
    private val _cartListingLiveData = MutableLiveData<CartResponse>()
    val cartListingLiveData: LiveData<CartResponse> = _cartListingLiveData

    fun getCartData(cartRequest: CartRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cartListingLiveData.postValue(apiRepository.getCartData(cartRequest))
        }
    }

    /*** Add to Cart View Model ***/
    private val _addToCartListingLiveData = MutableLiveData<AddToCartResponse>()
    val addToCartListingLiveData: LiveData<AddToCartResponse> = _addToCartListingLiveData

    fun getAddToCartData(addToCartRequest: AddToCartRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _addToCartListingLiveData.postValue(apiRepository.getAddToCartData(addToCartRequest))
        }
    }

    /*** Add Planner View Model ***/
    private val _addPlannerLiveData = MutableLiveData<PlannerAddResponse>()
    val addPlannerLiveData: LiveData<PlannerAddResponse> = _addPlannerLiveData

    fun getPlannerAddData(plannerAddRequest: PlannerAddRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _addPlannerLiveData.postValue(apiRepository.getAddPlannerData(plannerAddRequest))
        }
    }

    /*** Cart Count View Model ***/
    private val _cartCountLiveData = MutableLiveData<CartCountResponse>()
    val cartCountLiveData: LiveData<CartCountResponse> = _cartCountLiveData

    fun getCartCountData(cartCountRequest: CartCountRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _cartCountLiveData.postValue(apiRepository.getCartCountData(cartCountRequest))
        }
    }

}