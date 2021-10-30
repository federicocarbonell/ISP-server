package com.example.myapplication.Repositories

import com.example.myapplication.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRepository {
    @GET("product/{productId}")
    fun getProductInfo(@Path("productId") productId: Int): Call<Product>
}