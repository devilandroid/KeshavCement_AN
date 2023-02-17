package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/* My Earning Request */

@JsonClass(generateAdapter = true)
data class MyEarningRequest(
    @Json(name = "ActorId")
    var actorId: Int? = null,
    @Json(name = "BehaviorId")
    var behaviorId: Int? = null,
    @Json(name = "JFromDate")
    var jFromDate: String? = null,
    @Json(name = "JToDate")
    var jToDate: String? = null,
    @Json(name = "StartIndex")
    var startIndex: Int? = null,
    @Json(name = "PageSize")
    var pageSize: Int? = null
)

/* My Earning Response */
@JsonClass(generateAdapter = true)
data class MyEarningResponse(
    @Json(name = "lstRewardTransDetails")
    var lstRewardTransDetails: Any? = null,
    @Json(name = "lstRewardTransJsonDetails")
    var lstRewardTransJsonDetails: List<LstRewardTransJsonDetail>? = null,
    @Json(name = "lstRewardTransactionBasedonProduct")
    var lstRewardTransactionBasedonProduct: List<LstRewardTransactionBasedonProduct>? = null,
    @Json(name = "pdf")
    var pdf: Any? = null,
    @Json(name = "returnMessage")
    var returnMessage: Any? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null,
    @Json(name = "totalRewardPoints")
    var totalRewardPoints: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstRewardTransJsonDetail(
    @Json(name = "amount")
    var amount: Double? = null,
    @Json(name = "behaviorId")
    var behaviorId: Int? = null,
    @Json(name = "behaviourName")
    var behaviourName: String? = null,
    @Json(name = "bonusName")
    var bonusName: String? = null,
    @Json(name = "brandName")
    var brandName: Any? = null,
    @Json(name = "cashierName")
    var cashierName: Any? = null,
    @Json(name = "categoryName")
    var categoryName: String? = null,
    @Json(name = "claimApproveDate")
    var claimApproveDate: String? = null,
    @Json(name = "createdBy")
    var createdBy: Any? = null,
    @Json(name = "currencyName")
    var currencyName: String? = null,
    @Json(name = "expairyOnDate")
    var expairyOnDate: Any? = null,
    @Json(name = "invoiceNo")
    var invoiceNo: String? = null,
    @Json(name = "isNotionalId")
    var isNotionalId: Int? = null,
    @Json(name = "jTranDate")
    var jTranDate: String? = null,
    @Json(name = "locationID")
    var locationID: Int? = null,
    @Json(name = "locationName")
    var locationName: String? = null,
    @Json(name = "loyaltyId")
    var loyaltyId: String? = null,
    @Json(name = "ltyBehaviourId")
    var ltyBehaviourId: Int? = null,
    @Json(name = "memberName")
    var memberName: Any? = null,
    @Json(name = "merchantID")
    var merchantID: Int? = null,
    @Json(name = "merchantName")
    var merchantName: String? = null,
    @Json(name = "partyName")
    var partyName: Any? = null,
    @Json(name = "pointBalance")
    var pointBalance: Int? = null,
    @Json(name = "pointsGiftedBy")
    var pointsGiftedBy: String? = null,
    @Json(name = "processDateTime")
    var processDateTime: Any? = null,
    @Json(name = "prodCode")
    var prodCode: String? = null,
    @Json(name = "prodName")
    var prodName: String? = null,
    @Json(name = "quantity")
    var quantity: Int? = null,
    @Json(name = "remarks")
    var remarks: String? = null,
    @Json(name = "rewardPoints")
    var rewardPoints: Double? = null,
    @Json(name = "serialNumber")
    var serialNumber: String? = null,
    @Json(name = "skuName")
    var skuName: String? = null,
    @Json(name = "totalRows")
    var totalRows: Int? = null,
    @Json(name = "tranDate")
    var tranDate: Any? = null,
    @Json(name = "transactionType")
    var transactionType: String? = null,
    @Json(name = "vendorName")
    var vendorName: Any? = null
)

@JsonClass(generateAdapter = true)
data class LstRewardTransactionBasedonProduct(
    @Json(name = "amount")
    var amount: Double? = null,
    @Json(name = "discount")
    var discount: Int? = null,
    @Json(name = "invoiceNo")
    var invoiceNo: String? = null,
    @Json(name = "qty")
    var qty: Double? = null,
    @Json(name = "rewardPoints")
    var rewardPoints: Double? = null,
    @Json(name = "serialNumber")
    var serialNumber: String? = null
)


/*** Status List Request ***/
@JsonClass(generateAdapter = true)
data class StatusListRequest(
    @Json(name = "ActionType")
    var actionType: Int? = null
)

/*** Status list response ***/
@JsonClass(generateAdapter = true)
data class StatusListResponse(
    @Json(name = "actionType")
    var actionType: Int? = null,
    @Json(name = "lstAttributesDetails")
    var lstAttributesDetails: List<LstAttributesDetailll>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetailll(
    @Json(name = "attributeContents")
    var attributeContents: Any? = null,
    @Json(name = "attributeCurrencyId")
    var attributeCurrencyId: Any? = null,
    @Json(name = "attributeId")
    var attributeId: Int? = null,
    @Json(name = "attributeNames")
    var attributeNames: Any? = null,
    @Json(name = "attributeType")
    var attributeType: String? = null,
    @Json(name = "attributeValue")
    var attributeValue: Any? = null
)