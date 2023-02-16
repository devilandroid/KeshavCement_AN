package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*** My Support Executive List Request ***/
@JsonClass(generateAdapter = true)
data class MySupportExecutiveRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "SearchText")
    val searchText: String? = null,
    @Json(name = "StartIndex")
    var startIndex: Int? = null,
    @Json(name = "PageSize")
    var noOfRows: Int? = null
)

/*** My Support Executive List Response ***/
@JsonClass(generateAdapter = true)
data class MySupportExecutiveResponse(
    @Json(name = "lstCust1ParentChildMapping")
    val lstCust1ParentChildMapping: List<Any?>? = null,
    @Json(name = "lstCustParentChildMapping")
    val lstCustParentChildMapping: List<LstCustParentChildMapping>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCustParentChildMapping(
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
    val customerGrade: String? = null,
    @Json(name = "customerImage")
    val customerImage: String? = null,
    @Json(name = "customerType")
    val customerType: String? = null,
    @Json(name = "designationName")
    val designationName: Any? = null,
    @Json(name = "email")
    val email: Any? = null,
    @Json(name = "enrollmentDate")
    val enrollmentDate: String? = null,
    @Json(name = "firstName")
    val firstName: String? = null,
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
    val password: String? = null,
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
    val userID: Int? = null
)

/*** Create New Support Executive Request ***/
@JsonClass(generateAdapter = true)
data class CreateSupportExecutiveRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "HierarchyMapDetails")
    val hierarchyMapDetails: HierarchyMapDetails? = null,
    @Json(name = "ObjCustomer")
    val objCustomer: ObjCustomerSupportExecutive? = null
)

@JsonClass(generateAdapter = true)
data class HierarchyMapDetails(
    @Json(name = "CustomerUserID")
    val customerUserID: String? = null,
    @Json(name = "UserUserID")
    val userUserID: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerSupportExecutive(
    @Json(name = "CustomerMobile")
    val customerMobile: String? = null,
    @Json(name = "CustomerTypeID")
    val customerTypeID: String? = null,
    @Json(name = "FirstName")
    val firstName: String? = null,
    @Json(name = "IsActive")
    val isActive: String? = null,
    @Json(name = "MerchantId")
    val merchantId: String? = null,
    @Json(name = "RegistrationSource")
    val registrationSource: String? = null,
    @Json(name = "Password")
    val password: String? = null
)

/*** Create New Support Executive Response ***/
@JsonClass(generateAdapter = true)
data class CreateSupportExecutiveResponse(
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

/*** Activate / Deactivate Support Executive Request ***/
@JsonClass(generateAdapter = true)
data class ActivateDeactivateExecutiveRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "CustomerId")
    val customerId: String? = null,
    @Json(name = "IsActive")
    val isActive: String? = null
)

/*** Activate / Deactivate Support Executive Response ***/
@JsonClass(generateAdapter = true)
data class ActivateDeactivateExecutiveResponse(
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)
