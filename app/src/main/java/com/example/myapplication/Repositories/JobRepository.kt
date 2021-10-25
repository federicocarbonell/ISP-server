package com.example.myapplication.Repositories

import com.example.myapplication.Job
import com.example.myapplication.Models.JobDetail
import retrofit2.Call
import retrofit2.http.*

interface JobRepository {
    @GET("job/{employeeId}/pending")
    fun getPendingJobs(@Path("employeeId") employeeId: Int): Call<Array<Job>>
    @GET("job/{employeeId}/inProcess")
    fun getInProcessJobs(@Path("employeeId") employeeId: Int): Call<Array<Job>>
    @GET("job/{employeeId}/finished")
    fun getFinishedJobs(@Path("employeeId") employeeId: Int): Call<Array<Job>>
    @PUT("job/{jobId}")
    fun updateJobState(@Path("jobId") jobId: Integer?, @Body body: JobDetail) : Call<Void>
}
