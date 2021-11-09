package com.example.myapplication.Home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplication.R
import android.widget.*
import com.example.myapplication.Models.JobDetail
import com.example.myapplication.Repositories.JobRepository
import com.example.myapplication.Scan.Scan
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

class TaskDetails : AppCompatActivity() {
    var options = arrayOf("Pendiente", "En proceso", "Terminada")
    var spinner:Spinner? = null
    var mapButton: ImageButton? = null
    lateinit var job: JobDetail;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        var bundle: Bundle ?= intent.extras
        mapButton = findViewById(R.id.mapButton)
        job = bundle?.get("product") as JobDetail
        val titleText: TextView = findViewById(R.id.title) as TextView
        titleText.text = job.name
        val descriptionText: TextView = findViewById(R.id.description) as TextView
        descriptionText.text = job.description
        val direcitonText: TextView = findViewById(R.id.direction) as TextView
        direcitonText.text = job.direction.toString()
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
        val intentScan = Intent(this, Scan::class.java)

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    startActivity(intentHome);
                    true
                }
                R.id.camera -> {
                    startActivity(intentScan);
                    true
                }
                else -> false
            }
        }

        val mapButton = findViewById(R.id.mapButton) as ImageButton
        mapButton.setOnClickListener{
            val gmmIntentUri = Uri.parse("geo:0,0?q="+job.direction)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}