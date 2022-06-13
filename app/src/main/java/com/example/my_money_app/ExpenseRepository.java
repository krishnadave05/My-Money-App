package com.example.my_money_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ExpenseRepository {
    private ExpenseDao mExpenseDao;
    private LiveData<List<Expense>> mAllExpenses;

    ExpenseRepository(Application application) {
        ExpenseDatabase db = ExpenseDatabase.getDatabase(application);
        mExpenseDao = db.expenseDao();
        mAllExpenses = mExpenseDao.getExpenses();
    }

    LiveData<List<Expense>> getAllExpenses() {
        return mAllExpenses;
    }

    void insert(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            mExpenseDao.insert(expense);
        });
    }

    void deleteAll(){
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            mExpenseDao.deleteAll();
        });
    }

}
