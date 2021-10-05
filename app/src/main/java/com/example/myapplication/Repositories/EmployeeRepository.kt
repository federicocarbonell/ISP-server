package com.example.myapplication.Repositories

import com.example.myapplication.ApiResponse
import com.example.myapplication.Models.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface EmployeeRepository {
    @POST("employee")
    fun postLogin(@Body body: UserLogin): Call<ApiResponse>
}

