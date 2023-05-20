package com.example.my_money_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ValuesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Values values);

    @Query("SELECT * FROM `values_table`")
    LiveData<List<Values>> getValues();

    @Query("DELETE FROM `values_table`")
    void deleteAll();
}
