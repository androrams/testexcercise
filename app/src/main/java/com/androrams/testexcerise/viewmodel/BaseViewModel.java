package com.androrams.testexcerise.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading;
    protected CompositeDisposable disposables = new CompositeDisposable();

    public BaseViewModel() {
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.disposables.dispose();
    }
}
