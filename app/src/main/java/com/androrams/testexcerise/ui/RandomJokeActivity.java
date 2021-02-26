package com.androrams.testexcerise.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.androrams.testexcerise.R;
import com.androrams.testexcerise.adapter.JokeAdapter;
import com.androrams.testexcerise.databinding.ActivityRandomJokeBinding;
import com.androrams.testexcerise.network.RetrofitClient;
import com.androrams.testexcerise.repository.JokeRepository;
import com.androrams.testexcerise.viewmodel.JokeViewModel;
import com.androrams.testexcerise.viewmodel.ViewModelProviderFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import static com.androrams.testexcerise.utils.Constants.KEY_FIRST_NAME;
import static com.androrams.testexcerise.utils.Constants.KEY_LAST_NAME;
import static com.androrams.testexcerise.utils.Constants.KEY_LIMIT_TO;

public class RandomJokeActivity extends BaseActivity<ActivityRandomJokeBinding> {

    private LinearLayoutManager layoutManager;
    private JokeAdapter adapter;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setupRecyclerView();
        Intent intent = getIntent();
        if (intent != null) {
            firstName = intent.getStringExtra(KEY_FIRST_NAME);
            lastName = intent.getStringExtra(KEY_LAST_NAME);
        }
        setupViewPager();
        setupObservers();

    }

    private void setupViewPager() {
        adapter = new JokeAdapter(getViewModel());
        // load the first joke
        loadNextJoke(false);

        binding.viewPager.setAdapter(adapter);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //load next joke only if current item = lastItem
                if ((position == getViewModel().getItemCount() - 1 && !getViewModel().isJokeLoading && getViewModel().isLastJokeLoadedFromApi())) {
                    loadNextJoke(false);
                }
            }
        });

    }

    private void setupRecyclerView() {

        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.jokesRecyclerView.setLayoutManager(layoutManager);


        adapter = new JokeAdapter(getViewModel());
        binding.jokesRecyclerView.setAdapter(adapter);
        binding.jokesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        binding.jokesRecyclerView.addOnScrollListener(new RecyclerScrollingListener() {
            @Override
            public void loadNextJoke() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_FIRST_NAME, firstName);
                params.put(KEY_LAST_NAME, lastName);
                params.put(KEY_LIMIT_TO, "nerdy");
                getViewModel().getRandomJoke(params);
            }

            @Override
            public JokeAdapter getAdapter() {
                return adapter;
            }

            @Override
            public LinearLayoutManager getLayoutManager() {
                return layoutManager;
            }
        });
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.jokesRecyclerView);

    }

    private void setupObservers() {
        getViewModel().getJokeResponseLiveData().observe(this, joke -> {
            getViewModel().isJokeLoading = false;
            if (joke != null && joke.getJoke() != null) {
                toogleErrorState(false);
                adapter.setJokeAfterLoading(joke);
                //Load the second joke for continuity
                if (getViewModel().getItemCount() == 1) {
                    loadNextJoke(false);
                }
            } else {
                toogleErrorState(true);
                Snackbar.make(binding.viewPager, "Network Error, Try Again", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", v -> {
                            loadNextJoke(true);
                        }).show();
            }
        });
    }

    private void toogleErrorState(boolean isErrorState) {
        binding.viewPager.setVisibility(isErrorState ? View.GONE : View.VISIBLE);
        binding.errorMessage.setVisibility(isErrorState ? View.VISIBLE : View.GONE);

    }

    @Override
    public int layoutId() {
        return R.layout.activity_random_joke;
    }

    @Override
    public JokeViewModel getViewModel() {
        JokeRepository repository = new JokeRepository(RetrofitClient.getAPIInterface());
        Supplier<JokeViewModel> supplier = () -> new JokeViewModel(repository);
        ViewModelProviderFactory<JokeViewModel> factory = new ViewModelProviderFactory<>(JokeViewModel.class, supplier);
        return new ViewModelProvider(this, factory).get(JokeViewModel.class);
    }

    public void loadNextJoke(boolean isTryAgain) {
        getViewModel().isJokeLoading = true;
        if (!isTryAgain) {
            adapter.loadEmptyJoke();
        }
        Map<String, String> params = new HashMap<>();
        params.put(KEY_FIRST_NAME, firstName);
        params.put(KEY_LAST_NAME, lastName);
        params.put(KEY_LIMIT_TO, "nerdy");
        getViewModel().getRandomJoke(params);
    }

}
