package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener{
    Button resultsBtn;
    Button returnBtn;
    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;
    private LocalDate selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        resultsBtn = findViewById(R.id.results_button);
        returnBtn = findViewById(R.id.return_button);
        //onClick for button
        resultsBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, ResultActivity.class);
            startActivity(i);
        });
        returnBtn.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(i);
        });
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7 );
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calenderAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i =1; i <=32; i++ ){
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            }
            else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);

    }

    private void initWidgets() {
        calenderRecyclerView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }
    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    @Override
    public void onItemClick(int position, String dayText) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            String transferMessage = dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
             //Transfer date to input
            Bundle bundle = new Bundle();
            bundle.putString("date_key", transferMessage);
            Intent i = new Intent(DashboardActivity.this, Input.class);
            i.putExtras(bundle);
            startActivity(i);

    }
}