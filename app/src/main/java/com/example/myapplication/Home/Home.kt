package com.example.myapplication.Home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.R

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var userName = sharedPreference.getString("username","defaultName")
        val userTextView: TextView = findViewById(R.id.username) as TextView
        userTextView.text = "Bienvenido, " + userName
    }
}