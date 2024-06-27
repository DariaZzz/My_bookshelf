package com.example.server_test;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.server_test.model.User;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shelf extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


    }

    public void my_profile(View view){
        Intent intent = new Intent(Shelf.this, MyProfile.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void bookshelf(View view){

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);



        Intent intent = new Intent(Shelf.this, Bookshelf.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void books_later(View view){
        Intent intent = new Intent(Shelf.this, LaterBooks.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}

