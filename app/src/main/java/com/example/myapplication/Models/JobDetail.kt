package com.example.myapplication.Models

import java.io.Serializable

// TODO: Agregar los demas campos.
data class JobDetail(
    var id: Integer?=null,
    var name: String?=null,
    var description: String?=null,
    var state: Integer?=null,
    var latitude: Integer?=null,
    var longitud: Integer?=null,
) : Serializable
