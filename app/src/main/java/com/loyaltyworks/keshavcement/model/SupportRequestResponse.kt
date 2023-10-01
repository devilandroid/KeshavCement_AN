package com.loyaltyworks.keshavcement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


/*Help topic Request*/

@JsonClass(generateAdapter = true)
data class HelpTopicRequest(
    @Json(name = "ActionType")
    var actionType: String? = null,
    @Json(name = "ActorId")
    var actorId: String? = null,
    @Json(name = "IsActive")
    var isActive: String? = null
)

/*Help topic Response*/
@JsonClass(generateAdapter = true)
data class HelpTopicResponse(
    @Json(name = "objHelpTopicList")
    var objHelpTopicList: List<ObjHelpTopic>? = null,
    @Json(name = "returnMessage")
    var returnMessage: Any? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjHelpTopic(
    @Json(name = "createDate")
    var createDate: Any? = null,
    @Json(name = "customerView")
    var customerView: Boolean? = null,
    @Json(name = "escalationInHours")
    var escalationInHours: Int? = null,
    @Json(name = "helpTopicId")
    var helpTopicId: Int? = null,
    @Json(name = "helpTopicName")
    var helpTopicName: String? = null,
    @Json(name = "iS_ACTIVE")
    var iSACTIVE: Boolean? = null,
    @Json(name = "subHelpTopicId")
    var subHelpTopicId: Int? = null,
    @Json(name = "subHelpTopicName")
    var subHelpTopicName: Any? = null,
    @Json(name = "type")
    var type: Int? = null
)

/*Save New Ticket Request*/

@JsonClass(generateAdapter = true)
data class SaveNewTicketQueryRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var CustomerName: String? = null,
    var Email: String? = null,
    var HelpTopic: String? = null,
    var HelpTopicID: String? = null,
    var IsQueryFromMobile: String? = null,
    var LoyaltyID: String? = null,
    var Mobile: String? = null,
    var QueryDetails: String? = null,
    var QuerySummary: String? = null,
    var QuerySummaryMultipleQuery: String? = null,
    var SourceType: String? = null,
    var ImageUrl: String? = null,
    var FileType: String? = null
)


/*Save New Ticket Response*/

@JsonClass(generateAdapter = true)
data class SaveNewTicketQueryResponse(
    @Json(name = "objCustomerAllQueryJsonList")
    val objCustomerAllQueryJsonList: Any? = null,
    @Json(name = "objCustomerAllQueryList")
    val objCustomerAllQueryList: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)


/*Post Chat reply request*/

@JsonClass(generateAdapter = true)
data class PostChatStatusRequest(
    var ActionType: String? = null,
    var FileType: String? = null,
    var ImageUrl: String? = null,
    var ActorId: String? = null,
    var CustomerName: String? = null,
    var CustomerTicketID: String? = null,
    var Email: String? = null,
    var HelpTopic: String? = null,
    var HelpTopicID: String? = null,
    var IsQueryFromMobile: String? = null,
    var LoyaltyID: String? = null,
    var Mobile: String? = null,
    var QueryDetails: String? = null,
    var QueryStatus: String? = null,
    var QuerySummary: String? = null,
    var SourceType: String? = null
)

/*Post chat reply response*/

@JsonClass(generateAdapter = true)
data class PostChatStatusResponse(
    @Json(name = "objCustomerAllQueryJsonList")
    val objCustomerAllQueryJsonList: Any? = null,
    @Json(name = "objCustomerAllQueryList")
    val objCustomerAllQueryList: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

    /* Query Listing */

@JsonClass(generateAdapter = true)
data class QueryListingRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "HelpTopicID")
    val helpTopicID: String? = null,
    @Json(name = "TicketStatusId")
    val ticketStatusId: String? = null,
    @Json(name = "StartIndex")
    val startIndex: Int? = null,
    @Json(name = "PageSize")
    val pageSize: Int? = null
)

@JsonClass(generateAdapter = true)
data class QueryListingResponse(
    @Json(name = "objCustomerAllQueryJsonList")
    val objCustomerAllQueryJsonList: List<ObjCustomerAllQueryJson>? = null,
    @Json(name = "objCustomerAllQueryList")
    val objCustomerAllQueryList: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerAllQueryJson(
    @Json(name = "comments")
    val comments: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "customerTicketID")
    val customerTicketID: Int? = null,
    @Json(name = "customerTicketRefNo")
    val customerTicketRefNo: String? = null,
    @Json(name = "deviceId")
    val deviceId: Any? = null,
    @Json(name = "helpTopic")
    val helpTopic: String? = null,
    @Json(name = "helpTopicID")
    val helpTopicID: Int? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "jLastModifiedDate")
    val jLastModifiedDate: Any? = null,
    @Json(name = "lastModifiedDate")
    val lastModifiedDate: Any? = null,
    @Json(name = "memberId")
    val memberId: Any? = null,
    @Json(name = "memberName")
    val memberName: Any? = null,
    @Json(name = "mobile")
    val mobile: Any? = null,
    @Json(name = "modifiedBy")
    val modifiedBy: String? = null,
    @Json(name = "queryDetails")
    val queryDetails: String? = null,
    @Json(name = "querySource")
    val querySource: String? = null,
    @Json(name = "querySummary")
    val querySummary: String? = null,
    @Json(name = "rating")
    val rating: Any? = null,
    @Json(name = "subHelpTopic")
    val subHelpTopic: String? = null,
    @Json(name = "subHelpTopicID")
    val subHelpTopicID: Int? = null,
    @Json(name = "ticketStatus")
    val ticketStatus: String? = null,
    @Json(name = "totalRows")
    val totalRows: Int? = null
) : Serializable

/* Query Chat */

@JsonClass(generateAdapter = true)
data class QueryChatElementRequest(
    @Json(name = "ActionType")
    val actionType: String? = null,
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "CustomerTicketID")
    val customerTicketID: String? = null
)

@JsonClass(generateAdapter = true)
data class QueryChatElementResponse(
    @Json(name = "objQueryCenterList")
    val objQueryCenterList: Any? = null,
    @Json(name = "objQueryResponseJsonList")
    val objQueryResponseJsonList: List<ObjQueryResponseJson>? = null,
    @Json(name = "objQueryResponseList")
    val objQueryResponseList: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjQueryResponseJson(
    @Json(name = "color")
    val color: String? = null,
    @Json(name = "createdDate")
    val createdDate: String? = null,
    @Json(name = "imageUrl")
    val imageUrl: String? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "queryResponseInfo")
    val queryResponseInfo: String? = null,
    @Json(name = "queryStatus")
    val queryStatus: String? = null,
    @Json(name = "repliedBy")
    val repliedBy: String? = null,
    @Json(name = "userType")
    val userType: String? = null
)


