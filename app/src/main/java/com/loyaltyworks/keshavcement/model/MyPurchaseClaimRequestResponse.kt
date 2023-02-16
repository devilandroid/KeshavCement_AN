package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** My Purchase Claim List Request ***/
@JsonClass(generateAdapter = true)
data class MyPurchaseClaimRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActiveStatus")
    val activeStatus: String? = null,
    @Json(name = "FromDate")
    val fromDate: String? = null,
    @Json(name = "PageSize")
    val pageSize: Int? = null,
    @Json(name = "SalesPersonId")
    val salesPersonId: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "ToDate")
    val toDate: String? = null,
    @Json(name = "CustomerTypeId")
    val customerTypeId: String? = null
)

/*** My Purchase Claim List Response ***/
@JsonClass(generateAdapter = true)
data class MyPurchaseClaimResponse(
    @Json(name = "customerBasicInfoListJson")
    val customerBasicInfoListJson: List<CustomerBasicInfoJsonPurchaseClaim>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class CustomerBasicInfoJsonPurchaseClaim(
    @Json(name = "accountNumber")
    val accountNumber: Any? = null,
    @Json(name = "accountType")
    val accountType: Any? = null,
    @Json(name = "acountHolderName")
    val acountHolderName: Any? = null,
    @Json(name = "anniversary")
    val anniversary: Any? = null,
    @Json(name = "approOneDate")
    val approOneDate: Any? = null,
    @Json(name = "approOneName")
    val approOneName: Any? = null,
    @Json(name = "approTwoDate")
    val approTwoDate: Any? = null,
    @Json(name = "approTwoName")
    val approTwoName: Any? = null,
    @Json(name = "approvedBy")
    val approvedBy: Any? = null,
    @Json(name = "approverId")
    val approverId: Int? = null,
    @Json(name = "approverIp")
    val approverIp: Any? = null,
    @Json(name = "areaId")
    val areaId: Int? = null,
    @Json(name = "areaName")
    val areaName: Any? = null,
    @Json(name = "asmName")
    val asmName: Any? = null,
    @Json(name = "asmSeMapping")
    val asmSeMapping: Boolean? = null,
    @Json(name = "asmUserId")
    val asmUserId: Int? = null,
    @Json(name = "assessmentCertificate")
    val assessmentCertificate: Any? = null,
    @Json(name = "assessmentName")
    val assessmentName: Any? = null,
    @Json(name = "authorizedBy")
    val authorizedBy: Any? = null,
    @Json(name = "awardRewardId")
    val awardRewardId: Int? = null,
    @Json(name = "balance")
    val balance: Int? = null,
    @Json(name = "bankBranch")
    val bankBranch: Any? = null,
    @Json(name = "bankName")
    val bankName: Any? = null,
    @Json(name = "batchNo")
    val batchNo: Int? = null,
    @Json(name = "behaviourId")
    val behaviourId: Int? = null,
    @Json(name = "categoryChangeName")
    val categoryChangeName: Any? = null,
    @Json(name = "categoryName")
    val categoryName: Any? = null,
    @Json(name = "cityId")
    val cityId: Int? = null,
    @Json(name = "cityName")
    val cityName: Any? = null,
    @Json(name = "claimedRemarks")
    val claimedRemarks: Any? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: Any? = null,
    @Json(name = "createdById")
    val createdById: Int? = null,
    @Json(name = "createdByName")
    val createdByName: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "creditedPoint")
    val creditedPoint: Double? = null,
    @Json(name = "customerCategory")
    val customerCategory: Any? = null,
    @Json(name = "customerCategoryId")
    val customerCategoryId: Int? = null,
    @Json(name = "customerTicketId")
    val customerTicketId: Int? = null,
    @Json(name = "customer_Type")
    val customerType: String? = null,
    @Json(name = "customerTypeId")
    val customerTypeId: Int? = null,
    @Json(name = "dealerCode")
    val dealerCode: Any? = null,
    @Json(name = "debitedPoint")
    val debitedPoint: Double? = null,
    @Json(name = "departmentId")
    val departmentId: Int? = null,
    @Json(name = "designationId")
    val designationId: Int? = null,
    @Json(name = "districtId")
    val districtId: Int? = null,
    @Json(name = "districtName")
    val districtName: Any? = null,
    @Json(name = "dob")
    val dob: Any? = null,
    @Json(name = "editProfile")
    val editProfile: Boolean? = null,
    @Json(name = "email")
    val email: Any? = null,
    @Json(name = "enrolledDate")
    val enrolledDate: Any? = null,
    @Json(name = "fullName")
    val fullName: Any? = null,
    @Json(name = "gender")
    val gender: Any? = null,
    @Json(name = "giftAmount")
    val giftAmount: Any? = null,
    @Json(name = "ifscCode")
    val ifscCode: Any? = null,
    @Json(name = "imageUrl")
    val imageUrl: Any? = null,
    @Json(name = "invoiceNo")
    val invoiceNo: String? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "isAvailble")
    val isAvailble: Any? = null,
    @Json(name = "isCold")
    val isCold: Any? = null,
    @Json(name = "landLine")
    val landLine: Any? = null,
    @Json(name = "leadStatus")
    val leadStatus: Any? = null,
    @Json(name = "locationName")
    val locationName: Any? = null,
    @Json(name = "loyaltyTempId")
    val loyaltyTempId: Any? = null,
    @Json(name = "mappedCount")
    val mappedCount: Any? = null,
    @Json(name = "merchantID")
    val merchantID: Int? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "nationality")
    val nationality: Any? = null,
    @Json(name = "nominalPoints")
    val nominalPoints: Int? = null,
    @Json(name = "partnerID")
    val partnerID: Any? = null,
    @Json(name = "partnerName")
    val partnerName: Any? = null,
    @Json(name = "pinCode")
    val pinCode: Any? = null,
    @Json(name = "pointsBalance")
    val pointsBalance: Int? = null,
    @Json(name = "productCode")
    val productCode: Any? = null,
    @Json(name = "productDesc")
    val productDesc: Any? = null,
    @Json(name = "productName")
    val productName: String? = null,
    @Json(name = "programName")
    val programName: Any? = null,
    @Json(name = "quantity")
    val quantity: Int? = null,
    @Json(name = "redeemablePoints")
    val redeemablePoints: Int? = null,
    @Json(name = "redeemedPoints")
    val redeemedPoints: Int? = null,
    @Json(name = "redemption")
    val redemption: Boolean? = null,
    @Json(name = "referral")
    val referral: Boolean? = null,
    @Json(name = "registrationStatus")
    val registrationStatus: Any? = null,
    @Json(name = "remarks")
    val remarks: String? = null,
    @Json(name = "requestTo")
    val requestTo: String? = null,
    @Json(name = "resolveDate")
    val resolveDate: Any? = null,
    @Json(name = "retailerName")
    val retailerName: Any? = null,
    @Json(name = "rewardAdj")
    val rewardAdj: Boolean? = null,
    @Json(name = "row")
    val row: Int? = null,
    @Json(name = "salesPersonsID")
    val salesPersonsID: String? = null,
    @Json(name = "salesPersonsName")
    val salesPersonsName: String? = null,
    @Json(name = "salesRepresentative")
    val salesRepresentative: Any? = null,
    @Json(name = "salesReturn")
    val salesReturn: Int? = null,
    @Json(name = "sapID")
    val sapID: Any? = null,
    @Json(name = "seName")
    val seName: Any? = null,
    @Json(name = "seUserId")
    val seUserId: Int? = null,
    @Json(name = "sellingPrice")
    val sellingPrice: Int? = null,
    @Json(name = "sku")
    val sku: Any? = null,
    @Json(name = "smsToCustomer")
    val smsToCustomer: Boolean? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "talukId")
    val talukId: Int? = null,
    @Json(name = "talukName")
    val talukName: Any? = null,
    @Json(name = "ticketReferenceNumber")
    val ticketReferenceNumber: Any? = null,
    @Json(name = "totalRows")
    val totalRows: Int? = null,
    @Json(name = "tradLicence")
    val tradLicence: Any? = null,
    @Json(name = "transactionType")
    val transactionType: Any? = null,
    @Json(name = "trxnDate")
    val trxnDate: String? = null,
    @Json(name = "userOneHeader")
    val userOneHeader: Any? = null,
    @Json(name = "userTwoHeader")
    val userTwoHeader: Any? = null,
    @Json(name = "vehicleManagement")
    val vehicleManagement: Boolean? = null,
    @Json(name = "verified")
    val verified: Int? = null,
    @Json(name = "verifiedName")
    val verifiedName: Any? = null,
    @Json(name = "verifiedTypeId")
    val verifiedTypeId: Int? = null,
    @Json(name = "walletNumber")
    val walletNumber: Any? = null,
    @Json(name = "whatsAppMobileNumber")
    val whatsAppMobileNumber: Any? = null,
    @Json(name = "workSiteAddress")
    val workSiteAddress: Any? = null,
    @Json(name = "workSiteCityName")
    val workSiteCityName: Any? = null,
    @Json(name = "workSiteContactNumber")
    val workSiteContactNumber: Any? = null,
    @Json(name = "workSiteContactPersonName")
    val workSiteContactPersonName: Any? = null,
    @Json(name = "workSiteInfoID")
    val workSiteInfoID: Int? = null,
    @Json(name = "workSiteLandMark")
    val workSiteLandMark: Any? = null,
    @Json(name = "workSitePincode")
    val workSitePincode: Any? = null,
    @Json(name = "workSiteVerification")
    val workSiteVerification: Int? = null,
    @Json(name = "workSiteVerificationStatus")
    val workSiteVerificationStatus: Any? = null,
    @Json(name = "approvedQuantity")
    val approvedQuantity: Int? = null
)