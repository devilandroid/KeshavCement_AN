package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import java.io.Serializable

/*Dream Gift List Request*/
@JsonClass(generateAdapter = true)
data class DreamGiftRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "LoyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "Status")
    val status: String? = null,
    @Json(name = "Domain")
    val domain: String? = null
)
/*Dream Gift List Response*/
@JsonClass(generateAdapter = true)
data class DreamGiftResponse(
    @Json(name = "lstDreamGift")
    val lstDreamGift: List<LstDreamGift>? = null
)

@JsonClass(generateAdapter = true)
data class LstDreamGift(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "avgEarningPoints")
    val avgEarningPoints: Int? = null,
    @Json(name = "contractorName")
    val contractorName: String? = null,
    @Json(name = "dreamGiftDescription")
    val dreamGiftDescription: Any? = null,
    @Json(name = "dreamGiftId")
    val dreamGiftId: String? = null,
    @Json(name = "dreamGiftName")
    val dreamGiftName: String? = null,
    @Json(name = "earlyExpectedDate")
    val earlyExpectedDate: Any? = null,
    @Json(name = "earlyExpectedPoints")
    val earlyExpectedPoints: Int? = null,
    @Json(name = "expectedDate")
    val expectedDate: Any? = null,
    @Json(name = "giftImage")
    val giftImage: Any? = null,
    @Json(name = "giftStatusId")
    val giftStatusId: Int? = null,
    @Json(name = "giftType")
    val giftType: String? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "is_Redeemable")
    val isRedeemable: Int? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "jDesiredDate")
    val jDesiredDate: String? = null,
    @Json(name = "lateExpectedDate")
    val lateExpectedDate: Any? = null,
    @Json(name = "lateExpectedPoints")
    val lateExpectedPoints: Int? = null,
    @Json(name = "loyaltyID")
    val loyaltyID: String? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "pointsBalance")
    val pointsBalance: Int? = null,
    @Json(name = "pointsRequired")
    val pointsRequired: Int? = null,
    @Json(name = "remark")
    val remark: String? = null,
    @Json(name = "role")
    val role: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "tdsPoints")
    val tdsPoints: Double? = null,
    @Json(name = "token")
    val token: Any? = null,
    @Json(name = "totRow")
    val totRow: Int? = null,
    @Json(name = "is_DreamGiftRedeemable")
    val is_DreamGiftRedeemable: Int? = null
):Serializable

/*Dream Gift Details Request */
@JsonClass(generateAdapter = true)
data class DreamGiftDetailRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "DreamGiftId")
    val dreamGiftId: String? = null,
    @Json(name = "LoyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "Domain")
    val domain: String? = null
)

/*Dream Gift Details Response*/
@JsonClass(generateAdapter = true)
data class DreamGiftDetailResponse(
    @Json(name = "lstDreamGift")
    val lstDreamGift: List<LstDreamGifts>? = null
)

@JsonClass(generateAdapter = true)
data class LstDreamGifts(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "address")
    val address: Any? = null,
    @Json(name = "avgEarningPoints")
    val avgEarningPoints: Int? = null,
    @Json(name = "contractorName")
    val contractorName: Any? = null,
    @Json(name = "dreamGiftDescription")
    val dreamGiftDescription: Any? = null,
    @Json(name = "dreamGiftId")
    val dreamGiftId: Int? = null,
    @Json(name = "dreamGiftName")
    val dreamGiftName: String? = null,
    @Json(name = "earlyExpectedDate")
    val earlyExpectedDate: String? = null,
    @Json(name = "earlyExpectedPoints")
    val earlyExpectedPoints: Int? = null,
    @Json(name = "expectedDate")
    val expectedDate: String? = null,
    @Json(name = "giftImage")
    val giftImage: Any? = null,
    @Json(name = "giftStatusId")
    val giftStatusId: Int? = null,
    @Json(name = "giftType")
    val giftType: String? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "is_Redeemable")
    val isRedeemable: Int? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "jDesiredDate")
    val jDesiredDate: String? = null,
    @Json(name = "lateExpectedDate")
    val lateExpectedDate: String? = null,
    @Json(name = "lateExpectedPoints")
    val lateExpectedPoints: Int? = null,
    @Json(name = "loyaltyID")
    val loyaltyID: Any? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "pointsBalance")
    val pointsBalance: Int? = null,
    @Json(name = "pointsRequired")
    val pointsRequired: Double? = null,
    @Json(name = "remark")
    val remark: Any? = null,
    @Json(name = "role")
    val role: Any? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "tdsPoints")
    val tdsPoints: Double? = null,
    @Json(name = "token")
    val token: Any? = null,
    @Json(name = "totRow")
    val totRow: Int? = null,
    @Json(name = "is_DreamGiftRedeemable")
    val is_DreamGiftRedeemable: Int? = null,
    @Json(name = "pointsRequiredPerMonth")
    val pointsRequiredPerMonth: String? = null,
    @Json(name = "monthsRequiredToAchieve")
    val monthsRequiredToAchieve: String? = null,
    @Json(name = "pointsRequiredPerDay")
    val pointsRequiredPerDay: String? = null,
    @Json(name = "daysRequiredToAchieve")
    val daysRequiredToAchieve: String? = null
)

 /*  Dream Gift Remove Request  */

@JsonClass(generateAdapter = true)
data class DreamGiftRemoveRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "DreamGiftId")
    val dreamGiftId: String? = null,
    @Json(name = "GiftStatusId")
    val giftStatusId: Int? = null
)

/*  Dream Gift Remove Response  */
@JsonClass(generateAdapter = true)
data class DreamGiftRemoveResponse(
    @Json(name = "dreamGiftName")
    val dreamGiftName: String? = null,
    @Json(name = "memberName")
    val memberName: String? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "points")
    val points: String? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)