package com.example.rxjava;

import androidx.annotation.NonNull;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {


    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final UserAPIClient userAPIClient;
    private static RestApi instance;

    public RestApi() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        userAPIClient = retrofit.create(UserAPIClient.class);


    }

    @NonNull
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

    public Observable<User> getUserDetails(String id) {

        return userAPIClient.getUserDetails(id);
    }

    public Observable<List<Album>> getUserAlbums(String id) {

        return userAPIClient.getAlbums(id);
    }

    public Observable<List<Photo>> getUserPhotos(String id) {

        return userAPIClient.getPhotos(id);
    }

    public Observable<List<Post>> getUserPostDetails(String id) {

        return userAPIClient.getPosts(id);
    }
}
