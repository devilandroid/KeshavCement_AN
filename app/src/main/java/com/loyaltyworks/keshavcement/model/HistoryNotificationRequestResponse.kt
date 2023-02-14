package com.loyaltyworks.keshavcement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*History Notification List request*/
@JsonClass(generateAdapter = true)
data class HistoryNotificationRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var LoyaltyId: String? = null
)

/*History Notification List response*/
@JsonClass(generateAdapter = true)
data class HistoryNotificationResponse(
    @Json(name = "lstPushHistory")
    val lstPushHistory: Any? = null,
    @Json(name = "lstPushHistoryJson")
    val lstPushHistoryJson: MutableList<LstPushHistoryJson>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstPushHistoryJson(
    @Json(name = "createdBy")
    val createdBy: Int? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "imagesURL")
    val imagesURL: String? = null,
    @Json(name = "isActive")
    val isActive: Int? = null,
    @Json(name = "isRead")
    val isRead: Int? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "modifiedBy")
    val modifiedBy: Int? = null,
    @Json(name = "pushHistoryId")
    val pushHistoryId: Int? = null,
    @Json(name = "pushId")
    val pushId: String? = null,
    @Json(name = "pushMessage")
    val pushMessage: String? = null,
    @Json(name = "pushType")
    val pushType: String? = null,
    @Json(name = "sourceType")
    val sourceType: String? = null,
    @Json(name = "title")
    val title: String? = null
)

/*** History Notification Delete request ***/
@JsonClass(generateAdapter = true)
data class HistoryNotificationDetailsRequest (
    var ActionType: String? = null,
    var ActorId: String? = null,
    var LoyaltyId: String? = null,
    var PushHistoryIds: String? = null
)

/*** History Notification Delete response ***/
@JsonClass(generateAdapter = true)
data class HistoryNotificationDeleteResponse(
    @Json(name = "lstPushHistory")
    val lstPushHistory: Any? = null,
    @Json(name = "lstPushHistoryJson")
    val lstPushHistoryJson: List<LstPushHistoryJsons>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstPushHistoryJsons(
    @Json(name = "createdBy")
    val createdBy: Int? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "imagesURL")
    val imagesURL: Any? = null,
    @Json(name = "isActive")
    val isActive: Int? = null,
    @Json(name = "isRead")
    val isRead: Int? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: Any? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: Any? = null,
    @Json(name = "modifiedBy")
    val modifiedBy: Int? = null,
    @Json(name = "pushHistoryId")
    val pushHistoryId: Int? = null,
    @Json(name = "pushId")
    val pushId: Any? = null,
    @Json(name = "pushMessage")
    val pushMessage: Any? = null,
    @Json(name = "pushType")
    val pushType: Any? = null,
    @Json(name = "sourceType")
    val sourceType: Any? = null,
    @Json(name = "title")
    val title: Any? = null
)

