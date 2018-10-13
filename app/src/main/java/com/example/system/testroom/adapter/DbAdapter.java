package com.example.system.testroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.system.testroom.R;
import com.example.system.testroom.bd.App;
import com.example.system.testroom.model.Employee;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.ViewHolder> {

    private List<Employee> mEmployees;
    private Context context;

    public DbAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DbAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_item, viewGroup, false);
        return new DbAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DbAdapter.ViewHolder holder, int position) {
        Employee employee = mEmployees.get(position);
        holder.name.setText(employee.getName());
        holder.salary.setText(String.valueOf(employee.getSalary()));

        holder.delete.setOnClickListener(view -> {
            Completable.fromAction(() -> App.getInstance(context).taskDao()
                    .delete(employee))
                    .subscribeOn(Schedulers.io())
                    .subscribe();

            App.getInstance(context).taskDao().getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setData);
        });
    }

    public void setData(List<Employee> newData) {
        mEmployees = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEmployees == null) {
            return 0;
        }
        return mEmployees.size();
    }

    public List<Employee> getEmployees() {
        return mEmployees;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView salary;
        private Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            salary = itemView.findViewById(R.id.product_price);
            delete = itemView.findViewById(R.id.delete_btn);
        }
    }
}
