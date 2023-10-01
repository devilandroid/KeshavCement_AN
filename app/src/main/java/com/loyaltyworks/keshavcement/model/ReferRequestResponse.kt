package com.loyaltyworks.keshavcement.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/*** Refer Request ***/
@JsonClass(generateAdapter = true)
data class ReferRequest(
    @Json(name = "ActionType")
    var actionType: String? = null,
    @Json(name = "ActorId")
    var actorId: String? = null,
    @Json(name = "ObjContactCenterDetails")
    var objContactCenterDetails: ObjContactCenterDetails? = null
)

@JsonClass(generateAdapter = true)
data class ObjContactCenterDetails(
    @Json(name = "RefereeMobileNo")
    var refereeMobileNo: String? = null,
    @Json(name = "RefereeName")
    var refereeName: String? = null
)

/*** Refer Response ***/
@JsonClass(generateAdapter = true)
data class ReferResponse(
    @Json(name = "returnMessage")
    var returnMessage: String? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null
)