package com.example.myapplication.Reports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.Models.ReportModel
import com.example.myapplication.R
import com.example.myapplication.Repositories.ReportRepository
import com.example.myapplication.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReport : AppCompatActivity() {
    var productId: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)
        var bundle: Bundle ?= intent.extras
        productId = bundle?.get("prodId") as Int;
        var createBtn = findViewById(R.id.buttonFinish) as Button
        createBtn.setOnClickListener{
            finishReportCreation()
        }
    }

    fun obtainReportModel () : ReportModel {
        val visitDate = findViewById<View>(R.id.VisitDate) as EditText
        val arrivedTime = findViewById<View>(R.id.ArrivedTime) as EditText
        val resolutionTime = findViewById<View>(R.id.ResolutionTime) as EditText
        val technician = findViewById<View>(R.id.Technician) as EditText
        val summary = findViewById<View>(R.id.Summary) as EditText
        val details = findViewById<View>(R.id.Details) as EditText
        val comments = findViewById<View>(R.id.Comments) as EditText
        val images: List<String> = listOf("Prueba");
        val reportModel = ReportModel(visitDate.text.toString(), arrivedTime.text.toString(), resolutionTime.text.toString(), technician.text.toString(), summary.text.toString(), details.text.toString(), comments.text.toString(),images);
        return reportModel;
    }

    fun createReport (productId: Int, report: ReportModel) {
        val request = ServiceBuilder.buildService(ReportRepository::class.java)
        val call = request.createReport(1, report);
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@CreateReport, "Reporte agregado exitosamente.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CreateReport, "Ocurrio un problema, por favor intente nuevamente.", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun finishReportCreation() {
        createReport(productId, obtainReportModel());
    }
}