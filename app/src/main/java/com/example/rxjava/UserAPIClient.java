package com.example.rxjava;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPIClient {

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("users/{username}")
    Observable<GetUserDetails> getUserDetails(@Path("username") String username);

    @GET("posts")
    Observable<List<Post>> getPosts();
}