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
    @POST("CustomerAccountDelete")
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

    /*  User Active Or Not api call*/
    @POST("UserActiveOrInActive")
    fun getUserActivityOrNot(@Body userActiveOrNotRequest: UserActiveOrNotRequest): Deferred<Response<UserActiveOrNotResponse>>

    /*  Save Catalogue Redemption details api call*/
    @POST("SaveCatalogueRedemptionDetails")
    fun getSaveCatalogueRedemptionDetails(@Body saveCatalogueRedemptionRequest: SaveCatalogueRedemptionRequest): Deferred<Response<SaveCatalogueRedemptionResponse>>

    /* Send Catalogue Redemption Mobile Alert  */
    @POST("SendCatalogueRedemptionAlertMobileApp")
    fun getSendCatalogueRedeemAlert(@Body saveCatalogueRedemptionRequest: SaveCatalogueRedemptionRequest): Deferred<Response<SaveCatalogueRedemptionResponse>>

    /* Send success sms to user */
    @POST("SendSMSForSuccessfulRedemptionMobileApp")
    fun getSendSuccssSMStoUser(@Body saveCatalogueRedemptionRequest: SendSMSForSucessRedemReq): Deferred<Response<Boolean>>

    // Catalogue api call
    @POST("GetCatalogueDetails")
    fun fetchCatalogueAsync(@Body profileRequest: GatCatalogueRequest): Deferred<Response<GetCatalogueResponse>>

    // Catalogue Category api call
    @POST("GetCatalogueCategoryDetails")
    fun fetchCatalogueCategoryAsync(@Body catalogueCategoryRequest: CatalogueCategoryRequest): Deferred<Response<CatalogueCategoryResponse>>

    // Cart api call
    @POST("GetCatalogueCartDetailsMobile")
    fun fetchCartAsync(@Body cartResponse: CartRequest): Deferred<Response<CartResponse>>

    // Add to Cart api call
    @POST("SaveCatalogueCartDetailsMobile")
    fun fetchAddToCartAsync(@Body cartResponse: AddToCartRequest): Deferred<Response<AddToCartResponse>>

    /* Cart Count call*/
    @POST("GetCatalogueCartDetailsMobile")
    fun getCartCountData(@Body cartCountRequest: CartCountRequest): Deferred<Response<CartCountResponse>>

    /* Planner  Add call*/
    @POST("GetCatalogueDetails")
    fun getAddPlannerData(@Body plannerAddRequest: PlannerAddRequest): Deferred<Response<PlannerAddResponse>>

    /* Cart Count Update api call */
    @POST("UpdateCustomerCart")
    fun getCartUpdate(@Body cartListingRequest: UpdateQuantityRequest?): Deferred<Response<UpdateQuantityResponse>>

    /* Cart Item Remove api call*/
    @POST("UpdateCustomerCart")
    fun getCartItemRemove(@Body removeCartProductRequest: RemoveCartProductRequest): Deferred<Response<RemoveCartProductResponse>>

    // Redemption Planner List api call
    @POST("GetRedemptionPlannerDetails")
    fun fetchPlannerAsync(@Body plannerRequest: PlannerRequest): Deferred<Response<PlannerResponse>>

    /* Planner Remove api call*/
    @POST("GetPlannerAddedOrNot")
    fun getPlannerRemove(@Body removePlannerRequest: RemovePlannerRequest): Deferred<Response<RemovePlannerResponse>>

    // My Earning List api call
    @POST("GetRewardTransactionDetailsMobileApp")
    fun fetchEarningListAsync(@Body myEarningRequest: MyEarningRequest): Deferred<Response<MyEarningResponse>>

    // Dream Gift List api call
    @POST("GetDreamGiftDetails")
    fun fetchDreamGiftAsync(@Body dreamGiftRequest: DreamGiftRequest): Deferred<Response<DreamGiftResponse>>

    // Dream Gift Details api call
    @POST("GetDreamGiftDetails")
    fun fetchDreamGiftDetailAsync(@Body dreamGiftDetailRequest: DreamGiftDetailRequest): Deferred<Response<DreamGiftDetailResponse>>

    /* Dream Gift Remove api call*/
    @POST("SaveOrUpdateDreamGiftDetails")
    fun getDreamGiftRemove(@Body dreamGiftRemoveRequest: DreamGiftRemoveRequest): Deferred<Response<DreamGiftRemoveResponse>>

    /* Voucher api call*/
    @POST("GetCatalogueDetails")
    fun getRedeemGiftAsync(@Body redeemGiftRequest: RedeemGiftRequest?): Deferred<Response<RedeemGiftResponse>>

    /*RedeemGiftVoucher*/
    @POST("SaveCatalogueRedemptionDetails")
    fun getRedeemGiftVoucherAsync(@Body redeemGiftVoucherRequest: RedeemGiftVoucherRequest?): Deferred<Response<RedeemGiftVoucherResponse>>

    /* Level List api call */
    @POST("GetAttributeDetails")
    fun getLevelListAsync(@Body levelListRequest: LevelListRequest): Deferred<Response<LevelListResponse>>

    // Refer Friend api call
    @POST("SaveReferralDetails")
    fun fetchReferAsync(@Body referRequest: ReferRequest): Deferred<Response<ReferResponse>>

    /* Pending Claim Approve/Reject api call */
    @POST("SaveApprovePurchaseRequestDetail")
    fun getPendingClaimApproveRejactAsync(@Body pendingClaimApproveRejectRequest: PendingClaimApproveRejectRequest):  Deferred<Response<PendingClaimApproveRejectResponse>>


    /* Terms Condition Api call */
    @POST("SaveOrGetTermAndConditions")
    fun fetchTermsConditionDataAsync(@Body termsConditionRequest: TermsConditionRequest):  Deferred<Response<TermsConditionResponse>>

    // city List api call
    @POST("GetCityDetailsMobileApp")
    fun fetchCityListAsync(@Body cityListRequest: CityListRequest): Deferred<Response<CityListResponse>>

    /*MyRedemption Details Api call */
    @POST("GetCatalogueDetails")
    fun getMyRedemptionDetailsAsync(@Body myRedemptionDetailsRequest: MyRedemptionDetailsRequest?):  Deferred<Response<MyRedemptionDetailsResponse>>

    /*MyRedemption Status Api call */
    @POST("GetCustomerRedemptionHistorydetails")
    fun fetchRedemptionStatusListAsync(@Body redemptionHistoryRequest: RedemptionHistoryRequest?):  Deferred<Response<RedemptionHistoryResponse>>

    /* Redemption Status call*/
    @POST("GetAttributeDetails")
    fun getStatusSpinnerData(@Body statusSpinnerRequest: StatusSpinnerRequest): Deferred<Response<StatusSpinnerResponse>>

    /* CashBackPoints call*/
    @POST("GetCashTransferDenominations")
    fun getCashBackPointsAsyncData(@Body cashbackPointsListRequest: CashbackPointsListRequest): Deferred<Response<CashbackPointsListResponse>>

    /* Cash Transfer Submit call*/
    @POST("SaveCustomerCasTransferDEtails")
    fun getCashTransferAsyncData(@Body cashTransferSubmitRequest: CashTransferSubmitRequest): Deferred<Response<CashTransferSubmitResponse>>

    /* Cash Transfer Approval List call*/
    @POST("GetCustomerCashTransferList")
    fun getCashTransferApprovalListAsyncData(@Body cashTransferApprovalListRequest: CashTransferApprovalListRequest): Deferred<Response<CashTransferApprovalListResponse>>

     /* Cash Transfer Approve/Reject call*/
    @POST("CustomerCashTransferApprovalOrRejection")
    fun getCashTransferApproveRejectAsyncData(@Body cashTransferApproveRejectRequest: CashTransferApproveRejectRequest): Deferred<Response<CashTransferApproveRejectResponse>>

    /* Cash Transfer Approve/Reject call*/
    @POST("GetCustomerCashTransferList")
    fun getCashTransferHistoryListAsyncData(@Body cashTransferHistoryRequest: CashTransferHistoryRequest): Deferred<Response<CashTransferHistoryResponse>>

 /* Product View List call*/
    @POST("GetProductDetails")
    fun getProductViewListAsyncData(@Body productViewListRequest: ProductViewListRequest): Deferred<Response<ProductViewListResponse>>


}