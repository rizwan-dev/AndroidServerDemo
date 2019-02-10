package com.riztech.webservicedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.riztech.webservicedemo.R;
import com.riztech.webservicedemo.models.BaseResponse;
import com.riztech.webservicedemo.models.Employee;
import com.riztech.webservicedemo.service.ApiClient;
import com.riztech.webservicedemo.service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeActivity extends BaseActivity {

    EditText edtName, edtAddress, edtPhoneNumber, edtSalary, edtDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        initializeView();
    }

    private void initializeView() {
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDesignation = findViewById(R.id.edtDesignation);
        edtSalary = findViewById(R.id.edtSalary);
    }

    public void save(View view) {
        String name = edtName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String salary = edtSalary.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String designation = edtDesignation.getText().toString().trim();

        int salaryValue = Integer.parseInt(salary);

        Employee employee = new Employee();

        employee.setAddress(address);
        employee.setName(name);
        employee.setDesignation(designation);
        employee.setPhoneNumber(phoneNumber);
        employee.setSalary(salaryValue);

        addDataToServer(employee);

    }

    private void addDataToServer(Employee employee) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiInterface.addEmployee(employee);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                toast(baseResponse.getMessage());
                resetEditText();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                toast("Failed to add data");
            }
        });
    }

    private void resetEditText() {
        edtName.setText("");
        edtAddress.setText("");
        edtPhoneNumber.setText("");
        edtDesignation.setText("");
        edtSalary.setText("");
    }
}
