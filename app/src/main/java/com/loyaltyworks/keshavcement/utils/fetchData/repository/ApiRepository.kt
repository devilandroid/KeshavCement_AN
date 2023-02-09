package com.loyaltyworks.keshavcement.utils.fetchData.repository

import com.loyaltyworks.keshavcement.model.*
import com.loyaltyworks.keshavcement.utils.fetchData.ApiInterface
import com.loyaltyworks.walkaroo.utils.fetchData.repository.BaseRepository

class ApiRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    /* Customer Type List callback*/
    suspend fun getCustomerTypeData(customerTypeRequest: CustomerTypeRequest): CustomerTypeResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchCustomerTypeAsync(customerTypeRequest).await()
            },
            error = "Error Customer Type Trigger"
        )
    }

    // customer exist check api call For Login
    suspend fun getMobileExist(customerExistenceRequest: CustomerExistenceRequest): Int? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getMobileExistAsync(customerExistenceRequest).await() },
            error = "Error mobile check at Login"
            //convert to mutable list
        )
    }

    /*OTP Call back for Activate Retail_Distributor*/
    suspend fun getOTPDetail(saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest): SaveAndGetOTPDetailsResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getSaveAndGetOTPDetails(saveAndGetOTPDetailsRequest).await() },
            error = "Error saveAndGetOTPDetailsRequest check"
            //convert to mutable list
        )
    }

    /* Referal Code Valid callback */
    suspend fun getReferalCodeValidData(referalCodeExistancyRequest: ReferalCodeExistancyRequest): Boolean? {
        return safeApiCall(
            call = {
                apiInterface.getReferalCodeValidAsync(referalCodeExistancyRequest).await()
            },
            error = "Error Referal Code Valid Trigger"
        )
    }

    /* State List callback*/
    suspend fun getStateListData(stateListRequest: StateListRequest): StateListResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchStateListAsync(stateListRequest).await()
            },
            error = "Error State List Trigger"
        )
    }

    /* District List callback*/
    suspend fun getDistrictListData(districtListRequest: DistrictListRequest): DistrictListResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchDistrictListAsync(districtListRequest).await()
            },
            error = "Error District List Trigger"
        )
    }

    /* Taluk List callback*/
    suspend fun getTalukListData(talukListRequest: TalukListRequest): TalukListResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchTalukListAsync(talukListRequest).await()
            },
            error = "Error Taluk List Trigger"
        )
    }

    /* Register Customer callback*/
    suspend fun getRegisterRequest(registerRequest: RegisterRequest): RegisterResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchRegisterCustomerAsync(registerRequest).await()
            },
            error = "Error Register Customer Trigger"
        )
    }

    // Login Check api call
    suspend fun getLoginData(loginRequest: LoginRequest): LoginResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchLoginDataAsync(loginRequest).await() },
            error = "Error fetching Login Details"
            //convert to mutable list
        )
    }

    // Change Password api call
    suspend fun getChangePasswordData(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchChangePasswordAsync(changePasswordRequest).await() },
            error = "Error change password"
            //convert to mutable list
        )
    }

    // Dashboard api call
    suspend fun getDashboardData(dashboardRequest: UpdatedDashboardSingleApiRequest): UpdatedDashboardSingleApiResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashboardAsync(dashboardRequest).await() },
            error = "Error fetching Dashboard Details"
            //convert to mutable list
        )
    }

    // get banner Image
    suspend fun setBannerImageRequest(bannerImageRequest: BannerImageRequest): BannerImageResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.setBannerImageRequestAsync(bannerImageRequest).await() },
            error = "Error getting banner image"
            //convert to mutable list
        )
    }

}