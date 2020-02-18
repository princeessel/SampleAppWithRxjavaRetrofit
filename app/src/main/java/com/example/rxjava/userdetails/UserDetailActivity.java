package com.example.rxjava.userdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rxjava.R;


public class UserDetailActivity extends AppCompatActivity {

    private static final String EXTRA_REAL_NAME = "EXTRA_REAL_NAME";
    private static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private static final String EXTRA_USER_EMAIL = "EXTRA_USER_EMAIL";
    private static final String EXTRA_USER_ADDRESS = "EXTRA_USER_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        String stringName = getIntent().getStringExtra(EXTRA_REAL_NAME);
        String stringEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        String stringUserName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String stringId = getIntent().getStringExtra(EXTRA_USER_ID);
        String stringAddress = getIntent().getStringExtra(EXTRA_USER_ADDRESS);

        Log.d("NameOtisEssel", "onCreate: " + stringName);
        Log.d("EmailOvertoOtisEssel", "onCreate: " + stringEmail);
        Log.d("UsernameOvertoOtisEssel", "onCreate: " + stringUserName);
        Log.d("IdOvertoOtisEssel", "onCreate: " + stringId);
        Log.d("AddressOvertoOtisEssel", "onCreate: " + stringAddress);
    }
}
