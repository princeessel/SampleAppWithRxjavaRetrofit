package com.example.rxjava.userdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.Post;
import com.example.rxjava.PostAdapter;
import com.example.rxjava.R;
import com.example.rxjava.RestApi;
import com.example.rxjava.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserDetailActivity extends AppCompatActivity {

    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private ArrayList<Post> posts = new ArrayList<>();

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

    @BindView(R.id.postRecyclerView)
    RecyclerView postRecyclerView;

    public PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter();
        postRecyclerView.setAdapter(postAdapter);

        loadUserData();

    }

    private void loadUserData() {
        String stringId = getIntent().getStringExtra(EXTRA_USER_ID);

        loadUserDetails(stringId);

    }

    @OnClick(R.id.user_post_btn)
    protected void postButtonClicked() {
        String userId = getIntent().getStringExtra(EXTRA_USER_ID);
        loadPost(userId);
    }

    void loadPost(String id) {

        RestApi.getInstance()
                .getUserPostDetails(id)
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Post> postList) {
                        posts = (ArrayList<Post>) postList;

                        setPost(posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Otis", "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void loadUserDetails(String id) {
        RestApi.getInstance().getUserDetails(id)
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        showUserDetails(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void showUserDetails(User user) {
        userName.setText(user.getUsername());
        userId.setText(String.valueOf(user.getId()));
        userAddress.setText(user.getAddress().getStreet());
        userRealName.setText(user.getName());
        userWebsite.setText(user.getWebsite());
        userCompany.setText(user.getCompany().getName());
        userCity.setText(user.getAddress().getCity());
        userPhoneNumber.setText(user.getPhone());
        userEmail.setText(user.getEmail());
    }

    void setPost(ArrayList<Post> posts) {
        postAdapter.setUserPosts(posts);
    }
}
