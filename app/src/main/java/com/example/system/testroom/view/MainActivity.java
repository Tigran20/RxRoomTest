package com.example.system.testroom.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.system.testroom.R;
import com.example.system.testroom.adapter.DbAdapter;
import com.example.system.testroom.bd.App;
import com.example.system.testroom.bd.AppDatabase;
import com.example.system.testroom.bd.EmployeeDao;
import com.example.system.testroom.model.Employee;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private DbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);

        bd();
    }

    private void bd() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        adapter = new DbAdapter(this);

        employeeDao.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newData -> {
                    adapter.getEmployees();
                    adapter.setData(newData);
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(view -> {
            Employee employee = new Employee();
            employee.setName("Alex Troy");
            employee.setSalary(23);

            Completable.fromAction(() -> employeeDao
                    .insert(employee))
                    .subscribeOn(Schedulers.io())
                    .subscribe();

            employeeDao.getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newData -> {
                        adapter.getEmployees();
                        adapter.setData(newData);
                    });
        });

    }

}
