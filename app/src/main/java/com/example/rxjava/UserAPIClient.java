package com.example.rxjava;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface UserAPIClient {

    @GET("users")
    Observable<List<User>> getUsers();
}