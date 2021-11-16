package com.example.myapplication.Scan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.History.History
import com.example.myapplication.Home.Home
import com.example.myapplication.Models.JobDetail
import com.example.myapplication.Product
import com.example.myapplication.R
import com.example.myapplication.Reports.CreateReport
import com.example.myapplication.Repositories.ProductRepository
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActions : AppCompatActivity() {
    private var productId = -1;
    private var prodName = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_actions)
        var bundle: Bundle ?= intent.extras
        productId = bundle?.get("productId") as Int
        getProductInfo(productId)
        var historyBtn = findViewById(R.id.buttonHistory) as Button
        var createBtn = findViewById(R.id.buttonUploadReport) as Button
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
        historyBtn.setOnClickListener {
            openHistory(productId)
        }
        createBtn.setOnClickListener{
            createReport(productId)
        }
    }

    fun getProductInfo(id: Int) {
        val request = ServiceBuilder.buildService(ProductRepository::class.java)
        val call = request.getProductInfo(id);
        call.enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.raw().code() == 404) {
                    showError();
                } else {
                    chargeProductInfo(response.body() as Product)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                val titleText: TextView = findViewById(R.id.productName) as TextView
                titleText.text = "No se ha encontrado un producto válido.";
            }

        })
    }

    fun openHistory(productId: Int){
        val intentHistory = Intent(this, History::class.java)
        intentHistory.putExtra("prodId", productId)
        intentHistory.putExtra("prodName", prodName)
        startActivity(intentHistory)
    }

    fun createReport(productId: Int){
        val intentCreate = Intent(this, CreateReport::class.java)
        intentCreate.putExtra("prodId", productId)
        startActivity(intentCreate)
    }

    fun showError(){
        Toast.makeText(this, "No se ha encontrado un producto válido.", Toast.LENGTH_LONG).show()
        this.finish();
    }

    fun chargeProductInfo(product: Product){
        val productName: TextView = findViewById(R.id.productName) as TextView
        productName.text = product.name;
        prodName = product.name;
        val productId: TextView = findViewById(R.id.productId) as TextView
        productId.text = "Identificador: " + product.id.toString();
        val productYear: TextView = findViewById(R.id.productYear) as TextView
        productYear.text = "Año: " + product.year.toString();

    }
}
