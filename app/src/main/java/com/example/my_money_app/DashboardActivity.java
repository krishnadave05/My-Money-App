package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
Button resultsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        resultsBtn = findViewById(R.id.results_button);

        //onClick for results button
        resultsBtn.setOnClickListener(v -> {
                Intent i = new Intent(DashboardActivity.this, ResultActivity.class);
                startActivity(i);
        });
    }
}