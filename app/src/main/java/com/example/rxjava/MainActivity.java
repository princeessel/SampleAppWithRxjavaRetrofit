package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.rxjava.userdetails.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private static final String EXTRA_REAL_NAME = "EXTRA_REAL_NAME";
    private static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private static final String EXTRA_USER_EMAIL = "EXTRA_USER_EMAIL";
    private static final String EXTRA_USER_ADDRESS = "EXTRA_USER_ADDRESS";
    private static final String EXTRA_USER_CITY = "EXTRA_USER_CITY";
    private static final String EXTRA_USER_PHONENUMBER = "EXTRA_USER_PHONENUMBER";

    ArrayList<User> users = new ArrayList<>();

    private RecyclerView recyclerView;

    public UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(MainActivity.this, users);
        recyclerView.setAdapter(userAdapter);

        userAdapter.setOnClickListener(MainActivity.this);

        fetchData();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra(EXTRA_USER_ID, users.get(position).getId().toString());
        intent.putExtra(EXTRA_USER_NAME, users.get(position).getUsername());
        intent.putExtra(EXTRA_REAL_NAME, users.get(position).getName());
        intent.putExtra(EXTRA_USER_EMAIL, users.get(position).getEmail());
        intent.putExtra(EXTRA_USER_ADDRESS, users.get(position).getAddress().getStreet());
        intent.putExtra(EXTRA_USER_CITY, users.get(position).getAddress().getCity());
        intent.putExtra(EXTRA_USER_PHONENUMBER, users.get(position).getPhone());
        startActivity(intent);
    }

    public void showUsers(ArrayList<User> users) {

        userAdapter.setUserList(users);
    }

    private void fetchData() {

        RestApi.getInstance()
                .getUserLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> userList) {
                        users = new ArrayList<>(userList);
                        showUsers(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Otis Rxjava Testing", "apply: " + e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}