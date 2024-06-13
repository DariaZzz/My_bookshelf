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

import com.example.server_test.retrofit.RetrofitService;
import com.example.server_test.retrofit.UserApi;

import java.util.Calendar;

public class Registration extends AppCompatActivity{

    private int id;
    private DatePicker mDatePicker;
    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Intent intent = getIntent();
        intent.getIntExtra("id", id);

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



}






