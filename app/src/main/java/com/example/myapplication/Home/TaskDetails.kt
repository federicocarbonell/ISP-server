package com.example.myapplication.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplication.R
import android.widget.*
import com.example.myapplication.Models.JobDetail
import java.lang.Integer.parseInt

class TaskDetails : AppCompatActivity() {
    var options = arrayOf("Pendiente", "En proceso", "Terminada")
    var spinner:Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        var bundle: Bundle ?= intent.extras
        // TODO: Cuando se agregen las demas properties ajustar.
        var job = bundle?.get("product") as JobDetail
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
                userTextView.text = "Selected: " + position.toString()
                // get selected item text
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}