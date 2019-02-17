package com.riztech.webservicedemo.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.riztech.webservicedemo.EmployeeListAdapter;
import com.riztech.webservicedemo.R;
import com.riztech.webservicedemo.models.BaseResponse;
import com.riztech.webservicedemo.models.Employee;
import com.riztech.webservicedemo.service.ApiClient;
import com.riztech.webservicedemo.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    ApiInterface apiInterface;
    ProgressBar progress;
    RecyclerView rvEmployeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getDataFromServer();
    }

    private void initializeView() {
        progress = findViewById(R.id.progress);
        rvEmployeeList = findViewById(R.id.rvEmployeeList);
    }

    private void getDataFromServer() {
        progress.setVisibility(View.VISIBLE);
        Call<List<Employee>> employeesCall = apiInterface.getAllEmployees();
        employeesCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                progress.setVisibility(View.GONE);
                List<Employee> employees = response.body();
                String responseJson = new Gson().toJson(employees);
                showDataOnRecyclerView(employees);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                toast(t.getMessage());
            }
        });
    }

    private void showDataOnRecyclerView(List<Employee> employees) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvEmployeeList.setLayoutManager(layoutManager);

        rvEmployeeList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(employees);
        rvEmployeeList.setAdapter(employeeListAdapter);


    }

    public void deleteEmployee(String empId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiInterface.deleteEmployee(empId);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                toast(baseResponse.getMessage());
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                toast(t.getMessage());
            }
        });
    }
}
