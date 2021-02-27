package com.androrams.testexcerise.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.androrams.testexcerise.viewmodel.BaseViewModel;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    public VB binding;
    private ConnectivityManager connectivityManager;
    private NetworkCallback networkCallback;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding();

        //registerNetworkCallbacks();
    }

    private void registerNetworkCallbacks() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    showNoInternetConnection();
                }
            };
            try {

                connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkRequest.Builder builder = new NetworkRequest.Builder();

                connectivityManager.registerNetworkCallback(builder.build(), networkCallback);

            } catch (Exception e) {

            }
        }

    }

    public void showNoInternetConnection() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("No Internet Connection");
        alertDialog.setMessage("Please check the internet connectivity and try again");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void viewBinding() {
        this.binding = DataBindingUtil.setContentView(this, this.layoutId());
        this.binding.setLifecycleOwner(this);
    }

    @LayoutRes
    public abstract int layoutId();

    public abstract BaseViewModel getViewModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (connectivityManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                connectivityManager.unregisterNetworkCallback(networkCallback);
//            }
//        }
    }
}
