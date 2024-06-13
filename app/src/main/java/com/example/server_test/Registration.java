package com.example.server_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.server_test.model.User;
import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity{

    private int id;
    private DatePicker mDatePicker;
    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.w_m, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        /////////////////////////////////////
        TextView dateTextView = findViewById(R.id.dateTextView);
        DatePicker datePicker = this.findViewById(R.id.datePicker);

        // Месяц начиная с нуля. Для отображения добавляем 1.
        datePicker.init(2005, 02, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Отсчет месяцев начинается с нуля. Для отображения добавляем 1.
                dateTextView.setText("Дата: " + view.getDayOfMonth() + "/" +
                        (view.getMonth() + 1) + "/" + view.getYear());

                // альтернативная запись
                // dateTextView.setText("Дата: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        });
    }

    public void sign_up_next(View view){

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        EditText phoneET = (EditText) findViewById(R.id.phone);
        EditText emailET = (EditText) findViewById(R.id.email);
        Spinner w_mS = (Spinner) findViewById(R.id.spinner);
        DatePicker dateDP = (DatePicker) findViewById(R.id.datePicker);


        String phone = phoneET.getText().toString();
        String email = emailET.getText().toString();
        String w_m = w_mS.getSelectedItem().toString();
        String year = Integer.toString(dateDP.getYear());
        String month = Integer.toString(dateDP.getMonth() + 1);
        String day = Integer.toString(dateDP.getDayOfMonth());
        String date = day + ' ' + month + ' ' + year;

        if(!(phone.startsWith("8") && phone.length() == 11 || phone.startsWith("+7") && phone.length() == 12)){
            Toast.makeText(Registration.this, "Wrong phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setW_m(w_m);
        user.setDate_of_birth(date);
        user.setId(id);

        userApi.update(user.getId(), user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(Registration.this, "The information is saved.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Shelf.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(Registration.this, "Something went wrong, try later", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void sign_up_skip(View view){
        Toast.makeText(Registration.this, "You can fill these fields later", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Registration.this, Shelf.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }



}






