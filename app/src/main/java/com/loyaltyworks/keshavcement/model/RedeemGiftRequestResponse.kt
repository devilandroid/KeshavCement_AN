package com.loyaltyworks.keshavcement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable


/*Product Category List*/

/*
@JsonClass(generateAdapter = true)
data class ProductCategoryRequest(
    val ObjCatalogueRetriveRequest: ObjCatalogueRetriveRequest
)
*/

@JsonClass(generateAdapter = true)
data class ProductCategoryRequest(
    val ActionType: String?=null,
    val ActorId: String?=null,
    val ObjCatalogueCategoryDetails: ObjCatalogueCategoryDetail?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueCategoryDetail(
    val IsActive: String?=null
)

/*
@JsonClass(generateAdapter = true)
data class ProductCategoryResponse(
    val getCatalogueCategoryDetailsResult: GetCatalogueCategoryDetailsResult
)
*/

@JsonClass(generateAdapter = true)
data class ProductCategoryResponse(
        val objCatalogueCategoryListJson: List<ObjCatalogueCategoryJsonnn>?=null,
        val returnMessage: Any?=null,
        val returnValue: Int?=null,
        val totalRecords: Int?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueCategoryJsonnn(
    val actionType: Int?=null,
    val actorId: Int?=null,
    val actorRole: Any?=null,
    val catalogueBrandId: Int?=null,
    val catalogueBrandName: String?=null,
    val catogoryId: Int?=null,
    val catogoryImage: Any?=null,
    val catogoryName: String?=null,
    val isActive: Boolean?=null,
    val subCategoryID: Int?=null,
    val subCategoryName: Any?=null
)

/*Product Category List*/


/*Voucher and Catalogue Product Request*/
@JsonClass(generateAdapter = true)
data class RedeemGiftRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
//    var NoOfRows: String? = null,
        var ObjCatalogueDetails: ObjCatalogueDetailll? = null,
        var StartIndex: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailll(
        var CatalogueType: String? = null,
        var CatogoryId: String? = null,
        var CatalogueId: String? = null,
        var MerchantId: String? = null

)

/*Voucher and Product CatalogueResponse*/
@JsonClass(generateAdapter = true)
data class RedeemGiftResponse(
        var objCatalogueList: MutableList<ObjCatalogueListtt>? = null,
        var returnMessage: String? = null,
        var returnValue: Int? = null,
        var totalRecords: Int? = null,
        var catalogueImageGallery: String? = null,
        var locationCites: String? = null,
        var objCatalogueCategoryList: String? = null,
        var objCatalogueFixedPoints: List<ObjCatalogueFixedPoint>? = null,
        var objCatalogueRedemReqList: String? = null,
        var objCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var lstCatalogueProductAvailableCity: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueFixedPoint(
        var fixedPoints: Int? = null,
        var productCode: String? = null
):Serializable

@Entity(tableName = "Product_table")
@JsonClass(generateAdapter = true)
data class ObjCatalogueListtt(
        @PrimaryKey(autoGenerate = true) var id: Int?=null,
        var actionType: Int? = null,
        var actorId: Int? = null,
        var actorRole: String? = null,
        var isActive: Boolean? = null,
        var brandTermsAndConditions: String? = null,
        var catalogueBrandCode: String? = null,
        var catalogueBrandDesc: String? = null,
        var catalogueBrandId: Int? = null,
        var catalogueBrandName: String? = null,
        var categoryParentID: Int? = null,
        var catogoryId: Int? = null,
        var catogoryImage: String? = null,
        @ColumnInfo(name = "CatogoryName") var catogoryName: String? = null,
        var color_Code: String? = null,
        var color_Id: Int? = null,
        var color_Name: String? = null,
        var fromDate: String? = null,
        var jFromDate: String? = null,
        var jToDate: String? = null,
        @ColumnInfo(name = "MerchantId") var merchantId: Int? = null,
        @ColumnInfo(name = "MerchantName") var merchantName: String? = null,
        var modelId: Int? = null,
        var modelName: String? = null,
        var status: Int? = null,
        var subCategoryID: Int? = null,
        var subCategoryName: String? = null,
        @ColumnInfo(name = "TermsCondition") var termsCondition: String? = null,
        var toDate: String? = null,
        var userAccess: Int? = null,
        var activeStatus: Boolean? = null,
        var actualRedemptionDate: String? = null,
        var additionalRemarks: String? = null,
        var approverName: String? = null,
        var averageEarning: String? = null,
        var avgExpDate: String? = null,
        var avgGreaterExpDate: String? = null,
        var avgLesserExpDate: String? = null,
        var barcode: String? = null,
        var cashPerUnit: Int? = null,
        var cashValue: Int? = null,
        var catalogueId: Int? = null,
        var catalogueType: Int? = null,
        var catalougeBrandName: String? = null,
        @ColumnInfo(name = "CategoryID")  var categoryID: Int? = null,
        var commandName: String? = null,
        var countryCurrencyCode: String? = null,
        var countryID: Int? = null,
        var createdBy: String? = null,
        var createdDate: String? = null,
        var dailyAvgCash: String? = null,
        @ColumnInfo(name = "DeliveryType") var deliveryType: String? = null,
        var dreamGiftId: Int? = null,
        var expectedDelivery: String? = null,
        var expiryDate: String? = null,
        var expiryOn: Int? = null,
        var greaterAvgCash: String? = null,
        var hasPartialPayment: Boolean? = null,
        var isApproved: Boolean? = null,
        @ColumnInfo(name = "IsCash") var isCash: Boolean? = null,
        var isPlanner: Boolean? = null,
//        var isPopularCount: Int? = null,
        var jRedemptionDate: String? = null,
        var lesserAvgCash: String? = null,
        var locationId: Int? = null,
        var loyaltyId: String? = null,
        var mSQA: Int? = null,
        var memberName: String? = null,
        var minimumStockQunty: Int? = null,
        var mobile: String? = null,
        var multipleRedIds: String? = null,
        @ColumnInfo(name = "Net_Points") var noOfPointsDebit: Int? = null, // Total number of points need to redeem product
        @ColumnInfo(name = "NoOfQuantity") var noOfQuantity: Int? = null,
        var partialPaymentCash: Int? = null,
        var plannerStatus: String? = null,
        var pointBalance: Int? = null,
        var pointRedem: Int? = null,
        var pointReqToAcheiveProduct: Int? = null,
        var pointsPerUnit: Int? = null,
        @ColumnInfo(name = "PointsRequired") var pointsRequired: Int? = null,
        @ColumnInfo(name = "ProductCode") var productCode: String? = null,
        @ColumnInfo(name = "ProductDesc") var productDesc: String? = null,
        @ColumnInfo(name = "ProductImage") var productImage: String? = null,
        var productImageServerPath: String? = null,
        @ColumnInfo(name = "ProductName") var productName: String? = null,
        var product_type: Int? = null,
        var is_Redeemable: Int? = null,
        var redeemableAverageEarning: String? = null,
        var redeemableAverageEarning12: Int? = null,
        var redeemableAverageEarning6: Int? = null,
        var redeemableEncashBalance: Int? = null,
        var redeemablePointBalance: Int? = null,
        var redemptionDate: String? = null,
        var redemptionId: Int? = null,
        var redemptionPlannerId: Int? = null,
        var redemptionRefno: String? = null,
        var redemptionStatus: String? = null,
        var redemptionTypeId: Int? = null,
        var segmentDetails: String? = null,
        var selectedStatus: Int? = null,
        @ColumnInfo(name = "TotalCash") var totalCash: Int? = null,
        var total_Row: Int? = null,
        @ColumnInfo(name = "VendorId")  var vendorId: Int? = null,
        @ColumnInfo(name = "VendorName") var vendorName: String? = null,
        var max_points: String? = null,
        var min_points: String? = null,
        @Ignore var objCatalogueFixedPoints: List<ObjCatalogueFixedPoint>? = null // Added for hold the respective points range for Voucher list.
):Serializable


/*RedeemGiftVoucher Request*/

@JsonClass(generateAdapter = true)
data class RedeemGiftVoucherRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var DealerLoyaltyId: String? = null,
        var CountryCode: String? = null,
        var CountryID: String? = null,
        var MerchantId: String? = null,
        var ObjCatalogueList: List<ObjCatalogueListss>? = null,
        var ReceiverEmail: String? = null,
        var ReceiverName: String? = null,
        var ReceiverMobile: String? = null,
        var SourceMode: String? = null
)
@JsonClass(generateAdapter = true)
data class ObjCatalogueListss(
        var CatalogueId: Int? = null,
        var CountryCurrencyCode: String? = null,
        var Address1: String? = null,
        var DeliveryType: String? = null,
        var HasPartialPayment: Boolean? = null,
        var NoOfPointsDebit: Int? = null,
        var PointsRequired: Float? = null,
        var ProductCode: String? = null,
        var ProductImage: String? = null,
        var ProductName: String? = null,
        var JRedemptionDate: String? = null,
        var RedemptionId: Int? = null,
        var RedemptionTypeId: Int? = null,
        var NoOfQuantity: Int? = null,
        var Status: Int? = null,
        var VendorId: Int? = null,
        var VendorName: String? = null
)

/*RedeemGiftVoucherResponse*/
@JsonClass(generateAdapter = true)
data class RedeemGiftVoucherResponse(
        var returnMessage: String? = null,
        var returnValue: Int? = null,
        var totalRecords: Int? = null
)


/*RedeemGift Catalogue Request */

/*Send Customer Shipping Address Details Request*/
@JsonClass(generateAdapter = true)
data class RedeemGiftCatalogueRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var MemberName: String? = null,
        var ObjCatalogueList: List<ObjCatalogueList>? = null,
        var ObjCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var SourceMode: String? = null
):Serializable

@JsonClass(generateAdapter = true)
data class ObjCustShippingAddressDetail(
        var Address1: String? = null,
        var CityId: String? = null,
        var CityName: String? = null,
        var CountryId: String? = null,
        var StateId: String? = null,
        var StateName: String? = null,
        var Zip: String? = null,
        var Email: String? = null,
        var FullName: String? = null,
        var Mobile: String? = null
):Serializable

/*Response of Catalogue Save Request for Shipping Address*/
@JsonClass(generateAdapter = true)
data class RedeemGiftCatalogueResponse(
         var returnMessage: String? = null,
         var returnValue: Int? = null,
         var totalRecords: Int? = null
)


/*Call Alert Mobile App Request After Success of Customer Shipping Address Details Request*/

@JsonClass(generateAdapter = true)
data class SendCatalogueRedemptionAlertMobileAppRequest(
        var MemberName: String? = null,
        var MerchantEmailID: String? = null,
        var MerchantID: String? = null,
        var MerchantMobileNo: String? = null,
        var ObjCatalogueList: List<ObjCatalogueList>? = null,
        var ObjCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var TotalPointsRedeemed: String? = null,
        var UserName: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueTypeList(
        var Catalogue: ObjCatalogueList? = null
)

/*Alert Mobile Request Respone */
@JsonClass(generateAdapter = true)
data class SendCatalogueRedemptionAlertMobileAppResponse(
        var returnMessage: String? = null,
        var returnValue: Int? = null,
        var totalRecords: Int? = null

        )


/*After Success response of Alert Mobile App Request Call Send SMS For Success Redemption Request*/
@JsonClass(generateAdapter = true)
data class SendSMSForSuccessfulRedemptionMobileAppRequest(
         var CustomerName: String? = null,
         var EmailID: String? = null,
         var LoyaltyID: String? = null,
         var Mobile: String? = null,
         var PointBalance: String? = null,
         var RedeemedPoint: String? = null

        )


/*Success Response Of Send SMS For Success Redemption*/
@JsonClass(generateAdapter = true)
data class SendSMSForSuccessfulRedemptionMobileAppResponse (
     var sendSMSForSuccessfulRedemptionMobileAppResult: Boolean? = null
)


