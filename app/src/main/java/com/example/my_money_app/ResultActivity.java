package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
//This class calculates the amount of money made weekly by adding hours studied and hours worked
//while also subtracting expenses. Every Sunday at 12AM these values are all reset to 0

public class ResultActivity extends AppCompatActivity {
TextView title;
TextView hoursWorked;
TextView hoursStudied;
TextView moneyMade;
TextView expenses;
TextView total;
double studied;
double worked;
static double expen;
final String TAG ="Day is: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ValuesViewModel valuesViewModel = new ViewModelProvider(this).get(ValuesViewModel.class);
        //Calendar to track day
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Log.i(TAG, String.valueOf(day));


        //Every sunday at 12AM the weekly values reset to 0
        if(day==1 && hour == 0 && minute == 0){
            worked = 0;
            studied = 0;
            expen = 0;
            valuesViewModel.delete();
        }

        //instantiate textviews
        hoursWorked = findViewById(R.id.hours_worked);
        hoursStudied = findViewById(R.id.hours_studied);
        moneyMade = findViewById(R.id.money_made);
        expenses = findViewById(R.id.expenses);
        total = findViewById(R.id.this_week_total);

        //get bundle data from Input activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String stringWorked = bundle.getString("work");
            String stringStudied = bundle.getString("study");
            String stringExpense = bundle.getString("expense");

            //imported double values from input
            double workedInput = Double.parseDouble(stringWorked);
            double studiedInput = Double.parseDouble(stringStudied);
            double expenInput = Double.parseDouble(stringExpense);

            //add imported values to current values
            addWorkHours(workedInput);
            addStudyHours(studiedInput);
            addExpenses(expenInput);
        }


        Values valuesObject = new Values();
        valuesObject.setWorkHours(worked);
        valuesObject.setStudyHours(studied);
        valuesObject.setExpenses(expen);
        valuesViewModel.insert(valuesObject);

         Observer<List<Values>> valueObserver = valueList -> {
             double sumWork = 0;
             double sumStudy = 0;
             double sumExpense = 0;
             for(int i = 0; i < valueList.size(); i++){
                 sumWork += valueList.get(i).getWorkHours();
                 sumStudy += valueList.get(i).getStudyHours();
                 sumExpense += valueList.get(i).getExpenses();
             }
             hoursWorked.setText("Hours Worked: " + String.valueOf(sumWork));
             hoursStudied.setText("Hours Studied: " +String.valueOf(sumStudy));
             expenses.setText("Expenses: $" +String.valueOf(sumExpense));

             //calculations
             double made = workedHours(sumWork) + studyHours(sumStudy);
             double weekTotal = made - sumExpense;

             //display on textViews
             moneyMade.setText("Money made: $" + String.valueOf(made));
             total.setText("Weekly Total: $" +String.valueOf(weekTotal));
         };

        valuesViewModel.getAllExpenses().observe(this, valueObserver);

    }

    //temporary calculating method assuming 20$/hr
    public double workedHours(double hours){
        double pay = hours * 20;
        return pay;
    }
    //temporary calculating method assuming 100$/hr
    public double studyHours(double hours){
        double pay = hours * 100;
        return pay;
    }
    //add working hours
    public void addWorkHours(double hours){
        this.worked = this.worked + hours;
    }
    //add study hours
    public void addStudyHours(double hours){
        this.studied = this.studied + hours;
    }
    //add expenses
    public void addExpenses(double expenses){
        this.expen = this.expen + expenses;
    }

    //Currency Format to Dollars
    public void format(double input){
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        defaultFormat.format(input);
    }
}