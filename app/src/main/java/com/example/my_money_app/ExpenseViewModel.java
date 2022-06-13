package com.example.my_money_app;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository mRepository;

    private final LiveData<List<Expense>> mAllExpenses;

    public ExpenseViewModel (Application application) {
        super(application);
        mRepository = new ExpenseRepository(application);
        mAllExpenses = mRepository.getAllExpenses();
    }

    LiveData<List<Expense>> getAllExpenses() { return mAllExpenses; }

    public void insert(Expense expense) { mRepository.insert(expense); }

    public void delete() { mRepository.deleteAll();}
}
