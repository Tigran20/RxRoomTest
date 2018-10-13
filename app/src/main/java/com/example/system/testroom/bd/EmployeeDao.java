package com.example.system.testroom.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.system.testroom.model.Employee;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    Flowable<List<Employee>> getAll();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getById(long id);

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
}
