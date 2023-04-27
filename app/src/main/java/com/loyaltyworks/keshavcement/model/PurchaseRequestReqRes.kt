package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*** Common Spinner ***/
data class CommonSpinner(
    var name: String? = null,
    var id: Int? = null,
)

/*** Product Dropdown List Request ***/
@JsonClass(generateAdapter = true)
data class ProductDropdownRequest(
    @Json(name = "ActionType")
    val actionType: String? = null
)

/*** Product Dropdown List Response ***/
@JsonClass(generateAdapter = true)
data class ProductDropdownResponse(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "lstAttributesDetails")
    val lstAttributesDetails: List<LstAttributesDetailProduct>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetailProduct(
    @Json(name = "attributeContents")
    val attributeContents: Any? = null,
    @Json(name = "attributeCurrencyId")
    val attributeCurrencyId: Any? = null,
    @Json(name = "attributeId")
    var attributeId: Int? = null,
    @Json(name = "attributeNames")
    val attributeNames: Any? = null,
    @Json(name = "attributeType")
    val attributeType: String? = null,
    @Json(name = "attributeValue")
    var attributeValue: String? = null,
    @Json(name = "categoryCode")
    val categoryCode: Any? = null,
    @Json(name = "imageUrl")
    val imageUrl: Any? = null,
    @Json(name = "totalEarning")
    val totalEarning: Double? = null
)

/*** Dealer Sub-Dealer List Request ***/
@JsonClass(generateAdapter = true)
data class DealerSubDealerListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "SearchText")
    val searchText: String? = null
)

/*** Dealer Sub-Dealer List Response ***/
@JsonClass(generateAdapter = true)
data class DealerSubDealerListResponse(
    @Json(name = "lstCust1ParentChildMapping")
    val lstCust1ParentChildMapping: List<Any?>? = null,
    @Json(name = "lstCustParentChildMapping")
    val lstCustParentChildMapping: List<LstCustParentChildMappingDealer>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCustParentChildMappingDealer(
    @Json(name = "address1")
    val address1: Any? = null,
    @Json(name = "address2")
    val address2: Any? = null,
    @Json(name = "addressLatitude")
    val addressLatitude: Any? = null,
    @Json(name = "addressLongitude")
    val addressLongitude: Any? = null,
    @Json(name = "childCustomerUserId")
    val childCustomerUserId: Int? = null,
    @Json(name = "childFirstName")
    val childFirstName: Any? = null,
    @Json(name = "childLoyaltyId")
    val childLoyaltyId: Any? = null,
    @Json(name = "childMobileNumber")
    val childMobileNumber: Any? = null,
    @Json(name = "cityId")
    val cityId: Int? = null,
    @Json(name = "cityName")
    val cityName: Any? = null,
    @Json(name = "cust1FirstName")
    val cust1FirstName: Any? = null,
    @Json(name = "cust1UserId")
    val cust1UserId: Int? = null,
    @Json(name = "cust2FirstName")
    val cust2FirstName: Any? = null,
    @Json(name = "cust2UserId")
    val cust2UserId: Int? = null,
    @Json(name = "customerGrade")
    val customerGrade: Any? = null,
    @Json(name = "customerImage")
    val customerImage: Any? = null,
    @Json(name = "customerType")
    val customerType: Any? = null,
    @Json(name = "designationName")
    val designationName: Any? = null,
    @Json(name = "email")
    val email: Any? = null,
    @Json(name = "enrollmentDate")
    val enrollmentDate: Any? = null,
    @Json(name = "firstName")
    var firstName: String? = null,
    @Json(name = "isActive")
    val isActive: Int? = null,
    @Json(name = "lastName")
    val lastName: Any? = null,
    @Json(name = "loyaltyID")
    val loyaltyID: String? = null,
    @Json(name = "masterCustomerUser")
    val masterCustomerUser: Any? = null,
    @Json(name = "masterCustomerUserId")
    val masterCustomerUserId: Int? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "parentFirstName")
    val parentFirstName: Any? = null,
    @Json(name = "parentLoyaltyId")
    val parentLoyaltyId: Any? = null,
    @Json(name = "parentMobileNumber")
    val parentMobileNumber: Any? = null,
    @Json(name = "parentUserId")
    val parentUserId: Int? = null,
    @Json(name = "password")
    val password: Any? = null,
    @Json(name = "seFirstName")
    val seFirstName: Any? = null,
    @Json(name = "seLoyaltyId")
    val seLoyaltyId: Any? = null,
    @Json(name = "seUserId")
    val seUserId: Int? = null,
    @Json(name = "sfaCode")
    val sfaCode: Any? = null,
    @Json(name = "talukName")
    val talukName: Any? = null,
    @Json(name = "totalEnrollCount")
    val totalEnrollCount: Int? = null,
    @Json(name = "totalPointsBalance")
    val totalPointsBalance: Int? = null,
    @Json(name = "totalPointsEarned")
    val totalPointsEarned: Any? = null,
    @Json(name = "totalPointsRedeemed")
    val totalPointsRedeemed: Any? = null,
    @Json(name = "totalRedeemedCount")
    val totalRedeemedCount: Int? = null,
    @Json(name = "totalTransactionCount")
    val totalTransactionCount: Int? = null,
    @Json(name = "userID")
    var userID: Int? = null,
    @Json(name = "firmName")
    var firmName: String? = null
)

/*** Submit Purchase Request ***/
@JsonClass(generateAdapter = true)
data class SubmitPurchaseRequest(
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ProductSaveDetailList")
    val productSaveDetailList: List<ProductSaveDetail>? = null,
    @Json(name = "RitailerId")
    val ritailerId: String? = null,
    @Json(name = "SourceDevice")
    val sourceDevice: Int? = null,
    @Json(name = "TranDate")
    val tranDate: String? = null,
    @Json(name = "Approval_Status")
    val approvalStatus: String? = null
)

@JsonClass(generateAdapter = true)
data class ProductSaveDetail(
    @Json(name = "ProductCode")
    val productCode: String? = null,
    @Json(name = "Quantity")
    val quantity: String? = null
)

/*** Submit Purchase Response ***/
@JsonClass(generateAdapter = true)
data class SubmitPurchaseResponse(
    @Json(name = "creditedPoints")
    val creditedPoints: Int? = null,
    @Json(name = "firstName")
    val firstName: Any? = null,
    @Json(name = "lstVehicles")
    val lstVehicles: Any? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "objProductFileStatus")
    val objProductFileStatus: Any? = null,
    @Json(name = "pointsBalance")
    val pointsBalance: Int? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null
)