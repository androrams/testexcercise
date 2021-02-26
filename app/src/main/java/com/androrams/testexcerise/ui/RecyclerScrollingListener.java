package com.androrams.testexcerise.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androrams.testexcerise.adapter.JokeAdapter;

public abstract class RecyclerScrollingListener extends RecyclerView.OnScrollListener {

    static final int PAGE_SIZE = 1;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = getLayoutManager().getItemCount();
        int firstVisibleItemPosition = getLayoutManager().findFirstCompletelyVisibleItemPosition();

        // if (!isLoading && !isLastPage) {
        if (((firstVisibleItemPosition + 1) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE)) {
            getAdapter().loadEmptyJoke();
            loadNextJoke();
        }
        //  }
    }

    public abstract void loadNextJoke();

    public abstract JokeAdapter getAdapter();

    public abstract LinearLayoutManager getLayoutManager();
}
