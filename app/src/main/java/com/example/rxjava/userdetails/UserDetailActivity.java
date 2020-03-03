package com.example.rxjava.userdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.Post;
import com.example.rxjava.R;
import com.example.rxjava.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends AppCompatActivity {

    private static final String EXTRA_REAL_NAME = "EXTRA_REAL_NAME";
    private static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private static final String EXTRA_USER_EMAIL = "EXTRA_USER_EMAIL";
    private static final String EXTRA_USER_ADDRESS = "EXTRA_USER_ADDRESS";
    private static final String EXTRA_USER_CITY = "EXTRA_USER_CITY";
    private static final String EXTRA_USER_WEBSITE = "EXTRA_USER_WEBSITE";
    private static final String EXTRA_USER_COMPANY = "EXTRA_USER_COMPANY";
    private static final String EXTRA_USER_PHONENUMBER = "EXTRA_USER_PHONENUMBER";

    private ArrayList<Post> posts;

    private User user;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_id)
    TextView userId;

    @BindView(R.id.user_website)
    TextView userWebsite;

    @BindView(R.id.user_Email)
    TextView userEmail;

    @BindView(R.id.user_street_address)
    TextView userAddress;

    @BindView(R.id.user_city)
    TextView userCity;

    @BindView(R.id.user_phone)
    TextView userPhoneNumber;

    @BindView(R.id.user_company)
    TextView userCompany;

    @BindView(R.id.real_name)
    TextView userRealName;


    @BindView(R.id.user_post_btn)
    Button userPostButton;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        setUserData();


    }

    private void setUserData() {
        String stringName = getIntent().getStringExtra(EXTRA_REAL_NAME);
        String stringUserName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String stringId = getIntent().getStringExtra(EXTRA_USER_ID);
        String stringAddress = getIntent().getStringExtra(EXTRA_USER_ADDRESS);
        String stringCity = getIntent().getStringExtra(EXTRA_USER_CITY);
        String stringWebsite = getIntent().getStringExtra(EXTRA_USER_WEBSITE);
        String stringCompany = getIntent().getStringExtra(EXTRA_USER_COMPANY);
        String stringPhoneNumber = getIntent().getStringExtra(EXTRA_USER_PHONENUMBER);
        String stringEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);

        userName.setText(stringUserName);
        userId.setText(stringId);
        userAddress.setText(stringAddress);
        userRealName.setText(stringName);
        userWebsite.setText(stringWebsite);
        userCompany.setText(stringCompany);
        userCity.setText(stringCity);
        userPhoneNumber.setText(stringPhoneNumber);
        userEmail.setText(stringEmail);
    }

    @OnClick(R.id.user_post_btn)
    protected void postButtonClicked() {
    }
}
