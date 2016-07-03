package com.trainingandroidpart1.physicianregistration.Service;

/**
 * Created by Admin on 6/28/2016.
 */
public interface ServiceConfig {
    /*create account*/
    String API_CREATE_PROVIDER_ACCOUNT = "JioMobileWS/rest/jio/createProviderAccount";
    /* set security pin */
    String API_SET_SECURITY_PIN = "JioMobileWS/rest/jio/setSecurityPin";
    /* verify physician*/
    String VERIFY_PHYSICIAN = "JioMobileWS/rest/jio/getVerificationDocTypesByCountry";
    /* get provider profile*/
    String GET_PROVIDER_PROFILE = "JioMobileWS/rest/jio/getProviderProfile";
}