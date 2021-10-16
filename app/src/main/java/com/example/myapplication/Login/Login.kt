package com.example.myapplication.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.Home.Home
import com.example.myapplication.Models.UserLogin
import com.example.myapplication.R
import com.example.myapplication.Repositories.EmployeeRepository
import com.example.myapplication.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import com.example.myapplication.Job
import com.example.myapplication.Repositories.JobRepository
import com.example.myapplication.UserLogged
import com.example.myapplication.Home.TaskDetails


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Home::class.java)
        // get reference to button
        val btn = findViewById(R.id.loginBtn) as Button
        val user = findViewById<View>(R.id.username) as EditText
        val password = findViewById<View>(R.id.password) as EditText
        user.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
        password.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
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
        call.enqueue(object : Callback<UserLogged> {
            override fun onResponse(call: Call<UserLogged>, response: Response<UserLogged>) {
                if (response.body()?.id != null){
                    val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
                    var editor = sharedPreference.edit()
                    editor.putString("username",response.body()?.name.toString())
                    editor.putInt("userId", response.body()?.id!!.toInt())
                    editor.commit()
                    startActivity(intent)
                }else{
                    //QUITAR
                    startActivity(intent)
                    userTextView.text = "Credenciales incorrectas" ;
                }
            }
            override fun onFailure(call: Call<UserLogged>, t: Throwable) {
                //QUITAR
                startActivity(intent)
                userTextView.text = "Error de conexion" ;
            }
        })
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}