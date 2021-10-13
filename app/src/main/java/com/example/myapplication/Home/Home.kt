package com.example.myapplication.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    private var layoutManagerToDo: RecyclerView.LayoutManager? = null
    private var adapterToDo: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private var layoutManagerDone: RecyclerView.LayoutManager? = null
    private var adapterDone: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private var idUser = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var userName = sharedPreference.getString("username","defaultName")
        idUser = sharedPreference.getInt("userId",1)
        val userTextView: TextView = findViewById(R.id.username) as TextView
        userTextView.text = "Bienvenido, " + userName

        getPendingJobs(this);
        getInProcessJobs(this);
        getCompletedJobs(this);
    }

    private fun populateDoingJobs(context: Context, jobs: Array<Job>?){
        val doingView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.doingJobs) as androidx.recyclerview.widget.RecyclerView
        if(jobs == null){
            doingView.setVisibility(View.GONE)
        }else {
            doingView.setVisibility(View.VISIBLE)
            layoutManagerDoing = LinearLayoutManager(context)
            doingView.layoutManager = layoutManagerDoing
            adapterDoing = RecyclerAdapter(jobs) //Constructor
            doingView.adapter = adapterDoing
        }
    }

    private fun populateToDoJobs(context: Context, jobs: Array<Job>?){
        val toDoView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.toDoJobs) as androidx.recyclerview.widget.RecyclerView
        if(jobs == null){
            toDoView.setVisibility(View.GONE)
        }else {
            toDoView.setVisibility(View.VISIBLE)
            layoutManagerToDo = LinearLayoutManager(context)
            toDoView.layoutManager = layoutManagerToDo
            adapterToDo = RecyclerAdapter(jobs)
            toDoView.adapter = adapterToDo
        }
    }

    private fun populateDoneJobs(context: Context, jobs: Array<Job>?){
        val doneView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.doneJobs) as androidx.recyclerview.widget.RecyclerView
        if(jobs == null){
            doneView.setVisibility(View.GONE)
        }else {
            doneView.setVisibility(View.VISIBLE)
            layoutManagerDone = LinearLayoutManager(context)
            doneView.layoutManager = layoutManagerDone
            adapterDone = RecyclerAdapter(jobs)
            doneView.adapter = adapterDone
        }
    }

    private fun getPendingJobs(context: Context){
        val request = ServiceBuilder.buildService(JobRepository::class.java)
        val call = request.getPendingJobs(idUser);
        call.enqueue(object: Callback<Array<Job>> {
            val toDoTextView: TextView = findViewById(R.id.notToDoJobs) as TextView
            override fun onResponse(call: Call<Array<Job>>, response: Response<Array<Job>>) {
                if(response.code() == 200){
                    toDoTextView.setVisibility(View.GONE)
                    populateToDoJobs(context, response.body())
                }
                else{
                    populateToDoJobs(context,null)
                    toDoTextView.setVisibility(View.VISIBLE)
                    toDoTextView.text = "No tiene tareas pendientes."
                }
            }

            override fun onFailure(call: Call<Array<Job>>, t: Throwable) {
                populateToDoJobs(context,null)
                toDoTextView.setVisibility(View.VISIBLE)
                toDoTextView.text = "Imposible cargar las tareas"
            }
        })
    }

    private fun getInProcessJobs(context: Context){
        val request = ServiceBuilder.buildService(JobRepository::class.java)
        val call = request.getPendingJobs(idUser); //CAMBIAR
        call.enqueue(object: Callback<Array<Job>> {
            val doingTextView: TextView = findViewById(R.id.notDoingJobs) as TextView
            override fun onResponse(call: Call<Array<Job>>, response: Response<Array<Job>>) {
                if(response.code() == 200){
                    doingTextView.setVisibility(View.GONE)
                    populateDoingJobs(context, response.body())
                }
                else{
                    populateDoingJobs(context,null)
                    doingTextView.setVisibility(View.VISIBLE)
                    doingTextView.text = "No tiene tareas en proceso."
                }
            }

            override fun onFailure(call: Call<Array<Job>>, t: Throwable) {
                populateDoingJobs(context,null)
                doingTextView.setVisibility(View.VISIBLE)
                doingTextView.text = "Imposible cargar las tareas"
            }
        })
    }

    private fun getCompletedJobs(context: Context){
        val request = ServiceBuilder.buildService(JobRepository::class.java)
        val call = request.getPendingJobs(idUser); //CAMBIAR
        call.enqueue(object: Callback<Array<Job>> {
            val DoneTextView: TextView = findViewById(R.id.notDoneJobs) as TextView
            override fun onResponse(call: Call<Array<Job>>, response: Response<Array<Job>>) {
                if(response.code() == 200){
                    DoneTextView.setVisibility(View.GONE)
                    populateDoneJobs(context, response.body())
                }
                else{
                    populateDoneJobs(context,null)
                    DoneTextView.setVisibility(View.VISIBLE)
                    DoneTextView.text = "No tiene tareas terminadas."
                }
            }

            override fun onFailure(call: Call<Array<Job>>, t: Throwable) {
                populateDoneJobs(context,null)
                DoneTextView.setVisibility(View.VISIBLE)
                DoneTextView.text = "Imposible cargar las tareas"
            }
        })
    }


}