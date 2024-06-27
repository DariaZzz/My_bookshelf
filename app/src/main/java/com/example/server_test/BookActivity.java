package com.example.server_test;


import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.server_test.databinding.BookBinding;
import com.example.server_test.model.Book;
import com.example.server_test.retrofit.BookApi;
import com.example.server_test.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {
    private int book_id;
    private int bookId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookBinding bookBinding = BookBinding.inflate(getLayoutInflater());
        setContentView(bookBinding.getRoot());


        Intent intent = getIntent();
        book_id = intent.getIntExtra("id", 0);

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        bookApi.getBook(book_id).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book book = response.body();
                bookBinding.name.setText(book.getName());
                bookBinding.author.setText(book.getAuthor());
                bookBinding.review.setText(book.getReview());
                bookBinding.rate.setRating(Float.valueOf(book.getRate()));
                bookBinding.pages.setText(book.getPages());

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });


    }

    public void upd(View view){


    }
}
