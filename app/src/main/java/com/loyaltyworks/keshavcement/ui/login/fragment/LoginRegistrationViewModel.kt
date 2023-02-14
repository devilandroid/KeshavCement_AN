package com.loyaltyworks.keshavcement.ui.login.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class LoginRegistrationViewModel: BaseViewModel() {

    /*** Customer Verify View Model  ***/
    private val _emailMobileExists = MutableLiveData<Int>()
    val mobileNumberExists: LiveData<Int> = _emailMobileExists

    fun getMobileEmailExistenceCheck(customerExistenceRequest: CustomerExistenceRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _emailMobileExists.postValue(apiRepository.getMobileExist(customerExistenceRequest))
        }
    }

    /***  Save and Get OTP   ***/

    private val _saveAndGetOTPDetailsResponse = MutableLiveData<SaveAndGetOTPDetailsResponse>()
    val saveAndGetOTPDetailsResponse: LiveData<SaveAndGetOTPDetailsResponse> = _saveAndGetOTPDetailsResponse

    fun setOTPRequest(saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _saveAndGetOTPDetailsResponse.postValue(apiRepository.getOTPDetail(saveAndGetOTPDetailsRequest))
        }
    }

    /*** Referal Code Existancy ViewModel ***/
    private val _getReferalCodeValidResponse = MutableLiveData<Boolean>()
    val getReferalCodeValidResponse: LiveData<Boolean> = _getReferalCodeValidResponse

    fun getReferalCodeValidRequest(referalCodeExistancyRequest: ReferalCodeExistancyRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getReferalCodeValidResponse.postValue(apiRepository.getReferalCodeValidData(referalCodeExistancyRequest))
        }
    }


    /*** Register ViewModel ***/
    private val _registerCustomerResponse = MutableLiveData<RegisterResponse>()
    val registerCustomerResponse: LiveData<RegisterResponse> = _registerCustomerResponse

    fun getRegisterRequest(registerRequest: RegisterRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _registerCustomerResponse.postValue(apiRepository.getRegisterRequest(registerRequest))
        }
    }

    /*** Login ViewModel **/
    private val _loginDetails = MutableLiveData<LoginResponse>()
    val loginDetails: LiveData<LoginResponse> = _loginDetails

    fun getLoginData(loginRequest: LoginRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _loginDetails.postValue(apiRepository.getLoginData(loginRequest))
        }
    }

    /*** GetProfleDetails  ***/
    private val _getProfileResponse = MutableLiveData<ProfileResponse>()
    val getProfileResponse: LiveData<ProfileResponse> = _getProfileResponse

    fun setProfileRequest(profileReqeust: ProfileRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _getProfileResponse.postValue(apiRepository.getProfileData(profileReqeust))
        }
    }

    /*** Activate Customer ViewModel  ***/
    private val _activateCustomerLiveData = MutableLiveData<ActivateCustomerResponse>()
    val activateCustomerLiveData: LiveData<ActivateCustomerResponse> = _activateCustomerLiveData

    fun activateCustomerData(activateCustomerRequest: ActivateCustomerRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _activateCustomerLiveData.postValue(apiRepository.activateCustomerData(activateCustomerRequest))
        }
    }
}