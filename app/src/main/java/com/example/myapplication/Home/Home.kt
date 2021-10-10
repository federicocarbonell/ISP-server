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

    private var layoutManagerDoing: RecyclerView.LayoutManager? = null
    private var adapterDoing: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    /*
    private var layoutManagerToDo: RecyclerView.LayoutManager? = null
    private var adapterToDo: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private var layoutManagerDone: RecyclerView.LayoutManager? = null
    private var adapterDone: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var userName = sharedPreference.getString("username","defaultName")
        val userTextView: TextView = findViewById(R.id.username) as TextView
        userTextView.text = "Bienvenido, " + userName

        val doingView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.doingJobs) as androidx.recyclerview.widget.RecyclerView
        layoutManagerDoing = LinearLayoutManager(this)
        doingView.layoutManager = layoutManagerDoing
        adapterDoing = RecyclerAdapter()
        doingView.adapter = adapterDoing
        /*
        val toDoView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.toDoJobs) as androidx.recyclerview.widget.RecyclerView
        layoutManagerToDo = LinearLayoutManager(this)
        doingView.layoutManager = layoutManagerToDo
        adapterToDo = RecyclerAdapter()
        doingView.adapter = adapterToDo

        val doneView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.doneJobs) as androidx.recyclerview.widget.RecyclerView
        layoutManagerDone = LinearLayoutManager(this)
        doingView.layoutManager = layoutManagerDone
        adapterDone = RecyclerAdapter()
        doingView.adapter = adapterDone
         */
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