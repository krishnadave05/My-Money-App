package com.example.my_money_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Values.class}, version = 2, exportSchema = false)
public abstract class ValuesDatabase extends RoomDatabase {

    public abstract ValuesDao valuesDao();

    private static volatile ValuesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ValuesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ValuesDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ValuesDatabase.class,
                            "values_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
