package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json


/*** CashBackPoints List Request ***/
@JsonClass(generateAdapter = true)
data class CashbackPointsListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "CustomerTypeId")
    val customerTypeId: String? = null
)

/*** CashBackPoints List Response ***/
@JsonClass(generateAdapter = true)
data class CashbackPointsListResponse(
    @Json(name = "lstCashTransfer")
    val lstCashTransfer: List<LstCashTransfer>? = null,
    @Json(name = "lstCustomerCashTransferedDetails")
    val lstCustomerCashTransferedDetails: Any? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCashTransfer(
    @Json(name = "amount")
    val amount: String? = null,
    @Json(name = "cashBackValue")
    val cashBackValue: Int? = null,
    @Json(name = "loyaltyid")
    val loyaltyid: Any? = null,
    @Json(name = "redeemablePoints")
    val redeemablePoints: Int? = null
)

/*** Cash Transfer Submit Request ***/
@JsonClass(generateAdapter = true)
data class CashTransferSubmitRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "CustomerTypeId")
    val customerTypeId: String? = null,
    @Json(name = "PartyLoyaltyId")
    val partyLoyaltyId: String? = null,
    @Json(name = "Points")
    val points: String? = null
)

/*** Cash Transfer Submit Response ***/
@JsonClass(generateAdapter = true)
data class CashTransferSubmitResponse(
    @Json(name = "lstCashTransfer")
    val lstCashTransfer: Any? = null,
    @Json(name = "lstCustomerCashTransferedDetails")
    val lstCustomerCashTransferedDetails: Any? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null
)