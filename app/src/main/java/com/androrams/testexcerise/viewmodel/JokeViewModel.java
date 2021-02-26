package com.androrams.testexcerise.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.androrams.testexcerise.network.model.Joke;
import com.androrams.testexcerise.repository.JokeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JokeViewModel extends BaseViewModel {

    static final String SUCCESS = "success";
    private JokeRepository repository;
    private List<Joke> jokeList;
    public boolean isJokeLoading;
    private MutableLiveData<Joke> jokeResponseLiveData = new MutableLiveData<>();

    public JokeViewModel(JokeRepository repository) {
        this.repository = repository;
        jokeList = new ArrayList<>();
    }

    public void getRandomJoke(Map<String, String> requestParams) {
        disposables.add(repository.getRandomJoke(requestParams).subscribe(apiResponse -> {
            if (apiResponse.getType().equalsIgnoreCase(SUCCESS)) {
                jokeResponseLiveData.postValue(apiResponse.getValue());
            } else {
                jokeResponseLiveData.postValue(null);
            }
        }, throwable -> {
            jokeResponseLiveData.postValue(null);
        }));
    }

    public List<Joke> getJokeList() {
        return jokeList;
    }

    public int getItemCount() {
        return jokeList.size();
    }

    public boolean isLastJokeLoadedFromApi() {
        return jokeList.get(jokeList.size() - 1).getJoke() != null;
    }

    public MutableLiveData<Joke> getJokeResponseLiveData() {
        return jokeResponseLiveData;
    }
}
