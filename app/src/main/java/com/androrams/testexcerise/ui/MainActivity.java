package com.androrams.testexcerise.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.androrams.testexcerise.R;
import com.androrams.testexcerise.databinding.ActivityMainBinding;
import com.androrams.testexcerise.viewmodel.BaseViewModel;

import static com.androrams.testexcerise.utils.Constants.KEY_FIRST_NAME;
import static com.androrams.testexcerise.utils.Constants.KEY_LAST_NAME;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();
    }

    private void setupUI() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.firstNameEditText.getText().toString().isEmpty() || binding.lastNameEditText.getText().toString().isEmpty()) {
                    binding.submitButton.setEnabled(false);
                } else {
                    binding.submitButton.setEnabled(true);
                }
            }
        };
        binding.firstNameEditText.addTextChangedListener(textWatcher);
        binding.lastNameEditText.addTextChangedListener(textWatcher);
        binding.submitButton.setOnClickListener(v -> {
            validateAndSubmit();
        });
    }

    public void validateAndSubmit() {
        String firstName = binding.firstNameEditText.getText().toString();
        String lastName = binding.lastNameEditText.getText().toString();
        boolean isValidData = true;
        if (!firstName.matches("[a-zA-Z ]+")) {
            isValidData = false;
            binding.firstNameTextView.setError("Please Enter Only Alphabets");
        }

        if (!lastName.matches("[a-zA-Z ]+")) {
            isValidData = false;
            binding.lastNameLayout.setError("Please Enter Only Alphabets");
        }

        if (isValidData) {
            Intent intent = new Intent(this, RandomJokeActivity.class);
            intent.putExtra(KEY_FIRST_NAME, firstName);
            intent.putExtra(KEY_LAST_NAME, lastName);
            startActivity(intent);
        }
    }

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }
}