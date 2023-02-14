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

    // Profile api call
    suspend fun getProfileData(profileRequest: ProfileRequest): ProfileResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchProfileAsync(profileRequest).await() },
            error = "Error fetching Profile Details"
            //convert to mutable list
        )
    }

    // My Support Executive List api call
    suspend fun getSupportExecutiveListData(mySupportExecutiveRequest: MySupportExecutiveRequest): MySupportExecutiveResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchSupportExecutiveListAsync(mySupportExecutiveRequest).await() },
            error = "Error My Support Executive List"
            //convert to mutable list
        )
    }

    // Create Support Executive  api call
    suspend fun getCreateSupportExecutiveData(createSupportExecutiveRequest: CreateSupportExecutiveRequest): CreateSupportExecutiveResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchCreateSupportExecutiveAsync(createSupportExecutiveRequest).await() },
            error = "Error Create Support Executive "
            //convert to mutable list
        )
    }

    // Activate Customer  api call
    suspend fun activateCustomerData(activateCustomerRequest: ActivateCustomerRequest): ActivateCustomerResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchActivateCustomerAsync(activateCustomerRequest).await() },
            error = "Error Activate Customer "
            //convert to mutable list
        )
    }

    /*  Update Profile Image callback*/
    suspend fun getProfileImageUpdateAccount(profileImageUpdateRequest: ProfileImageUpdateRequest): ProfileImageUpdateResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getProfileImageUpdateAsync(profileImageUpdateRequest).await() },
            error = "Error Update Profile Image Trigger"
            //convert to mutable list
        )
    }

    /*  Product Dropdown callback*/
    suspend fun getProductDropdownData(productDropdownRequest: ProductDropdownRequest): ProductDropdownResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getProductDropdownAsync(productDropdownRequest).await() },
            error = "Error Product Dropdown Trigger"
            //convert to mutable list
        )
    }

    /*  Dealer SubDealer List callback*/
    suspend fun getDealerSubDealerListData(dealerSubDealerListRequest: DealerSubDealerListRequest): DealerSubDealerListResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getDealerSubDealerListAsync(dealerSubDealerListRequest).await() },
            error = "Error Dealer SubDealer List Trigger"
            //convert to mutable list
        )
    }

    /*  Submit Purchase Request callback*/
    suspend fun getPurchaseSubmitData(submitPurchaseRequest: SubmitPurchaseRequest): SubmitPurchaseResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getPurchaseSubmitAsync(submitPurchaseRequest).await() },
            error = "Error Submit Purchase Request Trigger"
            //convert to mutable list
        )
    }

    /*  My Purchase Claim List callback*/
    suspend fun getMyPurchaseClaimListData(myPurchaseClaimRequest: MyPurchaseClaimRequest): MyPurchaseClaimResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getMyPurchaseClaimListAsync(myPurchaseClaimRequest).await() },
            error = "Error My Purchase Claim List Trigger"
            //convert to mutable list
        )
    }

    /*  Worksite List callback*/
    suspend fun getWorksiteListData(worksiteListRequest: WorksiteListRequest): WorksiteListResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getWorksiteListAsync(worksiteListRequest).await() },
            error = "Error Worksite List Trigger"
            //convert to mutable list
        )
    }

    /*  Worksite List callback*/
    suspend fun getSaveWorksiteData(saveWorksiteRequest: SaveWorksiteRequest): SaveWorksiteResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getSaveWorksiteAsync(saveWorksiteRequest).await() },
            error = "Error Save Worksite Trigger"
            //convert to mutable list
        )
    }

    /*  Activate/Deactivate Support Executive  callback*/
    suspend fun getActivateDeactivateSupportExecutiveData(activateDeactivateExecutiveRequest: ActivateDeactivateExecutiveRequest): ActivateDeactivateExecutiveResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getActivateDeactivateSupportExecutiveAsync(activateDeactivateExecutiveRequest).await() },
            error = "Error Activate/Deactivate Support Executive Trigger"
            //convert to mutable list
        )
    }

    //QueryList for support api call
    suspend fun getQueryListData(queryListingRequest: QueryListingRequest): QueryListingResponse? {

        return safeApiCall(
            call = { apiInterface.qetQueryListingQueryResponse(queryListingRequest).await() },
            error = "Error Query List trigger"
        )

    }

    /*QueryChat for specific queryID*/
    suspend fun getChatQuery(chatQuery: QueryChatElementRequest): QueryChatElementResponse? {
        return safeApiCall(
            call = { apiInterface.getQueryChatElementResponse(chatQuery).await()},
            error = "Error Query Chat trigger"
        )
    }

    /*post chat*/
    suspend fun getPostReply(postChatStatusRequest: PostChatStatusRequest): PostChatStatusResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPostReplyHelpTopicStatus(postChatStatusRequest).await()
            },
            error = "Error PostReplyHelpTopicStatus Trigger"
        )
    }

    /*Help topic listing*/
    suspend fun getHelpTopic(helpTopicRequest: HelpTopicRequest): HelpTopicResponse? {
        return safeApiCall(
            call = { apiInterface.getHelpTopicList(helpTopicRequest).await() },
            error = "Error help topic list trigger"
        )
    }

    /*save new ticket*/
    suspend fun getSaveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest): SaveNewTicketQueryResponse? {
        return safeApiCall(
            call = {
                apiInterface.getSaveNewTicketQueryResponse(saveNewTicketQueryRequest).await()
            },
            error = "Error  Promotion Detail Trigger"
        )
    }

    /*My Redemption Request callback*/
    suspend fun getRedemptionListRequest(myRedemptionRequest: MyRedemptionRequest): MyRedemptionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyRedemptionListing(myRedemptionRequest).await()
            },
            error = "Error MyRedemptionListing Trigger"
        )
    }

    /* Promotion List Request callback*/
    suspend fun getPromotionListData(promotionListRequest: PromotionListRequest): PromotionListResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchPromotionListAsync(promotionListRequest).await()
            },
            error = "Error Promotion List Trigger"
        )
    }

    /*Promotion Details Request callback*/
    suspend fun getPromotionDetailData(promotionDetailsRequest: PromotionDetailsRequest): PromotionDetailsResponse? {
        return safeApiCall(
            call = {
                apiInterface.fetchPromotionDetailsAsync(promotionDetailsRequest).await()
            },
            error = "Error Promotion Details Trigger"
        )
    }

    /* Notification  callback*/
    suspend fun getHistoryNotificationList(historyNotificationRequest: HistoryNotificationRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiation(historyNotificationRequest).await()
            },
            error = "Error History Notification Trigger"
        )
    }

    /* Notification details  callback*/
    suspend fun getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDetailByID(historyNotificationDetailsRequest).await()
            },
            error = "Error History Notification Detail By ID Trigger"
        )
    }

    /* Notification delete  callback*/
    suspend fun getHistoryNotificationDeleteByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationDeleteResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDeleteByID(historyNotificationDetailsRequest).await()
            },
            error = "Error History Notification Delete By ID Trigger"
        )
    }

    /* Pending Claim List  callback*/
    suspend fun getPendingClaimListData(pendingClaimListRequest: PendingClaimListRequest): PendingClaimListResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPendingClaimListAsync(pendingClaimListRequest).await()
            },
            error = "Error Pending Claim List Trigger"
        )
    }

    /* Customer List  callback*/
    suspend fun getCustomerListData(customerListingRequest: CustomerListingRequest): CustomerListingResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerListAsync(customerListingRequest).await()
            },
            error = "Error Customer List Trigger"
        )
    }

    /* Enrollment  callback*/
    suspend fun getEnrollmentData(enrollmentRequest: EnrollmentRequest): EnrollmentResponse? {
        return safeApiCall(
            call = {
                apiInterface.getEnrollmentAsync(enrollmentRequest).await()
            },
            error = "Error Enrollment Trigger"
        )
    }
}