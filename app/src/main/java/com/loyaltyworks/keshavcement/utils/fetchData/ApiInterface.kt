package com.loyaltyworks.keshavcement.utils.fetchData

import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    // Customer Type api call
    @POST("GetAttributeDetails")
    fun fetchCustomerTypeAsync(@Body customerTypeRequest: CustomerTypeRequest): Deferred<Response<CustomerTypeResponse>>

    // mobile check api call
    @POST("CheckCustomerExistancyAndVerification")
    fun getMobileExistAsync(@Body customerExistenceRequest: CustomerExistenceRequest): Deferred<Response<Int>>

    // get OTP api call fpr login
    @POST("SaveAndGetOTPDetails")
    fun getSaveAndGetOTPDetails(@Body saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest): Deferred<Response<SaveAndGetOTPDetailsResponse>>

    // Referal Code Valid api call
    @POST("CheckEmailMobileExistsMobileApp")
    fun getReferalCodeValidAsync(@Body referalCodeExistancyRequest: ReferalCodeExistancyRequest): Deferred<Response<Boolean>>

    // state List api call
    @POST("GetStateDetailsMobileApp")
    fun fetchStateListAsync(@Body stateListRequest: StateListRequest): Deferred<Response<StateListResponse>>

    // District List api call
    @POST("GetDistrictDetails")
    fun fetchDistrictListAsync(@Body districtListRequest: DistrictListRequest): Deferred<Response<DistrictListResponse>>

    // Taluk List api call
    @POST("GetTalukDetails")
    fun fetchTalukListAsync(@Body talukListRequest: TalukListRequest): Deferred<Response<TalukListResponse>>

    // Register Customer api call
    @POST("SaveCustomerRegistrationDetailsMobileApp")
    fun fetchRegisterCustomerAsync(@Body registerRequest: RegisterRequest): Deferred<Response<RegisterResponse>>

    // Login Check api call
    @POST("CheckIsAuthenticatedMobileApp")
    fun fetchLoginDataAsync(@Body loginRequest: LoginRequest): Deferred<Response<LoginResponse>>

    // Change Password api call
    @POST("CheckIsAuthenticatedMobileApp")
    fun fetchChangePasswordAsync(@Body changePasswordRequest: ChangePasswordRequest): Deferred<Response<ChangePasswordResponse>>

    // Dashboard api call
    @POST("GetDashBoardDetailsApi")
    fun fetchDashboardAsync(@Body dashboardRequest: UpdatedDashboardSingleApiRequest): Deferred<Response<UpdatedDashboardSingleApiResponse>>

    // Banner Image api call
    @POST("BindLandingImageList")
    fun setBannerImageRequestAsync(@Body bannerImageRequest: BannerImageRequest): Deferred<Response<BannerImageResponse>>

}