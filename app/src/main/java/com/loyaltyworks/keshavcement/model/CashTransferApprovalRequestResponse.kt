package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Cash Transfer Approval Approve/Reject Request ***/
@JsonClass(generateAdapter = true)
data class CashTransferApproveRejectRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "Domain")
    val domain: String? = null,
    @Json(name = "ObjCatalogueDetails")
    val objCatalogueDetails: ObjCatalogueDetailsCashTransferApproval? = null,
    @Json(name = "SearchText")
    val searchText: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailsCashTransferApproval(
    @Json(name = "MemberName")
    val memberName: String? = null,
    @Json(name = "RedemptionId")
    val redemptionId: Int? = null,
    @Json(name = "Status")
    val status: String? = null
)

/*** Cash Transfer Approval Approve/Reject Response ***/
@JsonClass(generateAdapter = true)
data class CashTransferApproveRejectResponse(
    @Json(name = "catalogueImageGallery")
    val catalogueImageGallery: Any? = null,
    @Json(name = "locationCites")
    val locationCites: Any? = null,
    @Json(name = "lstCatalogueProductAvailableCity")
    val lstCatalogueProductAvailableCity: Any? = null,
    @Json(name = "objCatalogueCategoryList")
    val objCatalogueCategoryList: Any? = null,
    @Json(name = "objCatalogueFixedPoints")
    val objCatalogueFixedPoints: Any? = null,
    @Json(name = "objCatalogueList")
    val objCatalogueList: Any? = null,
    @Json(name = "objCatalogueRedemReqList")
    val objCatalogueRedemReqList: Any? = null,
    @Json(name = "objCustShippingAddressDetails")
    val objCustShippingAddressDetails: Any? = null,
    @Json(name = "objDreamProduct")
    val objDreamProduct: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)