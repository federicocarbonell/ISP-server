package com.example.myapplication.Footer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Home.Home
import com.example.myapplication.R

class Footer: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btnHome = findViewById(R.id.btnHome) as Button
        val btnCamera = findViewById(R.id.btnCamera) as Button
        val intentHome = Intent(this, Home::class.java)
        val intentCamera = Intent(this, Home::class.java)
        btnHome.setOnClickListener {

            startActivity(intentHome)
        }
        btnCamera.setOnClickListener {
            btnCamera.text = "cambio";
            startActivity(intentCamera)
        }
    }
}