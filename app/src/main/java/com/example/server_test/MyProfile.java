package com.example.server_test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.server_test.databinding.MyProfileBinding;
import com.example.server_test.model.User;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {
    private int id;
    private MyProfileBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyProfileBinding binding = MyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.binding = binding;

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String not_filled = "Не указано";
                User user = response.body();
                binding.username.setText(user.getUsername());
                if(user.getW_m() != null)
                    binding.wM.setText(user.getW_m());
                else
                    binding.wM.setText(not_filled);

                if(user.getPhone()!=null)
                    binding.number.setText(user.getPhone());
                else
                    binding.number.setText(not_filled);

                if(user.getEmail()!=null)
                    binding.email.setText(user.getEmail());
                else
                    binding.email.setText(not_filled);

                if(!user.getBooks().isEmpty()){
                    String[] b = user.getBooks().split(" ");
                    binding.numOfBooks.setText(String.valueOf(b.length));
                }
                else
                    binding.numOfBooks.setText("0");

                binding.image.setImageResource(R.drawable.baseline_person);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyProfile.this, "User was not found.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void update(View view){
        Intent intent = new Intent(MyProfile.this, Registration.class);
        intent.putExtra("id", id);
        finish();
        startActivity(intent);

    }

    public void delUser(View view){


    }

    public void back2(View view){
        finish();
    }
}
