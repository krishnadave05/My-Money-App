package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
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
double expen;
final String TAG ="Day is: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
        }

        //instantiate textviews
        hoursWorked = findViewById(R.id.hours_worked);
        hoursStudied = findViewById(R.id.hours_studied);
        moneyMade = findViewById(R.id.money_made);
        expenses = findViewById(R.id.expenses);
        total = findViewById(R.id.this_week_total);

        //test values
        worked = 40;
        studied = 40;
        expen = 1200;

        //calculations
        double made = workedHours(worked) + studyHours(studied);
        double weekTotal = made - expen;

        //display on textViews
        hoursWorked.append(String.valueOf(worked));
        hoursStudied.append(String.valueOf(studied));
        moneyMade.append(String.valueOf(made));
        expenses.append(String.valueOf(expen));
        total.append(String.valueOf(weekTotal));
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
}