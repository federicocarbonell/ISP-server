package com.example.myapplication

import com.google.gson.annotations.SerializedName
import java.util.*

//esto se usa para renombrar los campos que me trae la api a campos con un nombre deseado
//la api me devuelve status y message, yo quiero que se llamen status e images


data class UserLogged(
    @SerializedName("id") var id: Integer,
    @SerializedName("name") var name: String,
)

data class Job(
    @SerializedName("id") var id: Integer,
    @SerializedName("product") var product: Product,
    @SerializedName("state") var state: Integer,
    @SerializedName("description") var description: String,
    @SerializedName("time") var time: String,
    @SerializedName("direction") var direction: String,
)

data class Product(
    @SerializedName("id") var id: Integer,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("year") var year: Integer,
)

data class Report(
    @SerializedName("id") var id: Integer,
    @SerializedName("productName") var productName: String,
    @SerializedName("visitDate") var visitDate: String,
    @SerializedName("employeeName") var employeeName: String,
    @SerializedName("summary") var summary: String,
    @SerializedName("detail") var detail: String,
    @SerializedName("comment") var comment: String,
    @SerializedName("images") var images: List<String>,
)