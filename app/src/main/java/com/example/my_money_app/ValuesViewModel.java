package com.example.my_money_app;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ValuesViewModel extends AndroidViewModel {

    private ValuesRepository mRepository;

    private final LiveData<List<Values>> mAllValues;

    public ValuesViewModel(Application application) {
        super(application);
        mRepository = new ValuesRepository(application);
        mAllValues = mRepository.getAllValues();
    }

    LiveData<List<Values>> getAllExpenses() { return mAllValues; }

    public void insert(Values values) { mRepository.insert(values); }

    public void delete() { mRepository.deleteAll();}
}
