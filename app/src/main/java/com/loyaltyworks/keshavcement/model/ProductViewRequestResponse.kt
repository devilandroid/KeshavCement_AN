package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Product view list request ***/
@JsonClass(generateAdapter = true)
data class ProductViewListRequest(
    @Json(name = "ActionType")
    val actionType: Int? = null,
    @Json(name = "ActorId")
    val actorId: Int? = null
)

/*** Product view list response ***/
@JsonClass(generateAdapter = true)
data class ProductViewListResponse(
    @Json(name = "lstProductListDetails")
    val lstProductListDetails: List<LstProductDetailsProdView>? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstProductDetailsProdView(
    @Json(name = "brandCode")
    val brandCode: Any? = null,
    @Json(name = "brandDesc")
    val brandDesc: Any? = null,
    @Json(name = "brandId")
    val brandId: Int? = null,
    @Json(name = "brandName")
    val brandName: Any? = null,
    @Json(name = "categoryDesc")
    val categoryDesc: Any? = null,
    @Json(name = "categoryId")
    val categoryId: Int? = null,
    @Json(name = "categoryImg")
    val categoryImg: Any? = null,
    @Json(name = "categoryName")
    val categoryName: Any? = null,
    @Json(name = "imageUrl")
    val imageUrl: Any? = null,
    @Json(name = "productCode")
    val productCode: Any? = null,
    @Json(name = "productDesc")
    val productDesc: Any? = null,
    @Json(name = "productId")
    val productId: Int? = null,
    @Json(name = "productImage")
    val productImage: Any? = null,
    @Json(name = "productName")
    val productName: String? = null,
    @Json(name = "productShortDesc")
    val productShortDesc: Any? = null,
    @Json(name = "quantity")
    val quantity: String? = null
)