package com.example.myapplication.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplication.R
import android.widget.*
import com.example.myapplication.Job
import com.example.myapplication.Models.JobDetail
import com.example.myapplication.Repositories.JobRepository
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Integer.parseInt

class TaskDetails : AppCompatActivity() {
    var options = arrayOf("Pendiente", "En proceso", "Terminada")
    var spinner:Spinner? = null
    lateinit var job: JobDetail;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        var bundle: Bundle ?= intent.extras
        // TODO: Cuando se agregen las demas properties ajustar.
        job = bundle?.get("product") as JobDetail
        val titleText: TextView = findViewById(R.id.title) as TextView
        titleText.text = job.name
        val descriptionText: TextView = findViewById(R.id.description) as TextView
        descriptionText.text = job.description
        val latitudeText: TextView = findViewById(R.id.latitude) as TextView
        latitudeText.text = job.latitude.toString()
        //val longitudeText: TextView = findViewById(R.id.longitud) as TextView
        //longitudeText.text = job.longitud.toString()
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.task_states, android.R.layout.simple_spinner_item
        )
        val userTextView: TextView = findViewById(R.id.change) as TextView

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)
        spinner?.setSelection(parseInt(job.state.toString()));
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // get selected item text
                if (job.state != Integer(position.toString())) {
                    val request = ServiceBuilder.buildService(JobRepository::class.java)
                    job.state = Integer(position)
                    val call = request.updateJobState(job.id, job);
                    call.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            userTextView.text = "Estado actualizado correctamente."
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            userTextView.text = "Ocurrio un error al actualizar el estado."
                        }

                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val bottom_navigation: BottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        val intentHome = Intent(this, Home::class.java)

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    startActivity(intentHome);
                    true
                }
                R.id.camera -> {
                    startActivity(intentHome);
                    true
                }
                else -> false
            }
        }
    }
}