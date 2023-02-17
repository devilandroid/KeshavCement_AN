package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*  User Active Or Not  */
@JsonClass(generateAdapter = true)
data class UserActiveOrNotRequest(
    @Json(name = "userid")
    val userid: String? = null
)


@JsonClass(generateAdapter = true)
data class UserActiveOrNotResponse(
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)


/* reedm request */

@JsonClass(generateAdapter = true)
data class SaveCatalogueRedemptionRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "APIVendor")
    val apiVendor: String? = null,
    @Json(name = "MerchantEmailID")
    val merchantEmailID: String? = null,
    @Json(name = "MerchantID")
    val merchantID: Int? = null,
    @Json(name = "MerchantMobileNo")
    val merchantMobileNo: String? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "MemberName")
    val memberName: String? = null,
    @Json(name = "DealerLoyaltyId")
    val dealerLoyaltyId: String? = null,
    @Json(name = "UserName")
    val userName: String? = null,
    @Json(name = "TotalPointsRedeemed")
    val totalPointsRedeemed: String? = null,
    @Json(name = "ObjCatalogueList")
    val objCatalogueList: List<ObjCatalogueList>? = null,
    @Json(name = "ObjCustShippingAddressDetails")
    val objCustShippingAddressDetails: ObjCustShippingAddressDetails? = null,
    @Json(name = "SourceMode")
    val sourceMode: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueList(
    @Json(name = "CatalogueId")
    var catalogueId: Int? = null,
     @Json(name = "DreamGiftId")
    var dreamGiftId: String? = null,
    @Json(name = "LoyaltyId")
    var loyaltyId: String? = null,
    @Json(name = "PointBalance")
    var pointBalance: String? = null,
    @Json(name = "DeliveryType")
    var deliveryType: String? = null,
    @Json(name = "HasPartialPayment")
    var hasPartialPayment: Boolean? = null,
    @Json(name = "NoOfPointsDebit")
    var noOfPointsDebit: String? = null,
    @Json(name = "NoOfQuantity")
    var noOfQuantity: Int? = null,
    @Json(name = "PointsRequired")
    var pointsRequired: String? = null,
    @Json(name = "ProductCode")
    var productCode: String? = null,
    @Json(name = "ProductImage")
    var productImage: String? = null,
    @Json(name = "ProductName")
    var productName: String? = null,
    @Json(name = "RedemptionDate")
    var redemptionDate: String? = null,
    @Json(name = "RedemptionId")
    var redemptionId: Int? = null,
    @Json(name = "RedemptionRefno")
    var redemptionRefno: String? = null,
    @Json(name = "RedemptionTypeId")
    var redemptionTypeId: Int? = null,
    @Json(name = "Status")
    var status: Int? = null,
    @Json(name = "CatogoryId")
    var catogoryId: Int? = null,
    @Json(name = "CustomerCartId")
    var customerCartId: Int? = null,
    @Json(name = "TermsCondition")
    var termsCondition: String? = null,
    @Json(name = "TotalCash")
    var totalCash: Int? = null,
    @Json(name = "VendorId")
    var vendorId: Int? = null,
    @Json(name = "VendorName")
    var vendorName: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustShippingAddressDetails(
    @Json(name = "Address1")
    val address1: String? = null,
    @Json(name = "CityId")
    val cityId: Int? = null,
    @Json(name = "CityName")
    val cityName: String? = null,
    @Json(name = "CountryId")
    val countryId: Int? = null,
    @Json(name = "Email")
    val email: String? = null,
    @Json(name = "FullName")
    val fullName: String? = null,
    @Json(name = "Mobile")
    val mobile: Long? = null,
    @Json(name = "StateId")
    val stateId: Int? = null,
    @Json(name = "StateName")
    val stateName: String? = null,
    @Json(name = "Zip")
    val zip: Int? = null
)


@JsonClass(generateAdapter = true)
data class SaveCatalogueRedemptionResponse(
    @Json(name = "membershipID")
    val membershipID: Any? = null,
    @Json(name = "pdfLink")
    val pdfLink: Any? = null,
    @Json(name = "redemptionReferenceNumber")
    val redemptionReferenceNumber: Any? = null,
    @Json(name = "redemptionStatus")
    val redemptionStatus: Any? = null,
    @Json(name = "responseCode")
    val responseCode: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null,
    @Json(name = "uniqueID")
    val uniqueID: Any? = null,
    @Json(name = "userId")
    val userId: Int? = null
)


// Send SMS For Sucess Redemption Mobile App
@JsonClass(generateAdapter = true)
data class SendSMSForSucessRedemReq(
    @Json(name = "CustomerName")
    val customerName: String? = null,
    @Json(name = "EmailID")
    val emailID: String? = null,
    @Json(name = "LoyaltyID")
    val loyaltyID: String? = null,
    @Json(name = "Mobile")
    val mobile: String? = null,
    @Json(name = "PointBalance")
    val pointBalance: Int? = null,
    @Json(name = "RedeemedPoint")
    val redeemedPoint: String? = null,
    @Json(name = "RedemptionRefno")
    val redemptionRefno: String? = null,
    @Json(name = "ProductName")
    val productName: String? = null
)


@JsonClass(generateAdapter = true)
data class SendSMSFOrSucessRedemRes(
    @Json(name = "SendSMSForSuccessfulRedemptionMobileAppResult")
    val sendSMSForSuccessfulRedemptionMobileAppResult: Boolean? = null
)


@JsonClass(generateAdapter = true)
data class UpdateDreamGiftRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "DreamGiftId")
    val dreamGiftId: String? = null,
    @Json(name = "GiftStatusId")
    val giftStatusId: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateDreamGiftResponse(
    @Json(name = "DreamGiftName")
    val dreamGiftName: String? = null,
    @Json(name = "MemberName")
    val memberName: String? = null,
    @Json(name = "Mobile")
    val mobile: String? = null,
    @Json(name = "Points")
    val points: String? = null,
    @Json(name = "ReturnMessage")
    val returnMessage: Any? = null,
    @Json(name = "ReturnValue")
    val returnValue: Int? = null,
    @Json(name = "TotalRecords")
    val totalRecords: Int? = null
)