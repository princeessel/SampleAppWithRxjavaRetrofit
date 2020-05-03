package com.example.rxjava;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPIClient {

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("users/{id}")
    Observable<User> getUserDetails(@Path("id") String id);

    @GET("posts")
    Observable<List<Post>> getPosts(@Query("userId") String userId);

    @GET("albums")
    Observable<List<Album>> getAlbums(@Query("userId") String id);

    @GET("photos")
    Observable<List<Photo>> getPhotos(@Query("albumId") String albumId);
}