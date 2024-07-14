package com.example.server_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.databinding.LaterBooksBinding;
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

public class LaterBooks extends AppCompatActivity {
    private int id;
    private String books;
    private ArrayList<Book> books_info;
    private LaterBooksBinding laterBooksBinding;
    Boolean is_chosen = false;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LaterBooksBinding laterBooksBinding = LaterBooksBinding.inflate(getLayoutInflater());
        this.laterBooksBinding = laterBooksBinding;
        setContentView(laterBooksBinding.getRoot());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                recyclerStart(response.body().getLater_books());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LaterBooks.this, "The user is not found", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void recyclerStart(String books) {
        if (books != null && !books.isEmpty()) {

            this.books = books;

            RetrofitService retrofitService = new RetrofitService();
            BookApi laterBookApi = retrofitService.getRetrofit().create(BookApi.class);

            String[] parsedBooks = books.split(" ");

            laterBookApi.getAllBooks().enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                    if(response.body() != null) {
                        List<Book> books_info = response.body();
                        ArrayList<Book> result = new ArrayList<>();
                        for (String num : parsedBooks) {
                            for (Book book : books_info) {
                                if (Objects.equals(book.getId(), Integer.valueOf(num))) {
                                    result.add(book);
                                    break;
                                }
                            }
                        }
                        main_act(result);
                    }

                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {

                }
            });


        }
    }


    public void addInfo(Book book) {
        books_info.add(book);
    }

    public void main_act(ArrayList<Book> a) {
        books_info = a;

        String[] parsedBooks = books.split(" ");
        ArrayList<String> names = new ArrayList<String>();

        for (Book book : books_info) {
            names.add(book.getName());
        }

        String[] namesArr = new String[names.size()];
        names.toArray(namesArr);

        CustomAdapter2 customAdapter = new CustomAdapter2(namesArr, parsedBooks);

        RecyclerView recyclerView = laterBooksBinding.recyclerView1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapter);
    }

    public void new_later_book(View view){
        Intent intent = new Intent(LaterBooks.this, NewLaterBook.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void back_and_save(View view){
        if(books_info.size() != 0){
            request(0);
        }
        finish();
    }

    public void request(int index){
        Boolean flag = true;
        if(index == books_info.size()){
            flag = false;
        }
        if(flag) {
            SwitchCompat switchCompat = (SwitchCompat) findViewById(books_info.get(index).getId());
            int new_id = books_info.get(index).getId();

            if (switchCompat.isChecked()) {
                RetrofitService retrofitService = new RetrofitService();
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

                userApi.getUser(id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        ArrayList<String> lb;
                        String updateLb = "", books1 = "";
                        User user = response.body();
                        User updateUser = new User();

                        lb = new ArrayList<String>(Arrays.asList(user.getLater_books().split(" ")));
                        books1 = user.getBooks();

                        for (String id : lb) {
                            if (!id.equals(String.valueOf(new_id))) {
                                updateLb = updateLb + id + " ";
                            }
                        }

                        updateUser.setLater_books(updateLb);
                        if (books1 != null)
                            updateUser.setBooks(books1 + new_id + " ");
                        else
                            updateUser.setBooks(new_id + " ");

                        userApi.update(id, updateUser).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Toast.makeText(LaterBooks.this, "The book has been successfully postponed", Toast.LENGTH_SHORT).show();
                                request(index+1);
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
            else
                request(index+1);

        }
    }
}

