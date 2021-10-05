package com.example.myapplication

import com.google.gson.annotations.SerializedName

//esto se usa para renombrar los campos que me trae la api a campos con un nombre deseado
//la api me devuelve status y message, yo quiero que se llamen status e images


data class ApiResponse(
    @SerializedName("id") var id: Integer,
    @SerializedName("name") var name: String,
)