package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*** Customer Listing Request ***/
@JsonClass(generateAdapter = true)
data class CustomerListingRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "SearchText")
    val searchText: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "PageSize")
    val pageSize: Int? = null
)

/*** Customer Listing Response ***/
@JsonClass(generateAdapter = true)
data class CustomerListingResponse(
    @Json(name = "lstCust1ParentChildMapping")
    val lstCust1ParentChildMapping: List<Any?>? = null,
    @Json(name = "lstCustParentChildMapping")
    val lstCustParentChildMapping: List<LstCustParentChildMappingCustList>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCustParentChildMappingCustList(
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
    val email: String? = null,
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
    val userID: Int? = null,
    @Json(name = "lastRedemptionDate")
    val lastRedemptionDate: String? = null,
    @Json(name = "lastPurchaseDate")
    val lastPurchaseDate: String? = null
)

/*** Enrollment Request ***/
@JsonClass(generateAdapter = true)
data class EnrollmentRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "HierarchyMapDetails")
    val hierarchyMapDetails: HierarchyMapDetailsEnrollment? = null,
    @Json(name = "ObjCustomer")
    val objCustomer: ObjCustomerEnrollment? = null,
    @Json(name = "ObjCustomerOfficalInfo")
    val objCustomerOfficalInfo: ObjCustomerOfficalInfoEnrollment? = null
)

@JsonClass(generateAdapter = true)
data class HierarchyMapDetailsEnrollment(
    @Json(name = "CustomerUserID")
    val customerUserID: String? = null,
    @Json(name = "UserUserID")
    val userUserID: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerEnrollment(
    @Json(name = "Address")
    val address: String? = null,
    @Json(name = "CustomerCityId")
    val customerCityId: String? = null,
    @Json(name = "CustomerEmail")
    val customerEmail: String? = null,
    @Json(name = "CustomerMobile")
    val customerMobile: String? = null,
    @Json(name = "CustomerStateId")
    val customerStateId: String? = null,
    @Json(name = "CustomerTypeID")
    val customerTypeID: String? = null,
    @Json(name = "CustomerZip")
    val customerZip: String? = null,
    @Json(name = "DistrictId")
    val districtId: String? = null,
    @Json(name = "FirstName")
    val firstName: String? = null,
    @Json(name = "IsActive")
    val isActive: String? = null,
    @Json(name = "MerchantId")
    val merchantId: String? = null,
    @Json(name = "RegistrationSource")
    val registrationSource: String? = null,
    @Json(name = "TalukId")
    val talukId: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerOfficalInfoEnrollment(
    @Json(name = "CompanyName")
    val companyName: String? = null,
    @Json(name = "SAPCode")
    val sAPCode: String? = null
)

/*** Enrollment Response ***/
@JsonClass(generateAdapter = true)
data class EnrollmentResponse(
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)