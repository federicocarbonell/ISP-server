package com.example.myapplication.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Job
import com.example.myapplication.R
import com.example.myapplication.Repositories.JobRepository
import com.example.myapplication.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var userName = sharedPreference.getString("username","defaultName")
        val userTextView: TextView = findViewById(R.id.username) as TextView
        userTextView.text = "Bienvenido, " + userName

        val recyclerView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

    }


    private fun getPendingJobs(intent: Intent){
        val request = ServiceBuilder.buildService(JobRepository::class.java)
        val call = request.getPendingJobs(1);
        call.enqueue(object: Callback<Array<Job>> {
            override fun onResponse(call: Call<Array<Job>>, response: Response<Array<Job>>) {
                val log = response
            }

            override fun onFailure(call: Call<Array<Job>>, t: Throwable) {
                val log = "All wrong"
            }
        })
    }


}