package com.example.myapplication.Repositories

import com.example.myapplication.Models.ReportModel
import com.example.myapplication.Report
import retrofit2.Call
import retrofit2.http.*

interface ReportRepository {
    @GET("report/{productId}")
    fun getProductReports(@Path("productId") productId: Int): Call<Array<Report>>
    @GET("report/details/{reportId}")
    fun getReportDetail( @Path("reportId") reportId: Int): Call<Report>
    @POST("report/{productId}")
    fun createReport(@Path("productId") productId: Int, @Body body: ReportModel): Call<Void>
}
