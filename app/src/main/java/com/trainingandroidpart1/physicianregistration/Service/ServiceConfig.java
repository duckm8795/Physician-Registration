package com.trainingandroidpart1.physicianregistration.Service;

/**
 * Created by Admin on 6/28/2016.
 */
public interface ServiceConfig {

    /* base url */
    String BASE_URL = "http://dev.jiohealth.com:8081/";

    /*create account*/
    String API_CREATE_PROVIDER_ACCOUNT = "JioMobileWS/rest/jio/createProviderAccount";

    /* set security pin */
    String API_SET_SECURITY_PIN = "JioMobileWS/rest/jio/setSecurityPin";

    /* verify physician*/
    String VERIFY_PHYSICIAN = "JioMobileWS/rest/jio/getVerificationDocTypesByCountry";

    /* get provider profile*/
    String GET_PROVIDER_PROFILE = "JioMobileWS/rest/jio/getProviderProfile";


    /* get degree list */
    String GET_DEGREE_LIST = "JioMobileWS/rest/jio/getDegreeList";

    /* get specialty list */
    String GET_SPECIALTY_LIST = "JioMobileWS/rest/jio/getSpecialtyList";

    /* get language list */
    String GET_LANGUAGE_LIST = "JioMobileWS/rest/jio/getLanguageList";

    /* get upload avatar */
    String UPDATE_AVATAR_URL = "JioMobileWS/rest/jio/uploadAvatar";

    /* save provider profile info */
    String API_SAVE_PROVIDER_PROFILE = "/JioMobileWS/rest/jio/saveProviderProfileInfo";
    /*  update speciality list*/
    String API_UPDATE_SPECIALITY_LIST = "/JioMobileWS/rest/jio/updateProviderSpecialties";
    /* save language */
    String API_SAVE_USER_LANGUAGE = "/JioMobileWS/rest/jio/saveUserLanguage";

    /* get open doctor detail */
    String API_GET_OPEN_DOCTOR_DETAIL_FOR_PROVIDER = "/JioMobileWS/rest/jio/getOpenDoctorDetailForProvider";

    /* get open document upload url */
    String API_GET_OPEN_DOCUMENT_UPLOAD_URL = "JioMobileWS/rest/jio/getOpenDocumentUploadURL";

    /* save open doctor document */
    String API_SAVE_OPEN_DOCTOR_DOCUMENT_GUID = "JioMobileWS/rest/jio/saveOpenDoctorDocument";
}