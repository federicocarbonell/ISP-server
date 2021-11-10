package com.example.myapplication.History

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Home.Home
import com.example.myapplication.R
import com.example.myapplication.Report
import com.example.myapplication.Reports.Detail
import com.example.myapplication.Repositories.ReportRepository
import com.example.myapplication.Scan.Scan
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class History : AppCompatActivity() {
    private var layoutManagerReports: RecyclerView.LayoutManager? = null
    private var reportAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_list)
        Log.d("history", intent.extras.toString())
        var bundle: Bundle ?= intent.extras
        var productId = bundle?.get("prodId")
        val prodId: TextView = findViewById(R.id.productId) as TextView
        prodId.text = "Identificador: " + productId.toString()
        getReports(this);

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

    fun openDetails(v: View?){
        val report = v?.getTag() as Report
        val reportId = report.id
        Log.d("reports", "opening details in history")
        val intent = Intent(this, Detail::class.java)
        intent.putExtra("reportId",reportId)
        startActivity(intent)
    }


    private fun populateReports(context: Context, reports: Array<Report>?){
        val reportView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.reportList) as androidx.recyclerview.widget.RecyclerView
        if(reports == null){
            reportView.setVisibility(View.GONE)
        } else {
            reportView.setVisibility(View.VISIBLE)
            layoutManagerReports = LinearLayoutManager(context)
            reportView.layoutManager = layoutManagerReports
            reportAdapter = RecyclerAdapter(reports) //Constructor
            reportView.adapter = reportAdapter
        }
    }


    private fun getReports(context: Context){
        val request = ServiceBuilder.buildService(ReportRepository::class.java)
        val call = request.getProductReports(1);
        call.enqueue(object: Callback<Array<Report>> {
            val doingTextView: TextView = findViewById(R.id.notToDoJobs) as TextView
            override fun onResponse(call: Call<Array<Report>>, response: Response<Array<Report>>) {
                if(response.code() == 200){
                    doingTextView.setVisibility(View.GONE)
                    populateReports(context, response.body())
                }
                else{
                    populateReports(context,null)
                    doingTextView.setVisibility(View.VISIBLE)
                    doingTextView.text = "No tiene tareas en proceso."
                }
            }

            override fun onFailure(call: Call<Array<Report>>, t: Throwable) {
                populateReports(context,null)
                doingTextView.setVisibility(View.VISIBLE)
                doingTextView.text = "Imposible cargar los reportes" + t.message
            }
        })
    }
/*
    fun openDetails(v: View?){
        val job = v?.getTag()
        val jobDetail = JobDetail(job.id,job.product.name,job.description, job.state, job.direction);
        val intent = Intent(this, TaskDetails::class.java)
        intent.putExtra("product",jobDetail)
        startActivity(intent)
    }*/
}