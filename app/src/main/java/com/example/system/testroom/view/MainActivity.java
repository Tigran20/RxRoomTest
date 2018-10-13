package com.example.system.testroom.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.system.testroom.R;
import com.example.system.testroom.adapter.DbAdapter;
import com.example.system.testroom.bd.App;
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

        adapter = new DbAdapter(this);

        App.getInstance(this).taskDao().getAll()
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

            Completable.fromAction(() -> App.getInstance(this).taskDao()
                    .insert(employee))
                    .subscribeOn(Schedulers.io())
                    .subscribe();

            App.getInstance(this).taskDao().getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newData -> {
                        adapter.getEmployees();
                        adapter.setData(newData);
                    });
        });

    }

}
