package com.example.meucardapio.model;

import com.example.meucardapio.service.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;

    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
