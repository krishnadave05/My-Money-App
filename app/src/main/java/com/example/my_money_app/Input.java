package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Input extends AppCompatActivity {
Button addBtn;
EditText hoursWorked;
EditText hoursStudied;
TextView moneyMade;
EditText expenses;
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


        resultButton.setOnClickListener(v -> {
            Intent i = new Intent(Input.this, ResultActivity.class);
            startActivity(i);
        });

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(Input.this, DashboardActivity.class);
            startActivity(i);
        });

        //make onclick with bundle to send data to result activity
        addBtn.setOnClickListener(v -> {
            //intent to go to result activity

                Intent i = new Intent(Input.this, DashboardActivity.class);
                i.putExtra("message", "Login Screen");
                startActivity(i);


                //create bundle
                Bundle bundle = new Bundle();

                //convert input to strings
                String worked = hoursWorked.getText().toString();
                String studied = hoursStudied.getText().toString();
                String expen = expenses.getText().toString();

                //put data in bundle
                bundle.putString("work", worked);
                bundle.putString("study", studied);
                bundle.putString("expense", expen);

                i.putExtras(bundle);
                startActivity(i);

        });
    }
//    boolean validateData() {
//        boolean flag = true;
//       // mNumber.getText().toString().length()>0
//        if (hoursStudied.getText().toString().length()>0){
//       // if (isEmpty(hoursStudied)) {
//            hoursStudied.setError("Password can't be empty!");
//            flag = false;
//        }
//        return flag;
//    }
//    boolean isEmpty(EditText text) {
//        CharSequence str = text.getText().toString();
//        return TextUtils.isEmpty(str);
//    }




//public void onSubmit(View view){
//    String worked = hoursWorked.getText().toString();
//    String studied = hoursStudied.getText().toString();
//    String expen = expenses.getText().toString();
//
//    if ( hoursWorked.equals("")|| hoursStudied.equals("") || expenses.equals("") ) {
//        // empty strings are not valid form input show a Toast to the user
//        Toast.makeText(getApplicationContext(),"complete all the necessary field", Toast.LENGTH_LONG).show();
//        return;
//    }
//    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//    startActivity(intent);
//}


}