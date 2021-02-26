package com.androrams.testexcerise.network;

import com.androrams.testexcerise.network.model.ApiResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIInterface {

    String BASE_URL = "https://api.icndb.com/";
    String RANDOM_JOKE = "jokes/random";


    @GET(RANDOM_JOKE)
    Single<ApiResponse> getRandomJoke(@QueryMap Map<String, String> parameters);

}
