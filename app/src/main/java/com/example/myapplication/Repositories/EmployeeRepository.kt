package com.example.myapplication.Repositories

import com.example.myapplication.ApiResponse
import com.example.myapplication.Models.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface EmployeeRepository {
    @POST("https://10.0.2.2:44349/api/employee")
    fun postLogin(@Body body: UserLogin): Call<ApiResponse>
}

