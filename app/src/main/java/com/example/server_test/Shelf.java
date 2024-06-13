package com.example.server_test;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

