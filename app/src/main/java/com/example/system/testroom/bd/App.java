package com.example.system.testroom.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.system.testroom.model.Employee;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class App extends RoomDatabase {

    private static final String DATABASE_NAME = "database";
    public static App instance;

    public static App getInstance(Context context) {
        if (instance == null) {
            synchronized (new Object()) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        App.class, DATABASE_NAME).build();
            }
        }
        return instance;
    }

    public abstract EmployeeDao taskDao();
}
