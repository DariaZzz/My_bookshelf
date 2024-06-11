package com.example.server_test;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.server_test.model.User;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sign_in(View view) {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        EditText usernameET = (EditText) findViewById(R.id.username);
        EditText passwordET = (EditText) findViewById(R.id.password);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        userApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                for (User user1: response.body()){
                    if(user1.getUsername().equals(username) && user1.getPassword().equals(password)) {
                        Intent intent = new Intent(MainActivity.this, Shelf.class);
                        intent.putExtra("id", user1.getId());
                        startActivity(intent);
                        return;
                    }
                }

                Toast.makeText(MainActivity.this, "Wrong login or password.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Taking list failed.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void sign_up(View view) {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        EditText usernameET = (EditText) findViewById(R.id.username);
        EditText passwordET = (EditText) findViewById(R.id.password);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();


        userApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();

                for (User user1: list){
                    if(user1.getUsername().equals(username)) {
                        Toast.makeText(MainActivity.this, "Username already exists, choose another one.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                passwordET.getText().clear();
                sendPOST(userApi, username, password);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Taking list failed.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    public void sendPOST(UserApi userApi, String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userApi.saveUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this, "You've been registered!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Shelf.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "The registration failed", Toast.LENGTH_SHORT).show();
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error occured", t);
            }
        });
    }

}