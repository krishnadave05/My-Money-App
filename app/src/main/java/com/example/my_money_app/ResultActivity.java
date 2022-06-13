package com.example.my_money_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

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

        ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
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
            expenseViewModel.delete();
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
        //calculations
        double made = workedHours(worked) + studyHours(studied);
        double weekTotal = made - expen;

        //display on textViews
        hoursWorked.append(String.valueOf(worked));
        hoursStudied.append(String.valueOf(studied));
        moneyMade.append(String.valueOf(made));
        //expenses.append(String.valueOf(expen));
        total.append(String.valueOf(weekTotal));

        Expense expenseObject = new Expense(String.valueOf(expen));
        expenseViewModel.insert(expenseObject);

         Observer<List<Expense>> expenseObserver = expenseList -> {
             double sum = 0;
             for(int i = 0; i < expenseList.size(); i++){
                 sum += Double.parseDouble(expenseList.get(i).getExpense());
             }
             String newExpense = (expenseList.get(expenseList.size() - 1).getExpense());
             expenses.setText(String.valueOf(sum));
         };

        expenseViewModel.getAllExpenses().observe(this, expenseObserver);


    }

    //temporary calculating method assuming 20$/hr
    public double workedHours(double hours){
        hours = this.worked;
        double pay = hours * 20;
        return pay;
    }
    //temporary calculating method assuming 100$/hr
    public double studyHours(double hours){
        hours = this.studied;
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