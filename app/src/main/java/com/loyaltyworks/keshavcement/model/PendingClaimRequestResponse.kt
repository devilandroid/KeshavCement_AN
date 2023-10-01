package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

/*** Pending Claim List Request ***/
@JsonClass(generateAdapter = true)
data class PendingClaimListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "ApprovalStatusID")
    val approvalStatusID: Int? = null,
    @Json(name = "CustomerTypeId")
    val customerTypeId: Int? = null,
    @Json(name = "NoOfRows")
    val noOfRows: Int? = null,
    @Json(name = "SearchText")
    val searchText: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null
)

/*** Pending Claim List Response ***/
@JsonClass(generateAdapter = true)
data class PendingClaimListResponse(
    @Json(name = "lstCustOrderDeliveryDetails")
    val lstCustOrderDeliveryDetails: Any? = null,
    @Json(name = "lstTransactionApprovalDetails")
    val lstTransactionApprovalDetails: List<LstTransactionApprovalDetail>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "rewardPoints")
    val rewardPoints: Double? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstTransactionApprovalDetail(
    @Json(name = "amount")
    val amount: Double? = null,
    @Json(name = "approvalStatus")
    val approvalStatus: Any? = null,
    @Json(name = "approvalStatusID")
    val approvalStatusID: Int? = null,
    @Json(name = "approvedByMemberName")
    val approvedByMemberName: Any? = null,
    @Json(name = "approvedByMemberType")
    val approvedByMemberType: Any? = null,
    @Json(name = "approvedDate")
    val approvedDate: Any? = null,
    @Json(name = "approverIP")
    val approverIP: Any? = null,
    @Json(name = "approverMemberId")
    val approverMemberId: Any? = null,
    @Json(name = "approverMemberName")
    val approverMemberName: Any? = null,
    @Json(name = "approverMemberType")
    val approverMemberType: Any? = null,
    @Json(name = "approverName")
    val approverName: Any? = null,
    @Json(name = "batchNo")
    val batchNo: Int? = null,
    @Json(name = "color")
    val color: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "dealerLocation")
    val dealerLocation: Any? = null,
    @Json(name = "email")
    val email: Any? = null,
    @Json(name = "escalationDate")
    val escalationDate: Any? = null,
    @Json(name = "escalationMemberId")
    val escalationMemberId: Any? = null,
    @Json(name = "escalationMemberName")
    val escalationMemberName: Any? = null,
    @Json(name = "escalationMemberType")
    val escalationMemberType: Any? = null,
    @Json(name = "gstNumber")
    val gstNumber: Any? = null,
    @Json(name = "invoiceAttachment")
    val invoiceAttachment: Any? = null,
    @Json(name = "invoiceNo")
    val invoiceNo: String? = null,
    @Json(name = "isColor")
    val isColor: Int? = null,
    @Json(name = "isReversalStatus")
    val isReversalStatus: Int? = null,
    @Json(name = "isVisible")
    val isVisible: Int? = null,
    @Json(name = "locationID")
    val locationID: Int? = null,
    @Json(name = "locationName")
    val locationName: String? = null,
    @Json(name = "loyaltyBehaviour")
    val loyaltyBehaviour: Any? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "ltyTranTempID")
    val ltyTranTempID: Int? = null,
    @Json(name = "memberName")
    val memberName: String? = null,
    @Json(name = "memberType")
    val memberType: String? = null,
    @Json(name = "merchantApprovedStatus")
    val merchantApprovedStatus: Any? = null,
    @Json(name = "merchantID")
    val merchantID: Int? = null,
    @Json(name = "merchantName")
    val merchantName: Any? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "ordreId")
    val ordreId: Int? = null,
    @Json(name = "ordreNumber")
    val ordreNumber: Any? = null,
    @Json(name = "partyLoyaltyId")
    val partyLoyaltyId: Any? = null,
    @Json(name = "partyName")
    val partyName: String? = null,
    @Json(name = "plumberName")
    val plumberName: Any? = null,
    @Json(name = "pointBalance")
    val pointBalance: Any? = null,
    @Json(name = "pointsCredited")
    val pointsCredited: Int? = null,
    @Json(name = "prodCode")
    val prodCode: String? = null,
    @Json(name = "prodDescription")
    val prodDescription: Any? = null,
    @Json(name = "prodName")
    val prodName: String? = null,
    @Json(name = "prodVideoLink")
    val prodVideoLink: Any? = null,
    @Json(name = "productId")
    val productId: Int? = null,
    @Json(name = "productImage")
    val productImage: String? = null,
    @Json(name = "qty")
    val qty: Int? = null,
    @Json(name = "quantity")
    val quantity: Int? = null,
    @Json(name = "remarks")
    val remarks: String? = null,
    @Json(name = "retailerApprovedDate")
    val retailerApprovedDate: Any? = null,
    @Json(name = "retailerApprovedStatus")
    val retailerApprovedStatus: Any? = null,
    @Json(name = "retailerFirmName")
    val retailerFirmName: Any? = null,
    @Json(name = "retailerID")
    val retailerID: Any? = null,
    @Json(name = "retailerLocation")
    val retailerLocation: Any? = null,
    @Json(name = "retailerName")
    val retailerName: Any? = null,
    @Json(name = "retailerType")
    val retailerType: Any? = null,
    @Json(name = "rewardPoints")
    val rewardPoints: Double? = null,
    @Json(name = "seApprovedDate")
    val seApprovedDate: Any? = null,
    @Json(name = "seApprovedStatus")
    val seApprovedStatus: Any? = null,
    @Json(name = "seID")
    val seID: Any? = null,
    @Json(name = "seLocation")
    val seLocation: Any? = null,
    @Json(name = "seType")
    val seType: Any? = null,
    @Json(name = "sellingPrice")
    val sellingPrice: Any? = null,
    @Json(name = "serialNumber")
    val serialNumber: Any? = null,
    @Json(name = "shopName")
    val shopName: Any? = null,
    @Json(name = "skuName")
    val skuName: Any? = null,
    @Json(name = "sourceDevice")
    val sourceDevice: Any? = null,
    @Json(name = "totalEarnPoint")
    val totalEarnPoint: Int? = null,
    @Json(name = "totalRows")
    val totalRows: Int? = null,
    @Json(name = "tranDate")
    val tranDate: String? = null,
    @Json(name = "transactionType")
    val transactionType: Any? = null,
    @Json(name = "uom")
    val uom: Any? = null,
    @Json(name = "userRole")
    val userRole: Any? = null,
    @Json(name = "userRoleID")
    val userRoleID: Int? = null,
    @Json(name = "userType")
    val userType: Any? = null,
    var updatedQuantity: Int = 0,
    var enteredRemarks: String = ""
)

/*** Pending Claim Approve/Reject Request ***/
@JsonClass(generateAdapter = true)
data class PendingClaimApproveRejectRequest(
    @Json(name = "ActorId")
    val actorId: Int? = null,
    @Json(name = "ApprovalRemarks")
    val approvalRemarks: String? = null,
    @Json(name = "ApprovalStatusID")
    val approvalStatusID: String? = null,
    @Json(name = "lstTransactionApprovals")
    val lstTransactionApprovals: List<LstTransactionApproval>? = null
)

@JsonClass(generateAdapter = true)
data class LstTransactionApproval(
    @Json(name = "LtyTranTempID")
    val ltyTranTempID: Int? = null,
    @Json(name = "Quantity")
    val quantity: Int? = null
)

/*** Pending Claim Approve/Reject Response ***/
@JsonClass(generateAdapter = true)
data class PendingClaimApproveRejectResponse(
    @Json(name = "lstCustOrderDeliveryDetails")
    val lstCustOrderDeliveryDetails: Any? = null,
    @Json(name = "lstTransactionApprovalDetails")
    val lstTransactionApprovalDetails: List<Any?>? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "rewardPoints")
    val rewardPoints: Double? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)