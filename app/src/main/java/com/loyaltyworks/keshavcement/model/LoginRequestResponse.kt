package com.loyaltyworks.keshavcement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/*** Customer Existence request ***/
@JsonClass(generateAdapter = true)
data class CustomerExistenceRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "Location")
    val location: Location? = null,
    @Json(name = "ActorId")
    val actorId: String? = null
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "UserName")
    val userName: String? = null
)

/*** SaveAndGetOTPDetails Request ***/
@JsonClass(generateAdapter = true)
data class SaveAndGetOTPDetailsRequest(
    val MerchantUserName: String,
    val MobileNo: String,
    val OTPType: String,
    val UserId: Int,
    val UserName: String
)

/*** SaveAndGetOTPDetails Response ***/
@JsonClass(generateAdapter = true)
data class SaveAndGetOTPDetailsResponse(
    @Json(name = "adminList")
    var adminList: Any? = null,
    @Json(name = "merchantEmailSMSDetails")
    var merchantEmailSMSDetails: Any? = null,
    @Json(name = "merchantEmailSMSParameterDetails")
    var merchantEmailSMSParameterDetails: Any? = null,
    @Json(name = "returnMessage")
    var returnMessage: String? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null
)

/*** ReferalCode Existancy Request ***/
@JsonClass(generateAdapter = true)
data class ReferalCodeExistancyRequest(
    @Json(name = "ActionType")
    var actionType: Int? = null,
    @Json(name = "Location")
    var location: Locationn? = null
)

@JsonClass(generateAdapter = true)
data class Locationn(
    @Json(name = "UserName")
    var userName: String? = null
)

/*** Register Request ***/
@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "lstIdentityInfo")
    val lstIdentityInfo: LstIdentityInfo? = null,
    @Json(name = "ObjCustomer")
    val objCustomer: ObjCustomer? = null,
    @Json(name = "ObjCustomerOfficalInfo")
    val objCustomerOfficalInfo: ObjCustomerOfficalInfoRegister? = null
)

@JsonClass(generateAdapter = true)
data class LstIdentityInfo(
    @Json(name = "IdentityID")
    val identityID: String? = null,
    @Json(name = "IdentityNo")
    val identityNo: String? = null,
    @Json(name = "IdentityType")
    val identityType: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomer(
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
    val talukId: String? = null,
    @Json(name = "ReferrerCode")
    var referrerCode: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerOfficalInfoRegister(
    @Json(name = "CompanyName")
    val companyName: String? = null,
    @Json(name = "SAPCode")
    val sAPCode: String? = null,
    @Json(name = "OfficialGSTNumber")
    val officialGSTNumber: String? = null
)

/*** Register Response ***/
@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

/*** Login Request ***/
@JsonClass(generateAdapter = true)
data class LoginRequest(
    var Password: String? = null,
    var UserName: String? = null,
    var UserActionType: String? = null,
    var Browser: String? = null,
    var LoggedDeviceName: String? = null,
    var PushID: String? = null,
    var UserType: String? = null
)

/*** Login Response ***/
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "lstMerchantImageDetails")
    val lstMerchantImageDetails: Any? = null,
    @Json(name = "merchantImageDetails")
    val merchantImageDetails: Any? = null,
    @Json(name = "objUserDetailedInfo")
    val objUserDetailedInfo: Any? = null,
    @Json(name = "userId")
    val userId: Int? = null,
    @Json(name = "userList")
    val userList: List<User?>? = null
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "c_MerchantId")
    val cMerchantId: Int? = null,
    @Json(name = "cityName")
    val cityName: Any? = null,
    @Json(name = "commonUserMobile")
    val commonUserMobile: Any? = null,
    @Json(name = "commonUserName")
    val commonUserName: Any? = null,
    @Json(name = "country")
    val country: Any? = null,
    @Json(name = "countryCode")
    val countryCode: Any? = null,
    @Json(name = "currency")
    val currency: Any? = null,
    @Json(name = "custAccountNumber")
    val custAccountNumber: String? = null,
    @Json(name = "custAccountType")
    val custAccountType: Any? = null,
    @Json(name = "customerGrade")
    val customerGrade: Any? = null,
    @Json(name = "customerTypeID")
    val customerTypeID: Int? = null,
    @Json(name = "dob")
    val dob: Any? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "encrypted_OTP_PIN")
    val encryptedOTPPIN: Any? = null,
    @Json(name = "isBlacklisted")
    val isBlacklisted: Int? = null,
    @Json(name = "isDelete")
    val isDelete: Int? = null,
    @Json(name = "isDormant")
    val isDormant: Int? = null,
    @Json(name = "isGeofenceActive")
    val isGeofenceActive: Int? = null,
    @Json(name = "isOnHold")
    val isOnHold: Int? = null,
    @Json(name = "isUserActive")
    val isUserActive: Int? = null,
    @Json(name = "language")
    val language: Any? = null,
    @Json(name = "locationCountryID")
    val locationCountryID: Int? = null,
    @Json(name = "locationId")
    val locationId: Int? = null,
    @Json(name = "locationName")
    val locationName: String? = null,
    @Json(name = "locationType")
    val locationType: Any? = null,
    @Json(name = "memberSince")
    val memberSince: Any? = null,
    @Json(name = "merchantEmailID")
    val merchantEmailID: String? = null,
    @Json(name = "merchantId")
    val merchantId: Int? = null,
    @Json(name = "merchant_logo")
    val merchantLogo: Any? = null,
    @Json(name = "merchantMobileNo")
    val merchantMobileNo: String? = null,
    @Json(name = "merchantName")
    val merchantName: Any? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "parentLocationId")
    val parentLocationId: Int? = null,
    @Json(name = "parentLocationName")
    val parentLocationName: Any? = null,
    @Json(name = "password")
    val password: String? = null,
    @Json(name = "pinStatus")
    val pinStatus: Int? = null,
    @Json(name = "prefix")
    val prefix: String? = null,
    @Json(name = "result")
    val result: Int? = null,
    @Json(name = "roleName")
    val roleName: Any? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "superParentLocationId")
    val superParentLocationId: Int? = null,
    @Json(name = "userGender")
    val userGender: String? = null,
    @Json(name = "userId")
    val userId: Int? = null,
    @Json(name = "userImage")
    val userImage: String? = null,
    @Json(name = "userLastName")
    val userLastName: Any? = null,
    @Json(name = "userName")
    val userName: String? = null,
    @Json(name = "userType")
    val userType: String? = null,
    @Json(name = "verifiedStatus")
    val verifiedStatus: Int? = null
)