package com.example.server_test;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class Bookshelf extends AppCompatActivity {
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);



        String[] a = {"1", "2", "3", "4"};
        CustomAdapter customAdapter = new CustomAdapter(a);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapter);

    }

    public void new_book(View view){
        Intent intent = new Intent(Bookshelf.this, NewBook.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void back(View view){
        finish();
    }


}
