package com.example.my_money_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "expense")
    private String mExpense;

    public Expense(@NonNull String expense) {
        this.mExpense = expense;
    }
    public String getExpense(){
        return this.mExpense;
    }
}
