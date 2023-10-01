package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** State List Request ***/
@JsonClass(generateAdapter = true)
data class StateListRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "CountryID")
    val countryID: String? = null,
    @Json(name = "IsActive")
    val isActive: String? = null,
    @Json(name = "SortColumn")
    val sortColumn: String? = null,
    @Json(name = "SortOrder")
    val sortOrder: String? = null,
    @Json(name = "StartIndex")
    val startIndex: String? = null
)

/*** State List Response ***/
@JsonClass(generateAdapter = true)
data class StateListResponse(
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "stateList")
    val stateList: List<State>? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class State(
    @Json(name = "countryCode")
    val countryCode: Any? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: String? = null,
    @Json(name = "countryType")
    val countryType: Any? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "mobilePrefix")
    val mobilePrefix: Any? = null,
    @Json(name = "row")
    val row: Int? = null,
    @Json(name = "stateCode")
    val stateCode: String? = null,
    @Json(name = "stateId")
    val stateId: Int? = null,
    @Json(name = "stateName")
    val stateName: String? = null
)

/*** District List Request ***/
@JsonClass(generateAdapter = true)
data class DistrictListRequest(
    @Json(name = "StateId")
    val stateId: String? = null
)

/*** District List Response ***/
@JsonClass(generateAdapter = true)
data class DistrictListResponse(
    @Json(name = "lstDistrict")
    val lstDistrict: List<LstDistrict>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstDistrict(
    @Json(name = "districtCode")
    val districtCode: String? = null,
    @Json(name = "districtId")
    var districtId: Int? = null,
    @Json(name = "districtName")
    var districtName: String? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "row")
    val row: Int? = null,
    @Json(name = "stateId")
    val stateId: Int? = null
)

/*** Taluk List Request ***/
@JsonClass(generateAdapter = true)
data class TalukListRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "DistrictId")
    val districtId: String? = null
)

/*** Taluk List Response ***/
@JsonClass(generateAdapter = true)
data class TalukListResponse(
    @Json(name = "lstTaluk")
    val lstTaluk: List<LstTaluk>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstTaluk(
    @Json(name = "districtId")
    val districtId: Int? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "row")
    val row: Int? = null,
    @Json(name = "stateId")
    val stateId: Int? = null,
    @Json(name = "talukCityId")
    val talukCityId: Int? = null,
    @Json(name = "talukCityMappingId")
    val talukCityMappingId: Int? = null,
    @Json(name = "talukCode")
    val talukCode: String? = null,
    @Json(name = "talukId")
    var talukId: Int? = null,
    @Json(name = "talukName")
    var talukName: String? = null
)

/***  City Request ***/
@JsonClass(generateAdapter = true)
data class CityListRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "IsActive")
    val isActive: String? = null,
    @Json(name = "SortColumn")
    val sortColumn: String? = null,
    @Json(name = "SortOrder")
    val sortOrder: String? = null,
    @Json(name = "StartIndex")
    val startIndex: String? = null,
    @Json(name = "StateId")
    val stateId: String? = null
)

/***  City Response ***/
@JsonClass(generateAdapter = true)
data class CityListResponse(
    @Json(name = "cityList")
    val cityList: List<LstCity>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCity(
    @Json(name = "cityCode")
    val cityCode: String? = null,
    @Json(name = "cityId")
    var cityId: Int? = null,
    @Json(name = "cityName")
    var cityName: String? = null,
    @Json(name = "countryCode")
    val countryCode: Any? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: Any? = null,
    @Json(name = "countryType")
    val countryType: Any? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "mobilePrefix")
    val mobilePrefix: Any? = null,
    @Json(name = "row")
    val row: Int? = null,
    @Json(name = "stateCode")
    val stateCode: Any? = null,
    @Json(name = "stateId")
    val stateId: Int? = null,
    @Json(name = "stateName")
    val stateName: String? = null
)


/*** Common Spinner ***/
@JsonClass(generateAdapter = true)
data class CommonSpinners(
    var name: String? = null,
    var id: Int? = null,
    val type: String? = null
)