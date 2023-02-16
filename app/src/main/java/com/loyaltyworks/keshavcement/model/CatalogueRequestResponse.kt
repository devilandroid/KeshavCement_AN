package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


/*  Get Catalogue Details Request response */

@JsonClass(generateAdapter = true)
data class GatCatalogueRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ObjCatalogueDetails")
    val objCatalogueDetails: ObjCatalogueDetailss? = null,
    @Json(name = "SearchText")
    val searchText: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "NoOfRows")
    val noOfRows: Int? = null,
    @Json(name = "Sort")
    val sort: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailss(
    @Json(name = "MerchantId")
    val merchantId: Int? = null,
    @Json(name = "CatogoryId")
    val catogoryId: Int? = null,
    @Json(name = "CatalogueType")
    val catalogueType: Int? = null,
    @Json(name = "MultipleRedIds")
    val multipleRedIds: String? = null
)


/*   Get Catalogue Response  */
@JsonClass(generateAdapter = true)
data class GetCatalogueResponse(
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
    val objCatalogueList: List<ObjCataloguee>? = null,
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
data class ObjCataloguee(
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
    val catogoryName: String? = null,
    @Json(name = "color_Code")
    val colorCode: Any? = null,
    @Json(name = "color_Id")
    val colorId: Int? = null,
    @Json(name = "color_Name")
    val colorName: Any? = null,
    @Json(name = "commandName")
    val commandName: Any? = null,
    @Json(name = "comments")
    val comments: String? = null,
    @Json(name = "countryCurrencyCode")
    val countryCurrencyCode: Any? = null,
    @Json(name = "countryID")
    val countryID: Int? = null,
    @Json(name = "createdBy")
    val createdBy: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "customerCartId")
    val customerCartId: Int? = null,
    @Json(name = "dailyAvgCash")
    val dailyAvgCash: Any? = null,
    @Json(name = "deliveryType")
    val deliveryType: String? = null,
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
    @Json(name = "isAddPlanner")
    var isAddPlanner: Boolean? = null,
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
    @Json(name = "MSQA")
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
    val productDesc: String? = null,
    @Json(name = "productImage")
    val productImage: String? = null,
    @Json(name = "productImageServerPath")
    val productImageServerPath: Any? = null,
    @Json(name = "productName")
    val productName: String? = null,
    @Json(name = "catalogueIdExist")
    var catalogueIdExist: String? = null,
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
    val redemptionRefno: Any? = null,
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
    val termsCondition: String? = null,
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
    val vendorName: String? = null
) : Serializable

/*  Get Catalogue Category request */

@JsonClass(generateAdapter = true)
data class CatalogueCategoryRequest(
    @Json(name = "ActionType")
    var actionType: String? = null,
    @Json(name = "ActorId")
    var actorId: String? = null,
    @Json(name = "IsActive")
    var isActive: Int? = null
)

@JsonClass(generateAdapter = true)
data class CatalogueCategoryResponse(
    @Json(name = "objCatalogueCategoryListJson")
    var objCatalogueCategoryListJson: List<ObjCatalogueCategoryJson>? = null,
    @Json(name = "responseCode")
    var responseCode: Any? = null,
    @Json(name = "returnMessage")
    var returnMessage: Any? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueCategoryJson(
    @Json(name = "actionType")
    var actionType: Int? = null,
    @Json(name = "actorId")
    var actorId: Int? = null,
    @Json(name = "actorRole")
    var actorRole: Any? = null,
    @Json(name = "catalogueBrandId")
    var catalogueBrandId: Int? = null,
    @Json(name = "catalogueBrandName")
    var catalogueBrandName: String? = null,
    @Json(name = "catogoryId")
    var catogoryId: Int? = null,
    @Json(name = "catogoryImage")
    var catogoryImage: Any? = null,
    @Json(name = "catogoryName")
    var catogoryName: String? = null,
    @Json(name = "encashValue")
    var encashValue: Any? = null,
    @Json(name = "isActive")
    var isActive: Boolean? = null,
    @Json(name = "memberId")
    var memberId: Any? = null,
    @Json(name = "preferedModeOfRedemption")
    var preferedModeOfRedemption: Any? = null,
    @Json(name = "redemptionDateTime")
    var redemptionDateTime: Any? = null,
    @Json(name = "redemptionRefNo")
    var redemptionRefNo: Any? = null,
    @Json(name = "redemptionStatus")
    var redemptionStatus: Any? = null,
    @Json(name = "subCategoryID")
    var subCategoryID: Int? = null,
    @Json(name = "subCategoryName")
    var subCategoryName: Any? = null,
    @Json(name = "token")
    var token: Any? = null,
    @Json(name = "totalPointsRedemed")
    var totalPointsRedemed: Any? = null
)


/*  Add to Cart Request Response  */
@JsonClass(generateAdapter = true)
data class AddToCartRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "CatalogueSaveCartDetailListRequest")
    var catalogueSaveCartDetailListRequest: List<CatalogueSaveCartDetailRequest>? = null,
    @Json(name = "LoyaltyID")
    val loyaltyID: String? = null,
    @Json(name = "MerchantId")
    val merchantId: String? = null
)

@JsonClass(generateAdapter = true)
data class CatalogueSaveCartDetailRequest(
    @Json(name = "CatalogueId")
    var catalogueId: String? = null,
    @Json(name = "Comments")
    var comments: String? = null,
    @Json(name = "DeliveryType")
    var deliveryType: String? = null,
    @Json(name = "NoOfQuantity")
    var noOfQuantity: String? = null
)

@JsonClass(generateAdapter = true)
data class AddToCartResponse(
    @Json(name = "catalogueSaveCartDetailListResponse")
    val catalogueSaveCartDetailListResponse: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalCartCatalogue")
    val totalCartCatalogue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)


/*  Cart Listing  */
@JsonClass(generateAdapter = true)
data class CartRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "LoyaltyID")
    val loyaltyID: String? = null
)

@JsonClass(generateAdapter = true)
data class CartResponse(
    @Json(name = "catalogueSaveCartDetailListResponse")
    val catalogueSaveCartDetailListResponse: List<CatalogueSaveCartDetailResponse>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalCartCatalogue")
    val totalCartCatalogue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class CatalogueSaveCartDetailResponse(
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
    @Json(name = "catalogueIdExist")
    val catalogueIdExist: Int? = null,
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
    @Json(name = "categoryName")
    val catogoryName: String? = null,
    @Json(name = "color_Code")
    val colorCode: Any? = null,
    @Json(name = "color_Id")
    val colorId: Int? = null,
    @Json(name = "color_Name")
    val colorName: Any? = null,
    @Json(name = "commandName")
    val commandName: Any? = null,
    @Json(name = "comments")
    val comments: String? = null,
    @Json(name = "countryCurrencyCode")
    val countryCurrencyCode: Any? = null,
    @Json(name = "countryID")
    val countryID: Int? = null,
    @Json(name = "createdBy")
    val createdBy: Any? = null,
    @Json(name = "createdDate")
    val createdDate: String? = null,
    @Json(name = "customerCartId")
    val customerCartId: Int? = null,
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
    @Json(name = "isAddPlanner")
    val isAddPlanner: Boolean? = null,
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
    val loyaltyId: String? = null,
    @Json(name = "MSQA")
    val mSQA: Int? = null,
    @Json(name = "max_points")
    val maxPoints: Any? = null,
    @Json(name = "memberName")
    val memberName: String? = null,
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
    @Json(name = "multipleRedIds")
    val multipleRedIds: Any? = null,
    @Json(name = "noOfPointsDebit")
    val noOfPointsDebit: Int? = null,
    @Json(name = "noOfQuantity")
    var noOfQuantity: Int? = null,
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
    val pointsRequired: String? = null,
    @Json(name = "productCode")
    val productCode: String? = null,
    @Json(name = "productDesc")
    val productDesc: String? = null,
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
    val termsCondition: String? = null,
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
    @Json(name = "sumOfTotalPointsRequired")
    val sumofTotalPointsRequired: String? = null,
    @Json(name = "vendorName")
    val vendorName: String? = null
) : Serializable

 /*  Cart Count Update Request   */

@JsonClass(generateAdapter = true)
data class UpdateQuantityRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var CustomerCartId: String? = null,
    var CustomerCartList: List<CustomerCart>? = null
)

@JsonClass(generateAdapter = true)
data class CustomerCart(
    var CustomerCartId: String? = null,
    var Quantity: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateQuantityResponse(
    val returnMessage: String? = null,
    val returnValue: Int? = null,
    val totalRecords: Int? = null
)

/* Remove Cart Item  Request */
@JsonClass(generateAdapter = true)
data class RemoveCartProductRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val CustomerCartId: String? = null
)

/* Remove Cart Item  Response */
@JsonClass(generateAdapter = true)
data class RemoveCartProductResponse(
    val returnMessage: String? = null,
    val returnValue: String? = null,
    val totalRecords: String? = null
)

/* Planner Add Request */
@JsonClass(generateAdapter = true)
data class PlannerAddRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ObjCatalogueDetails")
    val objCatalogueDetails: ObjCatalogueDetailsy? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailsy(
    @Json(name = "CatalogueId")
    val catalogueId: Int? = null
)

/* Planner Add Response */
@JsonClass(generateAdapter = true)
data class PlannerAddResponse(
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
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

/* Cart Count Request */
@JsonClass(generateAdapter = true)
data class CartCountRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "LoyaltyID")
    val loyaltyID: String? = null
)

/* Cart Count Response */
@JsonClass(generateAdapter = true)
data class CartCountResponse(
    @Json(name = "catalogueSaveCartDetailListResponse")
    val catalogueSaveCartDetailListResponse: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalCartCatalogue")
    val totalCartCatalogue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)