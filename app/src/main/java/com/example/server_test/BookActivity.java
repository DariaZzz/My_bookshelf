package com.example.server_test;


import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.server_test.databinding.BookBinding;
import com.example.server_test.model.Book;
import com.example.server_test.model.User;
import com.example.server_test.retrofit.BookApi;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private int book_id;
    private int user_id;
    private TextView textView;
    private BookBinding bookBinding1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookBinding bookBinding = BookBinding.inflate(getLayoutInflater());
        setContentView(bookBinding.getRoot());
        bookBinding1 = bookBinding;

        textView = bookBinding.progressListener;
        bookBinding.progress.setOnSeekBarChangeListener(this);

        Intent intent = getIntent();
        book_id = intent.getIntExtra("book_id", 0);
        user_id = intent.getIntExtra("user_id", 0);

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
                if(book.getProgress_pages()!=null)
                    bookBinding.progress.setProgress(Integer.valueOf(book.getProgress_pages()));
                if(!book.getPages().equals(""))
                    bookBinding.progress.setMax(Integer.valueOf(book.getPages()));
                else
                    bookBinding.progress.setMax(200);

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });


    }

    public void upd(View view){


        Book book = new Book();

        book.setReview(bookBinding1.review.getText().toString());
        book.setRate(String.valueOf(bookBinding1.rate.getRating()));
        String pages = bookBinding1.pages.getText().toString();
        for(char b: pages.toCharArray()){
            String s = "0123456789";
            if(s.indexOf(b) == -1){
                Toast.makeText(BookActivity.this, "The number of pages is wrong.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        book.setProgress_pages(String.valueOf(bookBinding1.progress.getProgress()));
        book.setPages(bookBinding1.pages.getText().toString());

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        bookApi.update(book_id, book).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Intent intent = new Intent(BookActivity.this, Bookshelf.class);
                intent.putExtra("id", user_id);
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    public void deleteBook(View view){
        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        bookApi.delBook(book_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(BookActivity.this, "The book is succesfully deleted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(BookActivity.this, "The book can't be deleted. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });

        userApi.getUser(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String books = response.body().getBooks();
                String[] parsedBooks = books.split(" ");
                String newBooks = "";
                for(String b: parsedBooks){
                    if(!b.equals(String.valueOf(book_id)))
                        newBooks = newBooks + b +" ";
                }
                User user = new User();
                user.setBooks(newBooks);
                userApi.update(user_id, user).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toast.makeText(BookActivity.this, "Book was successfully deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, Bookshelf.class);
                        intent.putExtra("id", user_id);
                        startActivity(intent);
                        finish();
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

    public void back1(View view){
        Intent intent = new Intent(BookActivity.this, Bookshelf.class);
        intent.putExtra("id", user_id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView.setText(String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textView.setText(String.valueOf(seekBar.getProgress()));
    }
}
