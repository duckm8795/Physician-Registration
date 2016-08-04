package com.trainingandroidpart1.physicianregistration.Service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kieuduc on 04/08/2016.
 */
public class ServiceManager {
    private static ServiceAPI serviceManager;

    public static ServiceAPI instance() {

        if (serviceManager == null) {
            serviceManager = getRetrofit(ServiceAPI.BASE_URL).create(ServiceAPI.class);
        }
        return serviceManager;
    }


    private static Retrofit getRetrofit(String BASE_URL){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

    }
}
