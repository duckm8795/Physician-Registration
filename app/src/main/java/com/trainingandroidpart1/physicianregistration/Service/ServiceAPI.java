package com.trainingandroidpart1.physicianregistration.Service;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Response.SetSecurityPin.SetSecurityPinResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Admin on 6/28/2016.
 */
public interface ServiceAPI {
    /* base url */
    String BASE_URL = "http://dev.jiohealth.com:8081/";
    /*create account*/

    @POST(ServiceConfig.API_SET_SECURITY_PIN)
    Call<SetSecurityPinResponse> setSecurityPin(@Query(ProviderConstants.SET_SECURITY_PIN.TOKEN) String token,
                                                @Query(ProviderConstants.SET_SECURITY_PIN.USER_ID) String userID,
                                                @Query(ProviderConstants.SET_SECURITY_PIN.SECURITY_PIN) String securityPin

    );
    @POST(ServiceConfig.API_CREATE_PROVIDER_ACCOUNT)
    Call<CreateProviderAccountResponse> createProviderAccount(
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.DISPLAY_UNIT) String displayUnit,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.EMAIL) String email,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.FIRST_NAME) String firstName,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.LANGUAGE_CODE) String languageCode,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.LAST_NAME) String lastName,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.PASSWORD) String password,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.REGION_CODE) String regionalCode,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.GENDER) String gender,
            @Query(ProviderConstants.CREATE_PROVIDER_ACCOUNT.TIME_ZONE_NAME) String timeZoneName
    );
    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
