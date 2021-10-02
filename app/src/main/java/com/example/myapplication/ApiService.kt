package com.example.myapplication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("employee/{id}")
    fun getUsers(@Query("id") id: Int): Call<ApiResponse>
}