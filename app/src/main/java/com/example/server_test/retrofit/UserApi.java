package com.example.server_test.retrofit;

import com.example.server_test.model.Book;
import com.example.server_test.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") int id);



    @GET("/users")
    Call<List<User>> getAllUsers();

    @POST("/users/save")
    Call<User> saveUser(@Body User user);

    @PUT("/users/{id}")
    Call<Boolean> update(@Path("id") int id, @Body User user);

}
