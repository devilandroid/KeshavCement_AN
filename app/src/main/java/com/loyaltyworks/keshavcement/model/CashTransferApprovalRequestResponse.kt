package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Cash Transfer Approval Listing Request ***/
@JsonClass(generateAdapter = true)
data class CashTransferApprovalListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "CustomerTypeId")
    val customerTypeId: String? = null,
    @Json(name = "FromDate")
    val fromDate: String? = null,
    @Json(name = "IsTranHistory")
    val isTranHistory: String? = null,
    @Json(name = "PageSize")
    val pageSize: Int? = null,
    @Json(name = "SearchText")
    val searchText: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "Status")
    val status: String? = null,
    @Json(name = "Todate")
    val todate: String? = null
)

/*** Cash Transfer Approval Listing Response ***/
@JsonClass(generateAdapter = true)
data class CashTransferApprovalListResponse(
    @Json(name = "lstCashTransfer")
    val lstCashTransfer: Any? = null,
    @Json(name = "lstCustomerCashTransferedDetails")
    val lstCustomerCashTransferedDetails: List<LstCustomerCashTransferedDetail>? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerCashTransferedDetail(
    @Json(name = "cashTransferId")
    val cashTransferId: Int? = null,
    @Json(name = "cashTransferedStatus")
    val cashTransferedStatus: String? = null,
    @Json(name = "createdDate")
    val createdDate: String? = null,
    @Json(name = "customerMobile")
    val customerMobile: String? = null,
    @Json(name = "customerName")
    val customerName: String? = null,
    @Json(name = "customerType")
    val customerType: String? = null,
    @Json(name = "dispalyImage")
    val dispalyImage: String? = null,
    @Json(name = "locationName")
    val locationName: String? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "points")
    val points: Int? = null,
    @Json(name = "remarks")
    val remarks: String? = null,
    @Json(name = "transferedPointsinAmount")
    val transferedPointsinAmount: String? = null,
    var enteredRemarks: String = ""
)

/*** Cash Transfer Approve/Reject Request ***/
@JsonClass(generateAdapter = true)
data class CashTransferApproveRejectRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "CashTranId")
    val cashTranId: Int? = null,
    @Json(name = "LoyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "PartyLoyaltyId")
    val partyLoyaltyId: String? = null,
    @Json(name = "Remarks")
    val remarks: String? = null,
    @Json(name = "Status")
    val status: String? = null
)

/*** Cash Transfer Approve/Reject Response ***/
@JsonClass(generateAdapter = true)
data class CashTransferApproveRejectResponse(
    @Json(name = "lstCashTransfer")
    val lstCashTransfer: Any? = null,
    @Json(name = "lstCustomerCashTransferedDetails")
    val lstCustomerCashTransferedDetails: Any? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null
)