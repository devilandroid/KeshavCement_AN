package com.loyaltyworks.keshavcement.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ProfileRequest(
    val ActionType: String? = null,
    val MerchantID: String? = null,
    val MobileNumber: String? = null,
    val CustomerId: String? = null
)

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "customerBasicInfoList")
    val customerBasicInfoList: Any? = null,
    @Json(name = "customerFamilyList")
    val customerFamilyList: Any? = null,
    @Json(name = "customerPreferenceList")
    val customerPreferenceList: Any? = null,
    @Json(name = "hierarchyMapDetails")
    val hierarchyMapDetails: Any? = null,
    @Json(name = "lstCustomerIdentityInfo")
    val lstCustomerIdentityInfo: Any? = null,
    @Json(name = "lstCustomerJson")
    val lstCustomerJson: List<LstCustomerJson>? = null,
    @Json(name = "lstCustomerOfficalInfoJson")
    val lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJson>? = null,
    @Json(name = "lstVehicleJson")
    val lstVehicleJson: List<Any?>? = null,
    @Json(name = "lstWorkSiteInfoDetails")
    val lstWorkSiteInfoDetails: Any? = null,
    @Json(name = "mappedProductList")
    val mappedProductList: Any? = null,
    @Json(name = "objCustomer")
    val objCustomer: Any? = null,
    @Json(name = "objCustomerDetails")
    val objCustomerDetails: Any? = null,
    @Json(name = "objCustomerOfficalInfo")
    val objCustomerOfficalInfo: Any? = null,
    @Json(name = "returnMessage")
    val returnMessage: Any? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "subscriptionDetails")
    val subscriptionDetails: Any? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerJson(
    @Json(name = "aadharNumber")
    val aadharNumber: String? = null,
    @Json(name = "accountComStatus")
    val accountComStatus: Int? = null,
    @Json(name = "accountNumber")
    val accountNumber: Any? = null,
    @Json(name = "accountStatus")
    val accountStatus: String? = null,
    @Json(name = "accountType")
    val accountType: Any? = null,
    @Json(name = "accountTypeID")
    val accountTypeID: Int? = null,
    @Json(name = "acountHolderName")
    val acountHolderName: Any? = null,
    @Json(name = "address1")
    var address1: String? = null,
    @Json(name = "address2")
    val address2: String? = null,
    @Json(name = "addressId")
    val addressId: Int? = null,
    @Json(name = "addressLatitude")
    val addressLatitude: Any? = null,
    @Json(name = "addressLongitude")
    val addressLongitude: Any? = null,
    @Json(name = "ageGroupId")
    val ageGroupId: Int? = null,
    @Json(name = "anniversary")
    val anniversary: Any? = null,
    @Json(name = "areaId")
    val areaId: Int? = null,
    @Json(name = "areaName")
    val areaName: Any? = null,
    @Json(name = "autoAccountType")
    val autoAccountType: Any? = null,
    @Json(name = "autoBankCounty")
    val autoBankCounty: Any? = null,
    @Json(name = "autoBankPostCode")
    val autoBankPostCode: Any? = null,
    @Json(name = "bankAccountVerifiedStatus")
    val bankAccountVerifiedStatus: Int? = null,
    @Json(name = "bankAddress")
    val bankAddress: Any? = null,
    @Json(name = "bankBranch")
    val bankBranch: Any? = null,
    @Json(name = "bankCity")
    val bankCity: Any? = null,
    @Json(name = "bankCode")
    val bankCode: Any? = null,
    @Json(name = "bankCountryId")
    val bankCountryId: Int? = null,
    @Json(name = "bankCountryName")
    val bankCountryName: Any? = null,
    @Json(name = "bankName")
    val bankName: Any? = null,
    @Json(name = "bankPassbookImage")
    val bankPassbookImage: Any? = null,
    @Json(name = "bicSwiftCode")
    val bicSwiftCode: Any? = null,
    @Json(name = "branchCode")
    val branchCode: Any? = null,
    @Json(name = "bsbAbaRoutingNumber")
    val bsbAbaRoutingNumber: Any? = null,
    @Json(name = "chassisNumber")
    val chassisNumber: String? = null,
    @Json(name = "cityId")
    val cityId: Int? = null,
    @Json(name = "cityName")
    val cityName: Any? = null,
    @Json(name = "company")
    val company: Any? = null,
    @Json(name = "contractFileName")
    val contractFileName: Any? = null,
    @Json(name = "contractId")
    val contractId: Int? = null,
    @Json(name = "contractName")
    val contractName: Any? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: Any? = null,
    @Json(name = "createdBy")
    val createdBy: Any? = null,
    @Json(name = "createdDate")
    val createdDate: Any? = null,
    @Json(name = "currency")
    val currency: Any? = null,
    @Json(name = "currencyId")
    val currencyId: Int? = null,
    @Json(name = "custFamilyId")
    val custFamilyId: Int? = null,
    @Json(name = "customerCategory")
    val customerCategory: Any? = null,
    @Json(name = "customerCategoryId")
    val customerCategoryId: Int? = null,
    @Json(name = "customerDetailId")
    val customerDetailId: Int? = null,
    @Json(name = "customerGrade")
    val customerGrade: String? = null,
    @Json(name = "customer_Grade_ID")
    val customerGradeID: Int? = null,
    @Json(name = "customerId")
    val customerId: Int? = null,
    @Json(name = "customerOrderCount")
    val customerOrderCount: Int? = null,
    @Json(name = "customerRelationshipId")
    val customerRelationshipId: Int? = null,
    @Json(name = "customerRemarks")
    val customerRemarks: Any? = null,
    @Json(name = "customerType")
    val customerType: String? = null,
    @Json(name = "customerTypeID")
    val customerTypeID: Int? = null,
    @Json(name = "customerTypeId")
    val customerTypeId: Int? = null,
    @Json(name = "dealerName")
    val dealerName: Any? = null,
    @Json(name = "dealerUserId")
    val dealerUserId: Any? = null,
    @Json(name = "departmentId")
    val departmentId: Int? = null,
    @Json(name = "designationId")
    val designationId: Int? = null,
    @Json(name = "displayImage")
    val displayImage: Any? = null,
    @Json(name = "district")
    val district: String? = null,
    @Json(name = "districtId")
    var districtId: Int? = null,
    @Json(name = "districtName")
    var districtName: String? = null,
    @Json(name = "dob")
    val dob: Any? = null,
    @Json(name = "domain")
    val domain: Any? = null,
    @Json(name = "email")
    var email: String? = null,
    @Json(name = "emailStatus")
    val emailStatus: Int? = null,
    @Json(name = "engineNumber")
    val engineNumber: Any? = null,
    @Json(name = "enrollmentReferenceDate")
    val enrollmentReferenceDate: Any? = null,
    @Json(name = "enrollmentReferenceNumber")
    val enrollmentReferenceNumber: String? = null,
    @Json(name = "enrollmentStatus")
    val enrollmentStatus: Boolean? = null,
    @Json(name = "executiveName")
    val executiveName: Any? = null,
    @Json(name = "familyMemberBirthday")
    val familyMemberBirthday: Any? = null,
    @Json(name = "familyMemberName")
    val familyMemberName: Any? = null,
    @Json(name = "firstName")
    var firstName: String? = null,
    @Json(name = "gender")
    val gender: Any? = null,
    @Json(name = "ibanNumber")
    val ibanNumber: Any? = null,
    @Json(name = "identificationNo")
    val identificationNo: String? = null,
    @Json(name = "identificationOthers")
    val identificationOthers: Any? = null,
    @Json(name = "ifscCode")
    val ifscCode: Any? = null,
    @Json(name = "incomeRangeId")
    val incomeRangeId: Int? = null,
    @Json(name = "insuranceExpDate")
    val insuranceExpDate: Any? = null,
    @Json(name = "insuranceRenewalAmount")
    val insuranceRenewalAmount: Int? = null,
    @Json(name = "invoiceAmount")
    val invoiceAmount: Any? = null,
    @Json(name = "invoiceNo")
    val invoiceNo: Any? = null,
    @Json(name = "isActive")
    val isActive: Int? = null,
    @Json(name = "isBankPassbookNewImage")
    val isBankPassbookNewImage: Boolean? = null,
    @Json(name = "isBlackListed")
    val isBlackListed: Boolean? = null,
    @Json(name = "isGradeVerified")
    val isGradeVerified: Boolean? = null,
    @Json(name = "isSmartphoneUser")
    val isSmartphoneUser: Boolean? = null,
    @Json(name = "isUpdatePassword")
    val isUpdatePassword: Boolean? = null,
    @Json(name = "isVerified")
    val isVerified: Int? = null,
    @Json(name = "isVinChassis_Active")
    val isVinChassisActive: Any? = null,
    @Json(name = "isWhatsappUser")
    val isWhatsappUser: Boolean? = null,
    @Json(name = "jAnniversary")
    val jAnniversary: Any? = null,
    @Json(name = "jCreatedDate")
    val jCreatedDate: String? = null,
    @Json(name = "jD_InvoiceDate")
    val jDInvoiceDate: Any? = null,
    @Json(name = "jD_InvoiceNo")
    val jDInvoiceNo: Any? = null,
    @Json(name = "jDateOfSale")
    val jDateOfSale: Any? = null,
    @Json(name = "jEnrollmentReferenceDate")
    val jEnrollmentReferenceDate: Any? = null,
    @Json(name = "jInsuranceExpDate")
    val jInsuranceExpDate: Any? = null,
    @Json(name = "jPolicyDate")
    val jPolicyDate: Any? = null,
    @Json(name = "jdob")
    val jdob: Any? = null,
    @Json(name = "jjD_InvoiceDate")
    val jjDInvoiceDate: Any? = null,
    @Json(name = "lIdentificationType")
    val lIdentificationType: Int? = null,
    @Json(name = "laborAmount")
    val laborAmount: Int? = null,
    @Json(name = "landmark")
    val landmark: String? = null,
    @Json(name = "languageId")
    val languageId: Int? = null,
    @Json(name = "languageName")
    val languageName: Any? = null,
    @Json(name = "lastName")
    val lastName: String? = null,
    @Json(name = "locality")
    val locality: String? = null,
    @Json(name = "locationCode")
    val locationCode: Any? = null,
    @Json(name = "locationId")
    val locationId: Int? = null,
    @Json(name = "locationName")
    val locationName: Any? = null,
    @Json(name = "loyaltyId")
    val loyaltyId: String? = null,
    @Json(name = "loyaltyIdAutoGen")
    val loyaltyIdAutoGen: Boolean? = null,
    @Json(name = "maritalStatus")
    val maritalStatus: Any? = null,
    @Json(name = "merchantId")
    val merchantId: Int? = null,
    @Json(name = "mobile")
    var mobile: String? = null,
    @Json(name = "mobileNumberLimitation")
    val mobileNumberLimitation: Int? = null,
    @Json(name = "mobilePrefix")
    val mobilePrefix: String? = null,
    @Json(name = "mobile_Two")
    val mobileTwo: String? = null,
    @Json(name = "modelNumber")
    val modelNumber: String? = null,
    @Json(name = "nativeCountryId")
    val nativeCountryId: Int? = null,
    @Json(name = "nativeStateId")
    val nativeStateId: Int? = null,
    @Json(name = "nativeStateName")
    val nativeStateName: String? = null,
    @Json(name = "nominee")
    val nominee: Any? = null,
    @Json(name = "nomineeDOB")
    val nomineeDOB: Any? = null,
    @Json(name = "parentCustomerId")
    val parentCustomerId: Int? = null,
    @Json(name = "password")
    val password: Any? = null,
    @Json(name = "payOut")
    val payOut: Int? = null,
    @Json(name = "plainPassword")
    val plainPassword: Any? = null,
    @Json(name = "pointBalance")
    val pointBalance: Double? = null,
    @Json(name = "policyDate")
    val policyDate: Any? = null,
    @Json(name = "policyNumber")
    val policyNumber: String? = null,
    @Json(name = "professionId")
    val professionId: Int? = null,
    @Json(name = "profilePicture")
    val profilePicture: String? = null,
    @Json(name = "recipientPhonenumber")
    val recipientPhonenumber: Any? = null,
    @Json(name = "recipientType")
    val recipientType: Any? = null,
    @Json(name = "referedBy")
    val referedBy: Any? = null,
    @Json(name = "referralCode")
    val referralCode: Any? = null,
    @Json(name = "referrerCode")
    val referrerCode: Any? = null,
    @Json(name = "regStatus")
    val regStatus: Int? = null,
    @Json(name = "regType")
    val regType: String? = null,
    @Json(name = "registrationSource")
    val registrationSource: Int? = null,
    @Json(name = "relateD_PROJECT_TYPE")
    val relateDPROJECTTYPE: Any? = null,
    @Json(name = "relationship")
    val relationship: Any? = null,
    @Json(name = "religionID")
    val religionID: Int? = null,
    @Json(name = "remarks")
    val remarks: Any? = null,
    @Json(name = "stateId")
    var stateId: Int? = null,
    @Json(name = "stateName")
    var stateName: String? = null,
    @Json(name = "talukId")
    val talukId: Int? = null,
    @Json(name = "talukName")
    val talukName: String? = null,
    @Json(name = "taxId")
    val taxId: Any? = null,
    @Json(name = "tehsilBlockMandal")
    val tehsilBlockMandal: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "tradeLicence")
    val tradeLicence: Any? = null,
    @Json(name = "userId")
    val userId: Int? = null,
    @Json(name = "vehicleBrand")
    val vehicleBrand: Any? = null,
    @Json(name = "vehicleID")
    val vehicleID: Int? = null,
    @Json(name = "vehicleNumber")
    val vehicleNumber: String? = null,
    @Json(name = "vehicleType")
    val vehicleType: Any? = null,
    @Json(name = "verifiedStatus")
    val verifiedStatus: Any? = null,
    @Json(name = "village")
    val village: String? = null,
    @Json(name = "walletNumber")
    val walletNumber: String? = null,
    @Json(name = "walletVerifiedStatus")
    val walletVerifiedStatus: Boolean? = null,
    @Json(name = "whatsAppNumber")
    val whatsAppNumber: Any? = null,
    @Json(name = "zip")
    var zip: String? = null
): Serializable

@JsonClass(generateAdapter = true)
data class LstCustomerOfficalInfoJson(
    @Json(name = "address")
    val address: Any? = null,
    @Json(name = "cityId")
    val cityId: Int? = null,
    @Json(name = "cityName")
    val cityName: Any? = null,
    @Json(name = "companyName")
    val companyName: String? = null,
    @Json(name = "countryId")
    val countryId: Int? = null,
    @Json(name = "countryName")
    val countryName: Any? = null,
    @Json(name = "departmentIdOfficial")
    val departmentIdOfficial: Int? = null,
    @Json(name = "designation")
    val designation: Any? = null,
    @Json(name = "designationIdOfficial")
    val designationIdOfficial: Int? = null,
    @Json(name = "establishDate")
    val establishDate: Any? = null,
    @Json(name = "firmAddress")
    val firmAddress: Any? = null,
    @Json(name = "firmMobile")
    val firmMobile: Any? = null,
    @Json(name = "firmSize")
    val firmSize: Any? = null,
    @Json(name = "firmTypeID")
    val firmTypeID: Int? = null,
    @Json(name = "firmTypeName")
    val firmTypeName: Any? = null,
    @Json(name = "gstNumber")
    val gstNumber: String? = null,
    @Json(name = "incorporationDate")
    val incorporationDate: Any? = null,
    @Json(name = "industryID")
    val industryID: Int? = null,
    @Json(name = "isGSTNumber")
    val isGSTNumber: Any? = null,
    @Json(name = "jobTypeID")
    val jobTypeID: Int? = null,
    @Json(name = "jobTypeName")
    val jobTypeName: Any? = null,
    @Json(name = "officalEmail")
    val officalEmail: Any? = null,
    @Json(name = "ownerName")
    val ownerName: Any? = null,
    @Json(name = "phoneOffice")
    val phoneOffice: Any? = null,
    @Json(name = "phoneResidence")
    val phoneResidence: Any? = null,
    @Json(name = "sapCode")
    val sapCode: String? = null,
    @Json(name = "stateId")
    val stateId: Int? = null,
    @Json(name = "stateName")
    val stateName: Any? = null,
    @Json(name = "stdCode")
    val stdCode: Any? = null,
    @Json(name = "storeName")
    val storeName: Any? = null,
    @Json(name = "tahasilImage")
    val tahasilImage: Any? = null,
    @Json(name = "targetCreditPeriod")
    val targetCreditPeriod: Int? = null,
    @Json(name = "targetPoint")
    val targetPoint: Int? = null,
    @Json(name = "targetValue")
    val targetValue: Int? = null,
    @Json(name = "venderCode")
    val venderCode: Any? = null,
    @Json(name = "zip")
    val zip: Any? = null
)

/*** Profile Image Update request ***/
@JsonClass(generateAdapter = true)
data class ProfileImageUpdateRequest(
    @Json(name = "ActorId")
    val actorId: String? = null,
    @Json(name = "ObjCustomerJson")
    val objCustomerJson: ObjCustomerJsonss? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerJsonss(
    @Json(name = "DisplayImage")
    val displayImage: String? = null,
    @Json(name = "LoyaltyId")
    val loyaltyId: String? = null
)

/*** Profile Image Update response ***/
@JsonClass(generateAdapter = true)
data class ProfileImageUpdateResponse(
    @Json(name = "returnMessage")
    val returnMessage: String? = null,
    @Json(name = "returnValue")
    val returnValue: Int? = null,
    @Json(name = "totalRecords")
    val totalRecords: Int? = null
)