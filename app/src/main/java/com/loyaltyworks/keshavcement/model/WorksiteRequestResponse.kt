package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Worksite List Request ***/
@JsonClass(generateAdapter = true)
data class WorksiteListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "CustomerID")
    val customerID: String? = null,
    @Json(name = "FromDate")
    val fromDate: String? = null,
    @Json(name = "PageSize")
    val pageSize: Int? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "ToDate")
    val toDate: String? = null,
    @Json(name = "VerificationStatus")
    val verificationStatus: String? = null
)

/*** Worksite List Response ***/
@JsonClass(generateAdapter = true)
data class WorksiteListResponse(
    @Json(name = "lstWorkSiteInfo")
    val lstWorkSiteInfo: List<LstWorkSiteInfo>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstWorkSiteInfo(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "addressLatitude")
    val addressLatitude: String? = null,
    @Json(name = "addressLongitude")
    val addressLongitude: String? = null,
    @Json(name = "cityID")
    val cityID: Int? = null,
    @Json(name = "cityName")
    val cityName: String? = null,
    @Json(name = "contactNumber")
    val contactNumber: String? = null,
    @Json(name = "contactNumber1")
    val contactNumber1: String? = null,
    @Json(name = "contactPersonName")
    val contactPersonName: String? = null,
    @Json(name = "contactPersonName1")
    val contactPersonName1: String? = null,
    @Json(name = "locationname")
    val locationname: String? = null,
    @Json(name = "countryID")
    val countryID: Int? = null,
    @Json(name = "createdDate")
    val createdDate: String? = null,
    @Json(name = "customerID")
    val customerID: Int? = null,
    @Json(name = "domain")
    val domain: Any? = null,
    @Json(name = "fromDate")
    val fromDate: Any? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "landMark")
    val landMark: String? = null,
    @Json(name = "loyaltyID")
    val loyaltyID: Any? = null,
    @Json(name = "pincode")
    val pincode: String? = null,
    @Json(name = "remarks")
    val remarks: String? = null,
    @Json(name = "siteImageURl")
    val siteImageURl: String? = null,
    @Json(name = "siteLocationID")
    val siteLocationID: Int? = null,
    @Json(name = "siteName")
    val siteName: String? = null,
    @Json(name = "siteUserID")
    val siteUserID: Int? = null,
    @Json(name = "startIndex")
    val startIndex: Int? = null,
    @Json(name = "stateID")
    val stateID: Int? = null,
    @Json(name = "stateName")
    val stateName: String? = null,
    @Json(name = "tentativeDate")
    val tentativeDate: String? = null,
    @Json(name = "toDate")
    val toDate: Any? = null,
    @Json(name = "token")
    val token: Any? = null,
    @Json(name = "totalRows")
    val totalRows: Int? = null,
    @Json(name = "updatedAddress")
    val updatedAddress: Any? = null,
    @Json(name = "verification")
    val verification: Int? = null,
    @Json(name = "verificationStatus")
    val verificationStatus: String? = null,
    @Json(name = "workSiteApproverName")
    val workSiteApproverName: Any? = null,
    @Json(name = "workSiteID")
    val workSiteID: Int? = null,
    @Json(name = "worklevel")
    val worklevel: String? = null
)

/*** Save Worksite Request ***/
@JsonClass(generateAdapter = true)
data class SaveWorksiteRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "Address")
    val address: String? = null,
    @Json(name = "AddressLatitude")
    val addressLatitude: String? = null,
    @Json(name = "AddressLongitude")
    val addressLongitude: String? = null,
    @Json(name = "ContactNumber")
    val contactNumber: String? = null,
    @Json(name = "ContactNumber1")
    val contactNumber1: String? = null,
    @Json(name = "ContactPersonName")
    val contactPersonName: String? = null,
    @Json(name = "ContactPersonName1")
    val contactPersonName1: String? = null,
    @Json(name = "CustomerID")
    val customerID: String? = null,
    @Json(name = "Remarks")
    val remarks: String? = null,
    @Json(name = "SiteImageURl")
    val siteImageURl: String? = null,
    @Json(name = "TentativeDate")
    val tentativeDate: String? = null,
    @Json(name = "Verification")
    val verification: String? = null,
    @Json(name = "Worklevel")
    val worklevel: Int? = null,
    @Json(name = "Locationname")
    val locationname: String? = null
)

/*** Save Worksite Response ***/
@JsonClass(generateAdapter = true)
data class SaveWorksiteResponse(
    @Json(name = "lstWorkSiteInfo")
    val lstWorkSiteInfo: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)