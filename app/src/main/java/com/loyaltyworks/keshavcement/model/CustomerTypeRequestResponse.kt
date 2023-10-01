package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*** Customer Type Request ***/
@JsonClass(generateAdapter = true)
data class CustomerTypeRequest(
    @Json(name = "ActionType")
    var actionType: Int? = null
)

/*** Customer Type Response ***/
@JsonClass(generateAdapter = true)
data class CustomerTypeResponse(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "lstAttributesDetails")
    val lstAttributesDetails: List<LstAttributesDetail>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetail(
    @Json(name = "attributeContents")
    val attributeContents: Any? = null,
    @Json(name = "attributeCurrencyId")
    val attributeCurrencyId: Any? = null,
    @Json(name = "attributeId")
    val attributeId: String? = null,
    @Json(name = "attributeNames")
    val attributeNames: Any? = null,
    @Json(name = "attributeType")
    val attributeType: String? = null,
    @Json(name = "attributeValue")
    val attributeValue: String? = null,
    @Json(name = "categoryCode")
    val categoryCode: Any? = null,
    @Json(name = "imageUrl")
    val imageUrl: Any? = null,
    @Json(name = "totalEarning")
    val totalEarning: Double? = null
)