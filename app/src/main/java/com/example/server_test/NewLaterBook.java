package com.example.server_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.server_test.databinding.NewLaterBookBinding;
import com.example.server_test.model.Book;
import com.example.server_test.model.User;
import com.example.server_test.retrofit.BookApi;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLaterBook extends AppCompatActivity {
    private int id;
    private NewLaterBookBinding newLaterBookBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newLaterBookBinding = NewLaterBookBinding.inflate(getLayoutInflater());
        setContentView(newLaterBookBinding.getRoot());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


    }

    public void save2(View view){
        if(newLaterBookBinding.author.getText() == null || newLaterBookBinding.author.getText() == null){
            Toast.makeText(NewLaterBook.this, "Some fields aren't filled.", Toast.LENGTH_SHORT).show();
            return;
        }

        Book laterBook = new Book();
        laterBook.setAuthor(newLaterBookBinding.author.getText().toString());
        laterBook.setName(newLaterBookBinding.name.getText().toString());

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        bookApi.saveBook(laterBook).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
                String new_book = String.valueOf(response.body().getId());
                userApi.getUser(id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = new User();
                        if(response.body().getLater_books() != null)
                            user.setLater_books(response.body().getLater_books() + new_book + " ");
                        else
                            user.setLater_books(new_book +" ");
                        userApi.update(id, user).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Toast.makeText(NewLaterBook.this, "Book has been succesfully saved", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewLaterBook.this, LaterBooks.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(NewLaterBook.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });
    }

}
