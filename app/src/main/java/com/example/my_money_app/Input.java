package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Input extends AppCompatActivity {
    Button addButton;
    EditText hoursWorked;
    EditText hoursStudied;
    EditText expenses;
    EditText ratePerHOurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        hoursWorked = findViewById(R.id.hours_worked);
        hoursStudied = findViewById(R.id.hours_studied);
        expenses = findViewById(R.id.Expenses);
        ratePerHOurs= findViewById(R.id.rate_per_hour);
        addButton = findViewById(R.id.Add);
        //make onclick with bundle to send data to result activity
        addButton.setOnClickListener(v -> {
            //intent to go to result activity
            Intent i = new Intent(Input.this, ResultActivity.class);

            //create bundle
            Bundle bundle = new Bundle();

            //convert input to strings
            String worked = hoursWorked.getText().toString();
            String studied = hoursStudied.getText().toString();
            String rate = ratePerHOurs.getText().toString();
            String expen = expenses.getText().toString();

            //put data in bundle
            bundle.putString("work",worked);
            bundle.putString("study",studied);
            bundle.putString("rate",rate);
            bundle.putString("expense",expen);

            i.putExtras(bundle);
            startActivity(i);
        });
    }
}



