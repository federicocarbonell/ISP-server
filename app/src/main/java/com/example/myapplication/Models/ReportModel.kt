package com.example.myapplication.Models

import java.io.Serializable

data class ReportModel(
    var productId: Int ?= null,
    var employeeId: Int ?= null,
    var arrivedTime: String ?= null,
    var summary: String ?= null,
    var detail: String ?= null,
    var comment: String ?= null,
    var image: String ?= null,
) : Serializable

