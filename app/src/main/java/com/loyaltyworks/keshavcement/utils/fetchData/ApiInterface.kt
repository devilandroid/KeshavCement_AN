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

    // Profile api call
    @POST("GetCustomerDetailsMobileApp")
    fun fetchProfileAsync(@Body profileRequest: ProfileRequest): Deferred<Response<ProfileResponse>>

    // My Support Executive List api call
    @POST("GetCustParentChildMappingDetails")
    fun fetchSupportExecutiveListAsync(@Body supportExecutiveRequest: MySupportExecutiveRequest): Deferred<Response<MySupportExecutiveResponse>>

    // Create Support Executive api call
    @POST("SaveCustomerRegistrationDetailsMobileApp")
    fun fetchCreateSupportExecutiveAsync(@Body createSupportExecutiveRequest: CreateSupportExecutiveRequest): Deferred<Response<CreateSupportExecutiveResponse>>

    // Activate Customer api call
    @POST("SaveCustomerDetails")
    fun fetchActivateCustomerAsync(@Body activateCustomerRequest: ActivateCustomerRequest): Deferred<Response<ActivateCustomerResponse>>

    /* Update Profile api call*/
    @POST("UpdateCustomerProfileMobileApp")
    fun getProfileImageUpdateAsync(@Body profileImageUpdateRequest: ProfileImageUpdateRequest): Deferred<Response<ProfileImageUpdateResponse>>

    /* Product Dropdown api call*/
    @POST("GetAttributeDetails")
    fun getProductDropdownAsync(@Body productDropdownRequest: ProductDropdownRequest): Deferred<Response<ProductDropdownResponse>>

    /* Dealer SubDealer List api call*/
    @POST("GetCustParentChildMappingDetails")
    fun getDealerSubDealerListAsync(@Body dealerSubDealerListRequest: DealerSubDealerListRequest): Deferred<Response<DealerSubDealerListResponse>>

    /* Submit Purchase Request api call*/
    @POST("SavePurchaseRequest")
    fun getPurchaseSubmitAsync(@Body submitPurchaseRequest: SubmitPurchaseRequest): Deferred<Response<SubmitPurchaseResponse>>

    /* My Purchase Claim List api call*/
    @POST("BindAssessmentRequestDetails")
    fun getMyPurchaseClaimListAsync(@Body myPurchaseClaimRequest: MyPurchaseClaimRequest): Deferred<Response<MyPurchaseClaimResponse>>

    /* Worksite List api call*/
    @POST("GetWorkSiteDetails")
    fun getWorksiteListAsync(@Body worksiteListRequest: WorksiteListRequest): Deferred<Response<WorksiteListResponse>>

    /* Save Worksite api call*/
    @POST("SaveWorkSiteInfo")
    fun getSaveWorksiteAsync(@Body saveWorksiteRequest: SaveWorksiteRequest): Deferred<Response<SaveWorksiteResponse>>

    /* Activate/Deactivate Support Executive api call*/
    @POST("ApproveRejectDeleteRequest")
    fun getActivateDeactivateSupportExecutiveAsync(@Body activateDeactivateExecutiveRequest: ActivateDeactivateExecutiveRequest): Deferred<Response<ActivateDeactivateExecutiveResponse>>

    //Query listing api call
    @POST("SaveCustomerQueryTicket")
    fun qetQueryListingQueryResponse(@Body queryListingRequest: QueryListingRequest): Deferred<Response<QueryListingResponse>>

    //QueryChat api call
    @POST("GetQueryResponseInformation")
    fun getQueryChatElementResponse(@Body queryChatElementRequest: QueryChatElementRequest?): Deferred<Response<QueryChatElementResponse>>

    //PostReplyQueryChatStatus api call
    @POST("SaveCustomerQueryTicket")
    fun getPostReplyHelpTopicStatus(@Body queryChatElementRequest: PostChatStatusRequest?): Deferred<Response<PostChatStatusResponse>>

    //Help Topic listing api call
    @POST("GetHelpTopics")
    fun getHelpTopicList(@Body helpTopicRequest: HelpTopicRequest): Deferred<Response<HelpTopicResponse>>

    //save new ticket api call
    @POST("SaveCustomerQueryTicket")
    fun getSaveNewTicketQueryResponse(@Body saveNewTicketQueryRequest: SaveNewTicketQueryRequest?): Deferred<Response<SaveNewTicketQueryResponse>>

    /*MyRedemption Histrory Lisitng */
    @POST("GetCatalogueDetails")
    fun getMyRedemptionListing(@Body getRedemptionRequest: MyRedemptionRequest?):  Deferred<Response<MyRedemptionResponse>>

    // Promotion List api call
    @POST("GetPromotionDetailsMobileApp")
    fun fetchPromotionListAsync(@Body promotionListRequest: PromotionListRequest): Deferred<Response<PromotionListResponse>>

    // Promotion Details api call
    @POST("GetCustomerPromotionDetailsByPromotionID")
    fun fetchPromotionDetailsAsync(@Body promotionDetailsRequest: PromotionDetailsRequest): Deferred<Response<PromotionDetailsResponse>>

    /*History Notification List by Id api call */
    @POST("GetPushHistoryDetails")
    fun getHistoryNotifiation(@Body historyNotificationRequest: HistoryNotificationRequest?): Deferred<Response<HistoryNotificationResponse>>

    /*History Notification Details by Id api call */
    @POST("GetPushHistoryDetails")
    fun getHistoryNotifiationDetailByID(@Body historyNotificationDetailByIDRequest: HistoryNotificationDetailsRequest?): Deferred<Response<HistoryNotificationResponse>>

    /*History Notification Delete by Id api call */
    @POST("GetPushHistoryDetails")
    fun getHistoryNotifiationDeleteByID(@Body historyNotificationDetailByIDRequest: HistoryNotificationDetailsRequest?):  Deferred<Response<HistoryNotificationDeleteResponse>>

    /* Pending Claim List api call */
    @POST("GetPurchaseRequestDetailsList")
    fun getPendingClaimListAsync(@Body pendingClaimListRequest: PendingClaimListRequest):  Deferred<Response<PendingClaimListResponse>>

    /* Customer List api call */
    @POST("GetCustParentChildMappingDetails")
    fun getCustomerListAsync(@Body customerListingRequest: CustomerListingRequest):  Deferred<Response<CustomerListingResponse>>

    /* Enrollment api call */
    @POST("SaveCustomerRegistrationDetailsMobileApp")
    fun getEnrollmentAsync(@Body enrollmentRequest: EnrollmentRequest):  Deferred<Response<EnrollmentResponse>>

}