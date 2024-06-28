package com.example.server_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.server_test.model.Book;
import com.example.server_test.model.User;
import com.example.server_test.retrofit.BookApi;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBook extends AppCompatActivity {

    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_book);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

    }

    public void save(View view){
        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        EditText nameET = (EditText) findViewById(R.id.name);
        EditText authorET = (EditText) findViewById(R.id.author);
        EditText reviewET = (EditText) findViewById(R.id.review);
        EditText pagesET = (EditText) findViewById(R.id.pages);
        RatingBar ratingBarRB = (RatingBar) findViewById(R.id.rate);

        String name = nameET.getText().toString();
        String author = authorET.getText().toString();
        String review = reviewET.getText().toString();
        String pages = pagesET.getText().toString();
        String rate = String.valueOf(ratingBarRB.getRating());

        if(name.equals("") && author.equals("")){
            Toast.makeText(NewBook.this, "No name or author.", Toast.LENGTH_SHORT).show();
            return;
        }

        for(char b: pages.toCharArray()){
            String s = "123456789";
            if(s.indexOf(b) == -1){
                Toast.makeText(NewBook.this, "The number of pages is wrong.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setReview(review);
        book.setPages(pages);
        book.setRate(rate);

        bookApi.saveBook(book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
                String new_book = String.valueOf(response.body().getId());
                userApi.getUser(id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = new User();
                        if(response.body().getBooks() != null)
                            user.setBooks(response.body().getBooks() + new_book);
                        else
                            user.setBooks(new_book);
                        userApi.update(id, user).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Toast.makeText(NewBook.this, "Book has been succesfully saved", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewBook.this, Bookshelf.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(NewBook.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NewBook.this, "Save is failed.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void back(View view){
        Intent intent = new Intent(NewBook.this, Bookshelf.class);
        intent.putExtra("id", id);
        finish();
        startActivity(intent);
    }
}
