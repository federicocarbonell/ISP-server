package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val request = ServiceBuilder.buildService(ApiService::class.java)
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
        })
    }
}