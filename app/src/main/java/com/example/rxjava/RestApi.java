package com.example.rxjava;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {


    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final UserAPIClient userAPIClient;
    private static RestApi instance;
    private List<User> users;
    User user;


    public RestApi() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        userAPIClient = retrofit.create(UserAPIClient.class);


    }

    public static RestApi getInstance() {
        if (instance == null) {
            instance = new RestApi();
        }
        return instance;
    }


    public Observable<List<User>> getUserLists() {

        return userAPIClient.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
