package com.example.myapplication.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        /*
        val request = ServiceBuilder.buildService(EmployeeRepository::class.java)
        val call = request.getUsers(1)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val name = response.body()?.name
                val userTextView: TextView = findViewById(R.id.username) as TextView
                userTextView.text = "Bienvenido, " + name;
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val userTextView: TextView = findViewById(R.id.username) as TextView
                userTextView.text = "Bienvenido, " + t.message;
            }
        })d
         */
    }
}