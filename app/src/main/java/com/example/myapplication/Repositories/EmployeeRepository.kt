package com.example.myapplication.Repositories

import com.example.myapplication.Models.UserLogin
import com.example.myapplication.Repositories.ApiResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface EmployeeRepository {
    @POST("employee")
    fun postLogin(@Body body: UserLogin): Call<ApiResponse>
}

