package com.example.myapplication.Repositories

import com.google.gson.annotations.SerializedName

data class ApiResponseTask(
    @SerializedName("id") var id: Integer,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
)