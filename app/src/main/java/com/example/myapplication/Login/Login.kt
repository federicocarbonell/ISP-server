package com.example.myapplication.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.Home.Home
import com.example.myapplication.Models.UserLogin
import com.example.myapplication.R
import com.example.myapplication.Repositories.ApiResponse
import com.example.myapplication.Repositories.EmployeeRepository
import com.example.myapplication.Repositories.ServiceBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Home::class.java)
        // get reference to button
        val btn = findViewById(R.id.loginBtn) as Button
// set on-click listener
        btn.setOnClickListener {
            validateCredentials(intent)
        }
    }

    private fun validateCredentials(intent: Intent){
        val user = findViewById<View>(R.id.username) as EditText
        val password = findViewById<View>(R.id.password) as EditText
        val request = ServiceBuilder.buildService(EmployeeRepository::class.java)
        val call = request.postLogin(UserLogin(user.text.toString(),password.text.toString()))
        val userTextView: TextView = findViewById(R.id.errors) as TextView
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.body()?.id != null){
                    startActivity(intent)
                }else{
                    userTextView.text = "Credenciales incorrectas" ;
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                userTextView.text = "Credenciales incorrectas" ;
            }
        })
    }
}