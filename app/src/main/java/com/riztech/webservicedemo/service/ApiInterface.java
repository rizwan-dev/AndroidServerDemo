package com.riztech.webservicedemo.service;

import com.riztech.webservicedemo.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("myapp/api/employees")
    public Call<List<Employee>> getAllEmployees();

    @GET("myapp/api/employees/{employeeId}")
    public Call<Employee> findEmployee(@Path("employeeId") String employeeId);
}
