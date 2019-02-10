package com.riztech.webservicedemo.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.riztech.webservicedemo.R;
import com.riztech.webservicedemo.models.Employee;
import com.riztech.webservicedemo.service.ApiClient;
import com.riztech.webservicedemo.service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    ApiInterface apiInterface;
    EditText edtEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initializeView();

    }

    private void initializeView() {
        edtEmployee = findViewById(R.id.edtEmployee);
    }

    public void searchEmployee(final String empId) {
        Call<Employee> call = apiInterface.findEmployee(empId);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Employee employee = response.body();
                toast("Record found");
                showDataOnScreen(employee);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                toast(t.getMessage());
            }
        });
    }

    private void showDataOnScreen(Employee employee) {

    }

    public void search(View view) {
        String empId = edtEmployee.getText().toString().trim();
        if (!TextUtils.isEmpty(empId)) {
            searchEmployee(empId);
        }

    }
}
