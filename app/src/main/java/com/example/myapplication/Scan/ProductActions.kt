package com.example.myapplication.Scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.Models.JobDetail
import com.example.myapplication.Product
import com.example.myapplication.R
import com.example.myapplication.Repositories.JobRepository
import com.example.myapplication.Repositories.ProductRepository
import com.example.myapplication.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_actions)
        var bundle: Bundle ?= intent.extras
        var productId = bundle?.get("productId") as Int
        getProductInfo(productId)
    }

    fun getProductInfo(id: Int) {
        val request = ServiceBuilder.buildService(ProductRepository::class.java)
        val call = request.getProductInfo(id);
        call.enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                chargeProductInfo(response.body() as Product)
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                val titleText: TextView = findViewById(R.id.productName) as TextView
                titleText.text = "A problem has occurred.";
            }

        })
    }

    fun chargeProductInfo(product: Product){
        val productName: TextView = findViewById(R.id.productName) as TextView
        productName.text = product.name;
        val productId: TextView = findViewById(R.id.productId) as TextView
        productId.text = "Identifier: " + product.id.toString();
        val productYear: TextView = findViewById(R.id.productYear) as TextView
        productYear.text = "Year: " + product.year.toString();

    }
}
