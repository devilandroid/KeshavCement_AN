package com.loyaltyworks.keshavcement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

/*My Redemption Request */

@JsonClass(generateAdapter = true)
data class MyRedemptionRequest(
    @Json(name = "ActionType")
    var actionType: String? = null,
    @Json(name = "ActorId")
    var actorId: String? = null,
    @Json(name = "PartyLoyaltyID")
    var partyLoyaltyID: String? = null,
    @Json(name = "StartIndex")
    var startIndex: Int? = null,
    @Json(name = "NoOfRows")
    var noOfRows: Int? = null,
    @Json(name = "CustomerTypeID")
    var customerTypeID: String? = null,
    @Json(name = "SearchText")
    var searchText: String? = null,
    @Json(name = "Domain")
    var domain: String? = null,
    @Json(name = "ObjCatalogueDetails")
    var objCatalogueDetails: ObjCatalogueDetails? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetails(
    @Json(name = "JFromDate")
    var jFromDate: String? = null,
    @Json(name = "JToDate")
    var jToDate: String? = null,
    @Json(name = "SelectedStatus")
    var selectedStatus: String? = null,
    @Json(name = "RedemptionTypeId")
    var redemptionTypeId: Int? = null,
    @Json(name = "RedemptionId")
    val redemptionId: String? = null,
    @Json(name = "CatogoryId")
    var catogoryId: String? = null
)

/* My Redemption Response */

@JsonClass(generateAdapter = true)
data class MyRedemptionResponse(
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
    val objCatalogueRedemReqList: List<ObjCatalogueRedemReq>? = null,
    @Json(name = "objCustShippingAddressDetails")
    val objCustShippingAddressDetails: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueRedemReq(
    @Json(name = "ASM")
    val aSM: Any? = null,
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "address1")
    val address1: String? = null,
    @Json(name = "address2")
    val address2: Any? = null,
    @Json(name = "addressId")
    val addressId: Int? = null,
    @Json(name = "addressType")
    val addressType: String? = null,
    @Json(name = "balance")
    val balance: Any? = null,
    @Json(name = "barcode")
    val barcode: Any? = null,
    @Json(name = "beneficiaryAccount")
    val beneficiaryAccount: Any? = null,
    @Json(name = "beneficiaryIFSC")
    val beneficiaryIFSC: Any? = null,
    @Json(name = "beneficiaryName")
    val beneficiaryName: Any? = null,
    @Json(name = "cashPerUnit")
    val cashPerUnit: Int? = null,
    @Json(name = "cashValue")
    val cashValue: Int? = null,
    @Json(name = "catalogueId")
    val catalogueId: Int? = null,
    @Json(name = "catalogueType")
    val catalogueType: String? = null,
    @Json(name = "categoryName")
    val categoryName: Any? = null,
    @Json(name = "cityId")
    val cityId: Int? = null,
    @Json(name = "cityName")
    val cityName: String? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: String? = null,
    @Json(name = "createdBy")
    val createdBy: String? = null,
    @Json(name = "custMobile")
    val custMobile: String? = null,
    @Json(name = "deliveryType")
    val deliveryType: Int? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "expiryDate")
    val expiryDate: Any? = null,
    @Json(name = "fullName")
    val fullName: String? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "jRedemptionDate")
    val jRedemptionDate: String? = null,
    @Json(name = "landmark")
    val landmark: Any? = null,
    @Json(name = "locationName")
    val locationName: String? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "merchantEmail")
    val merchantEmail: Any? = null,
    @Json(name = "merchantName")
    val merchantName: String? = null,
    @Json(name = "mobile")
    val mobile: String? = null,
    @Json(name = "name")
    val name: Any? = null,
    @Json(name = "pdfLink")
    val pDFLink: Any? = null,
    @Json(name = "partialPaymentCash")
    val partialPaymentCash: Int? = null,
    @Json(name = "pendingVoucherBalance")
    val pendingVoucherBalance: Double? = null,
    @Json(name = "pointsPerUnit")
    val pointsPerUnit: Int? = null,
    @Json(name = "pointsRequired")
    val pointsRequired: Int? = null,
    @Json(name = "processedBy")
    val processedBy: String? = null,
    @Json(name = "productCode")
    val productCode: String? = null,
    @Json(name = "productDesc")
    val productDesc: String? = null,
    @Json(name = "productImage")
    val productImage: String? = null,
    @Json(name = "productName")
    val productName: String? = null,
    @Json(name = "quantity")
    val quantity: Int? = null,
    @Json(name = "redeemedPoints")
    val redeemedPoints: Int? = null,
    @Json(name = "redemptionDate")
    val redemptionDate: Any? = null,
    @Json(name = "redemptionId")
    val redemptionId: Int? = null,
    @Json(name = "redemptionPoints")
    val redemptionPoints: Int? = null,
    @Json(name = "redemptionRefno")
    val redemptionRefno: String? = null,
    @Json(name = "redemptionStatus")
    val redemptionStatus: Int? = null,
    @Json(name = "redemptionType")
    val redemptionType: Int? = null,
    @Json(name = "referrenceCustName")
    val referrenceCustName: Any? = null,
    @Json(name = "se")
    val sE: Any? = null,
    @Json(name = "sapCode")
    val sapCode: Any? = null,
    @Json(name = "sku")
    val sku: Any? = null,
    @Json(name = "stateId")
    val stateId: Int? = null,
    @Json(name = "stateName")
    val stateName: String? = null,
    @Json(name = "status")
    val status: Int? = null,
    @Json(name = "termsCondition")
    val termsCondition: String? = null,
    @Json(name = "totRowCount")
    val totRowCount: Int? = null,
    @Json(name = "transferMode")
    val transferMode: Any? = null,
    @Json(name = "vendorCode")
    val vendorCode: Any? = null,
    @Json(name = "vendorId")
    val vendorId: Int? = null,
    @Json(name = "vendorName")
    val vendorName: String? = null,
    @Json(name = "walletNumber")
    val walletNumber: Any? = null,
    @Json(name = "zip")
    val zip: String? = null,
    @Json(name = "remarks")
    val remarks: String? = null,
    @Json(name = "membertype")
    val membertype: String? = null,
    @Json(name = "districtName")
    val districtName: String? = null,
    @Json(name = "cashTransferedTo")
    val cashTransferedTo: String? = null,
    @Json(name = "cashTransferedInAmount")
    val cashTransferedInAmount: String? = null,
    @Json(name = "cashTransferedPoints")
    val cashTransferedPoints: String? = null,
    @Json(name = "cashTransferedStatus")
    val cashTransferedStatus: String? = null,
    var enteredRemarks: String = ""
): Serializable

  /* My Redemption Details Request */

@JsonClass(generateAdapter = true)
data class MyRedemptionDetailsRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ObjCatalogueDetails")
    val objCatalogueDetails: ObjCatalogueDetails? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetail(
    @Json(name = "RedemptionId")
    val redemptionId: String? = null
)

/* My Redemption Details Response */

@JsonClass(generateAdapter = true)
data class MyRedemptionDetailsResponse(
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
    val objCatalogueList: List<ObjCatalogue>? = null,
    @Json(name = "objCatalogueRedemReqList")
    val objCatalogueRedemReqList: Any? = null,
    @Json(name = "objCustShippingAddressDetails")
    val objCustShippingAddressDetails: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogue(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "activeStatus")
    val activeStatus: Boolean? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "actualRedemptionDate")
    val actualRedemptionDate: Any? = null,
    @Json(name = "additionalRemarks")
    val additionalRemarks: Any? = null,
    @Json(name = "approverName")
    val approverName: Any? = null,
    @Json(name = "averageEarning")
    val averageEarning: Any? = null,
    @Json(name = "avgExpDate")
    val avgExpDate: Any? = null,
    @Json(name = "avgGreaterExpDate")
    val avgGreaterExpDate: Any? = null,
    @Json(name = "avgLesserExpDate")
    val avgLesserExpDate: Any? = null,
    @Json(name = "barcode")
    val barcode: Any? = null,
    @Json(name = "brandTermsAndConditions")
    val brandTermsAndConditions: Any? = null,
    @Json(name = "cashPerUnit")
    val cashPerUnit: Int? = null,
    @Json(name = "cashValue")
    val cashValue: Int? = null,
    @Json(name = "catalogueBrandCode")
    val catalogueBrandCode: Any? = null,
    @Json(name = "catalogueBrandDesc")
    val catalogueBrandDesc: Any? = null,
    @Json(name = "catalogueBrandId")
    val catalogueBrandId: Int? = null,
    @Json(name = "catalogueBrandName")
    val catalogueBrandName: Any? = null,
    @Json(name = "catalogueId")
    val catalogueId: Int? = null,
    @Json(name = "catalogueType")
    val catalogueType: Int? = null,
    @Json(name = "catalougeBrandName")
    val catalougeBrandName: Any? = null,
    @Json(name = "categoryID")
    val categoryID: Int? = null,
    @Json(name = "categoryParentID")
    val categoryParentID: Int? = null,
    @Json(name = "catogoryId")
    val catogoryId: Int? = null,
    @Json(name = "catogoryImage")
    val catogoryImage: Any? = null,
    @Json(name = "catogoryName")
    val catogoryName: Any? = null,
    @Json(name = "color_Code")
    val colorCode: Any? = null,
    @Json(name = "color_Id")
    val colorId: Int? = null,
    @Json(name = "color_Name")
    val colorName: Any? = null,
    @Json(name = "commandName")
    val commandName: Any? = null,
    @Json(name = "countryCurrencyCode")
    val countryCurrencyCode: Any? = null,
    @Json(name = "countryID")
    val countryID: Int? = null,
    @Json(name = "createdBy")
    val createdBy: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "dailyAvgCash")
    val dailyAvgCash: Any? = null,
    @Json(name = "deliveryType")
    val deliveryType: Any? = null,
    @Json(name = "dreamGiftId")
    val dreamGiftId: Int? = null,
    @Json(name = "expectedDelivery")
    val expectedDelivery: Any? = null,
    @Json(name = "expiryDate")
    val expiryDate: Any? = null,
    @Json(name = "expiryOn")
    val expiryOn: Int? = null,
    @Json(name = "fromDate")
    val fromDate: Any? = null,
    @Json(name = "greaterAvgCash")
    val greaterAvgCash: Any? = null,
    @Json(name = "hasPartialPayment")
    val hasPartialPayment: Boolean? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "isApproved")
    val isApproved: Boolean? = null,
    @Json(name = "isCash")
    val isCash: Boolean? = null,
    @Json(name = "isPlanner")
    val isPlanner: Boolean? = null,
    @Json(name = "isPopularCount")
    val isPopularCount: Int? = null,
    @Json(name = "jFromDate")
    val jFromDate: Any? = null,
    @Json(name = "jRedemptionDate")
    val jRedemptionDate: String? = null,
    @Json(name = "jToDate")
    val jToDate: Any? = null,
    @Json(name = "lesserAvgCash")
    val lesserAvgCash: Any? = null,
    @Json(name = "locationId")
    val locationId: Int? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "msqa")
    val mSQA: Int? = null,
    @Json(name = "max_points")
    val maxPoints: Any? = null,
    @Json(name = "memberName")
    val memberName: Any? = null,
    @Json(name = "merchantId")
    val merchantId: Int? = null,
    @Json(name = "merchantName")
    val merchantName: String? = null,
    @Json(name = "min_points")
    val minPoints: Any? = null,
    @Json(name = "minimumStockQunty")
    val minimumStockQunty: Int? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "modelId")
    val modelId: Int? = null,
    @Json(name = "modelName")
    val modelName: Any? = null,
    @Json(name = "multipleRedIds")
    val multipleRedIds: Any? = null,
    @Json(name = "noOfPointsDebit")
    val noOfPointsDebit: Int? = null,
    @Json(name = "noOfQuantity")
    val noOfQuantity: Int? = null,
    @Json(name = "partialPaymentCash")
    val partialPaymentCash: Int? = null,
    @Json(name = "plannerStatus")
    val plannerStatus: Any? = null,
    @Json(name = "pointBalance")
    val pointBalance: Int? = null,
    @Json(name = "pointRedem")
    val pointRedem: Int? = null,
    @Json(name = "pointReqToAcheiveProduct")
    val pointReqToAcheiveProduct: Int? = null,
    @Json(name = "pointsPerUnit")
    val pointsPerUnit: Int? = null,
    @Json(name = "pointsRequired")
    val pointsRequired: Int? = null,
    @Json(name = "productCode")
    val productCode: String? = null,
    @Json(name = "productDesc")
    val productDesc: Any? = null,
    @Json(name = "productImage")
    val productImage: String? = null,
    @Json(name = "productImageServerPath")
    val productImageServerPath: Any? = null,
    @Json(name = "productName")
    val productName: String? = null,
    @Json(name = "product_type")
    val productType: Int? = null,
    @Json(name = "redeemableAverageEarning")
    val redeemableAverageEarning: Any? = null,
    @Json(name = "redeemableAverageEarning12")
    val redeemableAverageEarning12: Int? = null,
    @Json(name = "redeemableAverageEarning6")
    val redeemableAverageEarning6: Int? = null,
    @Json(name = "redeemableEncashBalance")
    val redeemableEncashBalance: Int? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "redemptionDate")
    val redemptionDate: Any? = null,
    @Json(name = "redemptionId")
    val redemptionId: Int? = null,
    @Json(name = "redemptionPlannerId")
    val redemptionPlannerId: Int? = null,
    @Json(name = "redemptionRefno")
    val redemptionRefno: String? = null,
    @Json(name = "redemptionStatus")
    val redemptionStatus: Any? = null,
    @Json(name = "redemptionTypeId")
    val redemptionTypeId: Int? = null,
    @Json(name = "segmentDetails")
    val segmentDetails: Any? = null,
    @Json(name = "selectedStatus")
    val selectedStatus: Int? = null,
    @Json(name = "status")
    val status: Int? = null,
    @Json(name = "subCategoryID")
    val subCategoryID: Int? = null,
    @Json(name = "subCategoryName")
    val subCategoryName: Any? = null,
    @Json(name = "termsCondition")
    val termsCondition: Any? = null,
    @Json(name = "toDate")
    val toDate: Any? = null,
    @Json(name = "totalCash")
    val totalCash: Int? = null,
    @Json(name = "total_Row")
    val totalRow: Int? = null,
    @Json(name = "userAccess")
    val userAccess: Int? = null,
    @Json(name = "vendorId")
    val vendorId: Int? = null,
    @Json(name = "vendorName")
    val vendorName: String? = null,
    @Json(name = "redeemedCatalogueType")
    val redeemedCatalogueType: String? = null
)


// common data class use in selected product list

data class CommonStatusSpinner(
    var productName: String? = null,
    var id: Int? = null,
    var productCode: String? = null,
    var ltyTranTempId: String? = null,
    var productPoint: String? = null
)



/*Customer Redemption History details*/

@JsonClass(generateAdapter = true)
data class RedemptionHistoryRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ObjCatalogueDetails")
    val objCatalogueDetails: ObjCatalogueDetailsss? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailsss(
    @Json(name = "RedemptionRefno")
    val redemptionRefno: String? = null
)

@JsonClass(generateAdapter = true)
data class RedemptionHistoryResponse(
    @Json(name = "catalogueImageGallery")
    val catalogueImageGallery: Any? = null,
    @Json(name = "locationCites")
    val locationCites: Any? = null,
    @Json(name = "lstCatalogueProductAvailableCity")
    val lstCatalogueProductAvailableCity: Any? = null,
    @Json(name = "lstShippingAddressDetails")
    val lstShippingAddressDetails: Any? = null,
    @Json(name = "objCatalogueCategoryList")
    val objCatalogueCategoryList: Any? = null,
    @Json(name = "objCatalogueFixedPoints")
    val objCatalogueFixedPoints: Any? = null,
    @Json(name = "objCatalogueList")
    val objCatalogueList: List<ObjCatalogueee>? = null,
    @Json(name = "objCatalogueRedemReqList")
    val objCatalogueRedemReqList: Any? = null,
    @Json(name = "objCustShippingAddressDetails")
    val objCustShippingAddressDetails: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueee(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "activeStatus")
    val activeStatus: Boolean? = null,
    @Json(name = "actorId")
    val actorId: Int? = null,
    @Json(name = "actorRole")
    val actorRole: Any? = null,
    @Json(name = "actualRedemptionDate")
    val actualRedemptionDate: Any? = null,
    @Json(name = "additionalRemarks")
    val additionalRemarks: Any? = null,
    @Json(name = "annualGiftCount")
    val annualGiftCount: Int? = null,
    @Json(name = "annualVoucherCount")
    val annualVoucherCount: Int? = null,
    @Json(name = "approverName")
    val approverName: Any? = null,
    @Json(name = "averageEarning")
    val averageEarning: Any? = null,
    @Json(name = "avgExpDate")
    val avgExpDate: Any? = null,
    @Json(name = "avgGreaterExpDate")
    val avgGreaterExpDate: Any? = null,
    @Json(name = "avgLesserExpDate")
    val avgLesserExpDate: Any? = null,
    @Json(name = "barcode")
    val barcode: Any? = null,
    @Json(name = "brandTermsAndConditions")
    val brandTermsAndConditions: Any? = null,
    @Json(name = "cashPerUnit")
    val cashPerUnit: Int? = null,
    @Json(name = "cashValue")
    val cashValue: Int? = null,
    @Json(name = "catalogueBrandCode")
    val catalogueBrandCode: Any? = null,
    @Json(name = "catalogueBrandDesc")
    val catalogueBrandDesc: Any? = null,
    @Json(name = "catalogueBrandId")
    val catalogueBrandId: Int? = null,
    @Json(name = "catalogueBrandName")
    val catalogueBrandName: Any? = null,
    @Json(name = "catalogueId")
    val catalogueId: Int? = null,
    @Json(name = "catalogueType")
    val catalogueType: Int? = null,
    @Json(name = "catalougeBrandName")
    val catalougeBrandName: Any? = null,
    @Json(name = "categoryID")
    val categoryID: Int? = null,
    @Json(name = "categoryParentID")
    val categoryParentID: Int? = null,
    @Json(name = "catogoryId")
    val catogoryId: Int? = null,
    @Json(name = "catogoryImage")
    val catogoryImage: Any? = null,
    @Json(name = "catogoryName")
    val catogoryName: Any? = null,
    @Json(name = "color_Code")
    val colorCode: Any? = null,
    @Json(name = "color_Id")
    val colorId: Int? = null,
    @Json(name = "color_Name")
    val colorName: Any? = null,
    @Json(name = "commandName")
    val commandName: Any? = null,
    @Json(name = "countryCurrencyCode")
    val countryCurrencyCode: Any? = null,
    @Json(name = "countryID")
    val countryID: Int? = null,
    @Json(name = "createdBy")
    val createdBy: Any? = null,
    @Json(name = "createdDate")
    val createdDate: String? = null,
    @Json(name = "custMobile")
    val custMobile: Any? = null,
    @Json(name = "customerCartId")
    val customerCartId: Int? = null,
    @Json(name = "dailyAvgCash")
    val dailyAvgCash: Any? = null,
    @Json(name = "dailyGiftCount")
    val dailyGiftCount: Int? = null,
    @Json(name = "dailyVoucherCount")
    val dailyVoucherCount: Int? = null,
    @Json(name = "deliveryType")
    val deliveryType: Any? = null,
    @Json(name = "dreamGiftId")
    val dreamGiftId: Int? = null,
    @Json(name = "expectedDelivery")
    val expectedDelivery: Any? = null,
    @Json(name = "expiryDate")
    val expiryDate: Any? = null,
    @Json(name = "expiryOn")
    val expiryOn: Int? = null,
    @Json(name = "fromDate")
    val fromDate: String? = null,
    @Json(name = "greaterAvgCash")
    val greaterAvgCash: Any? = null,
    @Json(name = "hasPartialPayment")
    val hasPartialPayment: Boolean? = null,
    @Json(name = "isActive")
    val isActive: Boolean? = null,
    @Json(name = "isApproved")
    val isApproved: Boolean? = null,
    @Json(name = "isCash")
    val isCash: Boolean? = null,
    @Json(name = "isPlanner")
    val isPlanner: Boolean? = null,
    @Json(name = "isPopularCount")
    val isPopularCount: Int? = null,
    @Json(name = "jFromDate")
    val jFromDate: Any? = null,
    @Json(name = "jRedemptionDate")
    val jRedemptionDate: Any? = null,
    @Json(name = "jToDate")
    val jToDate: Any? = null,
    @Json(name = "lesserAvgCash")
    val lesserAvgCash: Any? = null,
    @Json(name = "locationId")
    val locationId: Int? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: Any? = null,
    @Json(name = "max_points")
    val maxPoints: Any? = null,
    @Json(name = "memberName")
    val memberName: Any? = null,
    @Json(name = "merchantId")
    val merchantId: Int? = null,
    @Json(name = "merchantName")
    val merchantName: Any? = null,
    @Json(name = "min_points")
    val minPoints: Any? = null,
    @Json(name = "minimumStockQunty")
    val minimumStockQunty: Int? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "modelId")
    val modelId: Int? = null,
    @Json(name = "modelName")
    val modelName: Any? = null,
    @Json(name = "mrp")
    val mrp: Any? = null,
    @Json(name = "msqa")
    val msqa: Int? = null,
    @Json(name = "multipleRedIds")
    val multipleRedIds: Any? = null,
    @Json(name = "noOfPointsDebit")
    val noOfPointsDebit: Int? = null,
    @Json(name = "noOfQuantity")
    val noOfQuantity: Int? = null,
    @Json(name = "parentSubLocationID")
    val parentSubLocationID: Int? = null,
    @Json(name = "partialPaymentCash")
    val partialPaymentCash: Int? = null,
    @Json(name = "plannerStatus")
    val plannerStatus: Any? = null,
    @Json(name = "pointBalance")
    val pointBalance: Int? = null,
    @Json(name = "pointRedem")
    val pointRedem: Int? = null,
    @Json(name = "pointReqToAcheiveProduct")
    val pointReqToAcheiveProduct: Int? = null,
    @Json(name = "pointsPerUnit")
    val pointsPerUnit: Int? = null,
    @Json(name = "pointsRequired")
    val pointsRequired: Int? = null,
    @Json(name = "productCode")
    val productCode: Any? = null,
    @Json(name = "productDesc")
    val productDesc: Any? = null,
    @Json(name = "productImage")
    val productImage: Any? = null,
    @Json(name = "productImageServerPath")
    val productImageServerPath: Any? = null,
    @Json(name = "productName")
    val productName: Any? = null,
    @Json(name = "product_type")
    val productType: Int? = null,
    @Json(name = "redeemableAverageEarning")
    val redeemableAverageEarning: Any? = null,
    @Json(name = "redeemableAverageEarning12")
    val redeemableAverageEarning12: Int? = null,
    @Json(name = "redeemableAverageEarning6")
    val redeemableAverageEarning6: Int? = null,
    @Json(name = "redeemableEncashBalance")
    val redeemableEncashBalance: Int? = null,
    @Json(name = "redeemablePointBalance")
    val redeemablePointBalance: Int? = null,
    @Json(name = "redemptionDate")
    val redemptionDate: String? = null,
    @Json(name = "redemptionId")
    val redemptionId: Int? = null,
    @Json(name = "redemptionPlannerId")
    val redemptionPlannerId: Int? = null,
    @Json(name = "redemptionRefno")
    val redemptionRefno: String? = null,
    @Json(name = "redemptionStatus")
    val redemptionStatus: Any? = null,
    @Json(name = "redemptionTypeId")
    val redemptionTypeId: Int? = null,
    @Json(name = "responseOrderNumber")
    val responseOrderNumber: Any? = null,
    @Json(name = "segmentDetails")
    val segmentDetails: Any? = null,
    @Json(name = "selectedStatus")
    val selectedStatus: Int? = null,
    @Json(name = "status")
    val status: Int? = null,
    @Json(name = "subCategoryID")
    val subCategoryID: Int? = null,
    @Json(name = "subCategoryName")
    val subCategoryName: Any? = null,
    @Json(name = "subLocationID")
    val subLocationID: Int? = null,
    @Json(name = "termsCondition")
    val termsCondition: Any? = null,
    @Json(name = "toDate")
    val toDate: String? = null,
    @Json(name = "token")
    val token: Any? = null,
    @Json(name = "totalCash")
    val totalCash: Int? = null,
    @Json(name = "total_Row")
    val totalRow: Int? = null,
    @Json(name = "userAccess")
    val userAccess: Int? = null,
    @Json(name = "vendorId")
    val vendorId: Int? = null,
    @Json(name = "vendorName")
    val vendorName: Any? = null,
    @Json(name = "voucherCardName")
    val voucherCardName: Any? = null
)

@JsonClass(generateAdapter = true)
data class StatusSpinnerRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null
)


@JsonClass(generateAdapter = true)
data class StatusSpinnerResponse(
    @Json(name = "actionType")
    val actionType: Int? = null,
    @Json(name = "lstAttributesDetails")
    val lstAttributesDetails: List<LstAttributesDetailStatus>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetailStatus(
    @Json(name = "attributeContents")
    val attributeContents: Any? = null,
    @Json(name = "attributeCurrencyId")
    val attributeCurrencyId: Any? = null,
    @Json(name = "attributeId")
    var attributeId: Int? = null,
    @Json(name = "attributeNames")
    val attributeNames: Any? = null,
    @Json(name = "attributeType")
    val attributeType: String? = null,
    @Json(name = "attributeValue")
    var attributeValue: String? = null,
    @Json(name = "totalEarning")
    val totalEarning: Int? = null
)