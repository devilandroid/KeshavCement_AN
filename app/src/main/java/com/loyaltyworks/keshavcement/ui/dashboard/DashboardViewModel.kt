package com.loyaltyworks.keshavcement.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class DashboardViewModel: BaseViewModel() {

    /*** Change Password ViewModel **/
    private val _changePasswordLiveData = MutableLiveData<ChangePasswordResponse>()
    val changePasswordLiveData: LiveData<ChangePasswordResponse> = _changePasswordLiveData

    fun getChangePasswordData(changePasswordRequest: ChangePasswordRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _changePasswordLiveData.postValue(apiRepository.getChangePasswordData(changePasswordRequest))
        }
    }

    /*** Dashboard viewModel ***/
    private val _dashboardLiveData = MutableLiveData<UpdatedDashboardSingleApiResponse>()
    val dashboardLiveData: LiveData<UpdatedDashboardSingleApiResponse> = _dashboardLiveData

    fun getDashboardData(dashboardRequest: UpdatedDashboardSingleApiRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _dashboardLiveData.postValue(apiRepository.getDashboardData(dashboardRequest))
        }
    }

    /*** Get Banner Image ***/
    private val _bannerImageLiveData = MutableLiveData<BannerImageResponse>()
    val bannerImageLiveData: LiveData<BannerImageResponse> = _bannerImageLiveData

    fun getBannerImage(bannerImageRequest: BannerImageRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _bannerImageLiveData.postValue(apiRepository.setBannerImageRequest(bannerImageRequest))
        }
    }

    /*** Product View List viewModel ***/
    private val _prodViewListLiveData = MutableLiveData<ProductViewListResponse>()
    val prodViewListLiveData: LiveData<ProductViewListResponse> = _prodViewListLiveData

    fun getProductViewData(productViewListRequest: ProductViewListRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _prodViewListLiveData.postValue(apiRepository.getProductViewData(productViewListRequest))
        }
    }

}