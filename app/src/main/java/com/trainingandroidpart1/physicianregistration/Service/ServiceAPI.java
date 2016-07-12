package com.trainingandroidpart1.physicianregistration.Service;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.DegreeList;
import com.trainingandroidpart1.physicianregistration.Response.GetProfile.GetProfileResponse;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.SetSecurityPin.SetSecurityPinResponse;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.MainSpecialList;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;

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

    /* craete provider account */
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

    /* set security pin*/
    @POST(ServiceConfig.API_SET_SECURITY_PIN)
    Call<SetSecurityPinResponse> setSecurityPin(
            @Query(ProviderConstants.SET_SECURITY_PIN.USER_ID) long userID,
            @Query(ProviderConstants.SET_SECURITY_PIN.TOKEN) String token,
            @Query(ProviderConstants.SET_SECURITY_PIN.SECURITY_PIN) String securityPin
    );

    /* verify physician */
    @POST(ServiceConfig.VERIFY_PHYSICIAN)
    Call<Main> verify(
            @Query(ProviderConstants.VERIFY_PHYSICIAN.COUNTRY_ID)  long countryID,
            @Query(ProviderConstants.VERIFY_PHYSICIAN.LANGUAGE_CODE) String languageCode,
            @Query(ProviderConstants.VERIFY_PHYSICIAN.REGION_CODE) String regionCode,
            @Query(ProviderConstants.VERIFY_PHYSICIAN.TOKEN) String token,
            @Query(ProviderConstants.VERIFY_PHYSICIAN.USER_ID) long userID
    );

    /* get provider profile */
    @POST(ServiceConfig.GET_PROVIDER_PROFILE)
    Call<GetProfileResponse> getProfile(
            @Query(ProviderConstants.GET_PROFILE.TOKEN) String token,
            @Query(ProviderConstants.GET_PROFILE.USER_ID) long userID
    );
    /* get provider profile */
    @POST(ServiceConfig.GET_DEGREE_LIST)
    Call<DegreeList> getDegreeList(
            @Query(ProviderConstants.GET_DEGREE_LIST.COUNTRY_ID) String countryID

    );
    @POST(ServiceConfig.GET_SPECIALTY_LIST)
    Call<MainSpecialList> getSpecialtyList(
            @Query(ProviderConstants.GET_SPECIALTY_LIST.LANGUAGE_CODE) String languageCode,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.REGION_CODE) String regionCode,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.TOKEN) String token,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.USER_ID) long userID

    );
    @POST(ServiceConfig.GET_LANGUAGE_LIST)
    Call<MainLanguageListResponse> getLanguageList(
            @Query(ProviderConstants.GET_SPECIALTY_LIST.LANGUAGE_CODE) String languageCode,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.REGION_CODE) String regionCode,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.TOKEN) String token,
            @Query(ProviderConstants.GET_SPECIALTY_LIST.USER_ID) long userID

    );
    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
