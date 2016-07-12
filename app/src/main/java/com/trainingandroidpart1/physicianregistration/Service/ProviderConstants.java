package com.trainingandroidpart1.physicianregistration.Service;



/**
 * Created by Admin on 6/28/2016.
 */
public interface ProviderConstants  {
    interface CREATE_PROVIDER_ACCOUNT{
        String DISPLAY_UNIT = "displayUnit";
        String EMAIL = "email";
        String FIRST_NAME = "firstName";
        String LANGUAGE_CODE = "languageCode";
        String LAST_NAME = "lastName";
        String PASSWORD = "password";
        String REGION_CODE = "regionCode";
        String GENDER = "gender";
        String TIME_ZONE_NAME = "timeZoneName";
    }
    interface  SET_SECURITY_PIN{
        String TOKEN = "token";
        String USER_ID = "userID";
        String SECURITY_PIN = "securityPin";
    }
    interface  TEST{
        String USERID_TEST = "userIDTest";
        String TOKEN_TEST = "tokenTest";
    }
    interface VERIFY_PHYSICIAN{
        String COUNTRY_ID = "countryID";
        String LANGUAGE_CODE = "languageCode";
        String REGION_CODE = "regionCode";
        String TOKEN = "token";
        String USER_ID = "userID";
    }
    interface GET_PROFILE{
        String TOKEN = "token";
        String USER_ID = "userID";
    }
    interface GET_DEGREE_LIST{
        String COUNTRY_ID = "countryID";
    }
    interface GET_SPECIALTY_LIST{
        String LANGUAGE_CODE = "languageCode";
        String REGION_CODE = "regionCode";
        String TOKEN = "token";
        String USER_ID = "userID";
    }
    interface GET_LANGUAGE_LIST{
        String LANGUAGE_CODE = "languageCode";
        String REGION_CODE = "regionCode";
        String TOKEN = "token";
        String USER_ID = "userID";
    }

}
