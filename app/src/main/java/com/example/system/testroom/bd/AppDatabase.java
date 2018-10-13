package com.example.system.testroom.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.system.testroom.model.Employee;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
}
