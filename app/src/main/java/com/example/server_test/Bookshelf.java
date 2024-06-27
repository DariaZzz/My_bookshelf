package com.example.server_test;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.model.Book;
import com.example.server_test.model.User;
import com.example.server_test.retrofit.BookApi;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bookshelf extends AppCompatActivity {
    private int id;
    private String books;
    private ArrayList<Book> books_info;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                recyclerStart(response.body().getBooks());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Bookshelf.this, "The user is not found", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void recyclerStart(String books){
        if(books != null) {

            this.books = books;

            RetrofitService retrofitService = new RetrofitService();
            BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

            String[] parsedBooks = books.split(" ");


            bookApi.getAllBooks().enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    List<Book> books_info = response.body();
                    ArrayList<Book> result = new ArrayList<>();
                    for(String num: parsedBooks) {
                        for (Book book : books_info) {
                            if (Objects.equals(book.getId(), Integer.valueOf(num))) {
                                result.add(book);
                                break;
                            }
                        }
                    }
                    main_act(result);
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {

                }
            });




        }
    }


    public void addInfo(Book book){
        books_info.add(book);
    }

    public void main_act(ArrayList<Book> a){
        books_info = a;
        String[] parsedBooks = books.split(" ");
        ArrayList<String> names = new ArrayList<String>();

        for(Book book: books_info){
            names.add(book.getName());
        }

        String[] namesArr = new String[names.size()];
        names.toArray(namesArr);

        CustomAdapter customAdapter = new CustomAdapter(namesArr, parsedBooks);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapter);
    }



    public void new_book(View view){
        Intent intent = new Intent(Bookshelf.this, NewBook.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void back(View view){
        finish();
    }

    public void handler(View view){

        Intent intent = new Intent(Bookshelf.this, BookActivity.class);
        intent.putExtra("id", view.getId());
        startActivity(intent);

    }


}
