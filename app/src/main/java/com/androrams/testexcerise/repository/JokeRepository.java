package com.androrams.testexcerise.repository;

import com.androrams.testexcerise.network.APIInterface;
import com.androrams.testexcerise.network.model.ApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class JokeRepository {

    private APIInterface apiInterface;

    public JokeRepository(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<ApiResponse> getRandomJoke(Map<String, String> requestParams) {
        return apiInterface.getRandomJoke(requestParams)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
