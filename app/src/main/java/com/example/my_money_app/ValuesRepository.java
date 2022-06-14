package com.example.my_money_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ValuesRepository {
    private ValuesDao mValuesDao;
    private LiveData<List<Values>> mAllValues;

    ValuesRepository(Application application) {
        ValuesDatabase db = ValuesDatabase.getDatabase(application);
        mValuesDao = db.valuesDao();
        mAllValues = mValuesDao.getValues();
    }

    LiveData<List<Values>> getAllValues() {
        return mAllValues;
    }

    void insert(Values values) {
        ValuesDatabase.databaseWriteExecutor.execute(() -> {
            mValuesDao.insert(values);
        });
    }

    void deleteAll(){
        ValuesDatabase.databaseWriteExecutor.execute(() -> {
            mValuesDao.deleteAll();
        });
    }

}
