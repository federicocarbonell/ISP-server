package com.example.myapplication.Reports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Home.Home
import com.example.myapplication.R
import com.example.myapplication.Report
import com.example.myapplication.Repositories.ReportRepository
import com.example.myapplication.Scan.Scan
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import java.lang.Integer.parseInt


class Detail  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_details)
        var bundle: Bundle ?= intent.extras
        var reportId = bundle?.get("reportId")
        getReportDetails(parseInt(reportId.toString()));

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
    }

    fun decode(imageString: String) {
        var image: ImageView = findViewById<ImageView>(R.id.image)
        // Decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        image.setImageBitmap(decodedImage)
    }

    private fun getReportDetails(reportId:Int){
        val request = ServiceBuilder.buildService(ReportRepository::class.java)
        val call = request.getReportDetail(reportId)
        call.enqueue(object: Callback<Report> {
            val productNameData: TextView = findViewById(R.id.productName) as TextView
            val visitDateData: TextView = findViewById(R.id.visitDate) as TextView
            val employeeNameData: TextView = findViewById(R.id.employee) as TextView
            val detailData: TextView = findViewById(R.id.detail) as TextView
            val commentData: TextView = findViewById(R.id.comment) as TextView
            val errorData: TextView = findViewById(R.id.error) as TextView
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if (response.code() == 200) {
                    productNameData.text = response.body()?.productName
                    visitDateData.text = response.body()?.visitDate
                    employeeNameData.text = response.body()?.employeeName
                    detailData.text = response.body()?.detail
                    commentData.text = response.body()?.comment

                    var image: ImageView = findViewById<ImageView>(R.id.image)

                    val base64String = response.body()?.image
                    if (base64String != null) {
                        decode(base64String);
                    }
                } else {
                    errorData.text = response.message().toString()
                }
            }
            override fun onFailure(call: Call<Report>, t: Throwable) {
                Log.d("reportdetail", t.message.toString())
                errorData.text = t.message.toString()
            }
        })
    }
}