package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Change Password Request ***/
@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    @Json(name = "Password")
    val password: String? = null,
    @Json(name = "UserActionType")
    val userActionType: String? = null,
    @Json(name = "UserName")
    val userName: String? = null
)

/*** Change Password Response ***/
@JsonClass(generateAdapter = true)
data class ChangePasswordResponse(
    @Json(name = "lstMerchantImageDetails")
    val lstMerchantImageDetails: Any? = null,
    @Json(name = "merchantImageDetails")
    val merchantImageDetails: Any? = null,
    @Json(name = "objUserDetailedInfo")
    val objUserDetailedInfo: Any? = null,
    @Json(name = "userId")
    val userId: Int? = null,
    @Json(name = "userList")
    val userList: List<UserChangePassw>? = null
)

@JsonClass(generateAdapter = true)
data class UserChangePassw(
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
    val custAccountNumber: Any? = null,
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
    val locationName: Any? = null,
    @Json(name = "locationType")
    val locationType: Any? = null,
    @Json(name = "memberSince")
    val memberSince: Any? = null,
    @Json(name = "merchantEmailID")
    val merchantEmailID: Any? = null,
    @Json(name = "merchantId")
    val merchantId: Int? = null,
    @Json(name = "merchant_logo")
    val merchantLogo: Any? = null,
    @Json(name = "merchantMobileNo")
    val merchantMobileNo: Any? = null,
    @Json(name = "merchantName")
    val merchantName: Any? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "name")
    val name: Any? = null,
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
    val status: Any? = null,
    @Json(name = "superParentLocationId")
    val superParentLocationId: Int? = null,
    @Json(name = "userGender")
    val userGender: String? = null,
    @Json(name = "userId")
    val userId: Int? = null,
    @Json(name = "userImage")
    val userImage: Any? = null,
    @Json(name = "userLastName")
    val userLastName: Any? = null,
    @Json(name = "userName")
    val userName: String? = null,
    @Json(name = "userType")
    val userType: String? = null,
    @Json(name = "verifiedStatus")
    val verifiedStatus: Int? = null
)