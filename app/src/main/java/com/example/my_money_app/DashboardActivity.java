package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

public class DashboardActivity extends AppCompatActivity {
    Button resultsBtn;
    Button returnBtn;
    Button inputBtn;
    int year;
    int month;
    int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        resultsBtn = findViewById(R.id.results_button);
        returnBtn = findViewById(R.id.return_button);
        inputBtn = findViewById(R.id.input_button);
        //onClick for button
        resultsBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, ResultActivity.class);
            startActivity(i);
        });
        returnBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(i);
        });
        inputBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, Input.class);
            startActivity(i);
        });
    }
}