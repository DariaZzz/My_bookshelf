package com.example.server_test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaterBooks extends AppCompatActivity {
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.later_books);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
    }
}
