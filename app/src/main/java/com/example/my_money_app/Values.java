package com.example.my_money_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "values_table")
public class Values {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "work_hours")
    private double workHours;

    @ColumnInfo(name = "study_hours")
    private double studyHours;

    @ColumnInfo(name = "expenses")
    private double expenses;

    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    public int getId(){
        return id;
    }

    public void setId(@NonNull int id){
        this.id = id;
    }

    public void setWorkHours(double hours){
        this.workHours = hours;
    }

    public double getWorkHours(){
        return workHours;
    }

    public void setStudyHours(double hours){
        this.studyHours = hours;
    }

    public double getStudyHours(){
        return studyHours;
    }

    public void setExpenses(double expenses){
        this.expenses = expenses;
    }

    public double getExpenses(){
        return expenses;
    }

    public void setDate(String date) {this.date = date;}

    public String getDate() {return date;}
}
