package com.example.myapplication

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Home::class.java)
        // get reference to button
        val btn = findViewById(R.id.loginBtn) as Button
// set on-click listener
        btn.setOnClickListener {
            validateCredentials()
        }
    }

    private fun validateCredentials(){
        val user = findViewById<View>(R.id.username) as EditText
        val password = findViewById<View>(R.id.password) as EditText
        if (user.equals("rodrigo") && password.equals("pass")){
            startActivity(intent)
        }else{
            val userTextView: TextView = findViewById(R.id.errors) as TextView
            userTextView.text = "Credenciales incorrectas" ;
        }
    }
}