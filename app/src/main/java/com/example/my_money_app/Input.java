package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Input extends AppCompatActivity {
Button addBtn;
EditText hoursWorked;
EditText hoursStudied;
EditText expenses;
String dateString;
Button resultButton;
Button backButton;
//ToDo: EditText boxes must only take numerical input.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        //instantiate elements
        hoursWorked = findViewById(R.id.hours_worked_input);
        hoursStudied = findViewById(R.id.hours_studied_input);
        expenses = findViewById(R.id.ExpensesField);
        addBtn = findViewById(R.id.Add);
        resultButton = findViewById(R.id.results_button);
        backButton = findViewById(R.id.return_button);



//        resultButton.setOnClickListener(v -> {
//            onSubmit();
//            Intent i = new Intent(Input.this, ResultActivity.class);
//            i.putExtra("hoursWorked",hoursWorked.getText().toString());
//            i.putExtra("hoursStudied",hoursStudied.getText().toString());
//            startActivity(i);
//        });

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hoursWorked.getText().toString().isEmpty()==false && hoursStudied.getText().toString().isEmpty()==false){
                    Intent i = new Intent(Input.this, ResultActivity.class);
                    i.putExtra("hoursWorked",hoursWorked.getText().toString());
                    i.putExtra("hoursStudied",hoursStudied.getText().toString());
                    startActivity(i);
                }else{
                    Toast.makeText(Input.this, "Enter data in fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(Input.this, DashboardActivity.class);
            startActivity(i);
        });

        //make onclick with bundle to send data to result activity
        addBtn.setOnClickListener(v -> {
            //intent to go to result activity
            Intent i = new Intent(Input.this, ResultActivity.class);
            Intent x = new Intent(Input.this, DashboardActivity.class);
            //create bundle
            Bundle bundle = new Bundle();

            //convert input to strings
            String worked = hoursWorked.getText().toString();
            String studied = hoursStudied.getText().toString();
            String expen = expenses.getText().toString();

            Bundle getDate = getIntent().getExtras();
            if(getDate.equals(null)){
                this.dateString = "null";}
            else{dateString = getDate.getString("date_key");}

            //put data in bundle
            bundle.putString("work",worked);
            bundle.putString("study",studied);
            bundle.putString("expense",expen);
            bundle.putString("date",dateString);
            i.putExtras(bundle);
            startActivity(i);
        });
    }



public boolean onSubmit(){
    String worked = hoursWorked.getText().toString();
    String studied = hoursStudied.getText().toString();
    String expen = expenses.getText().toString();

    if ( hoursWorked.equals("")|| hoursStudied.equals("") || expenses.equals("") ) {
        // empty strings are not valid form input show a Toast to the user
        Toast.makeText(getApplicationContext(),"complete all the necessary field", Toast.LENGTH_LONG).show();
        return false;
    }
    return true;
}


}