package com.example.myapplication.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplication.R
import android.widget.*

class TaskDetails : AppCompatActivity() {
    var options = arrayOf("Pendiente", "En proceso", "Terminada")
    var spinner:Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        var bundle: Bundle ?= intent.extras
        var name = bundle!!.getString("nombre")
        val titleText: TextView = findViewById(R.id.title) as TextView
        titleText.text = name
        //var description = bundle!!.getString("description")
        //var state = bundle!!.getString("state")
        //var latitud = bundle!!.getString("latitud")
        //var longitude = bundle!!.getString("longitude")
        //val titleText: TextView = findViewById(R.id.title) as TextView
        //titleText.text = name
        //val descriptionText: TextView = findViewById(R.id.description) as TextView
        //descriptionText.text = description
        //val latitudeText: TextView = findViewById(R.id.latitude) as TextView
        //latitudeText.text = longitude
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.task_states, android.R.layout.simple_spinner_item
        )
        val userTextView: TextView = findViewById(R.id.change) as TextView

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)
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