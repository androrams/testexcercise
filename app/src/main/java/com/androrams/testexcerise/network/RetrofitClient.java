package com.androrams.testexcerise.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androrams.testexcerise.network.APIInterface.BASE_URL;

public class RetrofitClient {

    private static Retrofit retrofitClient;

    public static Retrofit getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder().build())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitClient;
    }

    public static APIInterface getAPIInterface() {
        return getInstance().create(APIInterface.class);
    }
}
