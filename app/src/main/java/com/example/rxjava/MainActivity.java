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

    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

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

        fetchUsers();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra(EXTRA_USER_ID, users.get(position).getId().toString());
        startActivity(intent);
    }

    public void showUsers(ArrayList<User> users) {
        userAdapter.setUserList(users);
    }

    private void fetchUsers() {

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