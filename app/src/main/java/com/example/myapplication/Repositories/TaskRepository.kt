package com.example.myapplication.Repositories

import com.example.myapplication.ApiResponse
import com.example.myapplication.Models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskRepository {
    //Poner el id de la persona
    @GET("api/...")
    fun getTaskInProcess(@Body body: UserLogin): Call<ApiResponseTask>
}