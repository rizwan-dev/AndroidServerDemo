package com.riztech.webservicedemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.riztech.webservicedemo.listeners.EmployeeListener;
import com.riztech.webservicedemo.models.Employee;
import com.riztech.webservicedemo.view.MainActivity;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {

    List<Employee> employeeList;
    EmployeeListener employeeListener;

    public EmployeeListAdapter(List<Employee> employeeList, EmployeeListener listener) {
        this.employeeList = employeeList;
        this.employeeListener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee_list, parent, false);
        return new EmployeeViewHolder(view, employeeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.txtPhoneNumber.setText(employee.getPhoneNumber());
        holder.txtName.setText(employee.getName());
        holder.txtAddress.setText(employee.getAddress());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtAddress, txtPhoneNumber;
        ImageView imgDelete;

        public EmployeeViewHolder(@NonNull View itemView, final EmployeeListener employeeListener) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Employee employee = employeeList.get(getAdapterPosition());
                    employeeListener.onEmployeeDelete(employee.getId());
                    employeeList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
