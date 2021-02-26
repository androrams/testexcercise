package com.androrams.testexcerise.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.androrams.testexcerise.viewmodel.BaseViewModel;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    public VB binding;

    public BaseActivity() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding();
    }

    public void viewBinding() {
        this.binding = DataBindingUtil.setContentView(this, this.layoutId());
        this.binding.setLifecycleOwner(this);
    }

    @LayoutRes
    public abstract int layoutId();

    public abstract BaseViewModel getViewModel();
}
