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

public interface BookApi {
    @GET("/book")
    Call<List<Book>> getAllBooks();



    @GET("/book/{id}")
    Call<Book> getBook(@Path("id") int id);

    @POST("/book/save")
    Call<Book> saveBook(@Body Book book);

    @PUT("/book/{id}")
    Call<Boolean> update(@Path("id") int id, @Body Book book);
}
