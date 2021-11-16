package com.example.myapplication.Models

import java.io.Serializable

data class ReportModel (
    var visitDate: String ?= null,
    var timeArrival: String ?= null,
    var timeResolution: String ?= null,
    var employeeName: String ?= null,
    var summary: String ?= null,
    var detail: String ?= null,
    var comment: String ?= null,
    var images: List<String> ?= null,
) : Serializable

