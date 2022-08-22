package com.example.myapplication.Reports

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.Home.Home
import com.example.myapplication.Models.ReportModel
import com.example.myapplication.R
import com.example.myapplication.Repositories.ReportRepository
import com.example.myapplication.Scan.Scan
import com.example.myapplication.ServiceBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class CreateReport : AppCompatActivity() {
    var productId: Int = 0;
    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var sharedPreferences: SharedPreferences;
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imageB64 = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)
        var bundle: Bundle ?= intent.extras
        productId = bundle?.get("prodId") as Int;
        var createBtn = findViewById(R.id.buttonFinish) as Button
        createBtn.setOnClickListener{
            finishReportCreation()
        }

        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.buttonImages)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

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

    fun obtainReportModel () : ReportModel {
        val employeeId = sharedPreferences.getInt("userId",1);
        val arrivedTime = findViewById<View>(R.id.ArrivedTime) as EditText
        val summary = findViewById<View>(R.id.Summary) as EditText
        val details = findViewById<View>(R.id.Details) as EditText
        val comments = findViewById<View>(R.id.Comments) as EditText
        val image: String = imageB64;
        val reportModel = ReportModel(productId, employeeId, arrivedTime.text.toString(), summary.text.toString(), details.text.toString(), comments.text.toString(),image);
        return reportModel;
    }

    fun createReport (productId: Int, report: ReportModel) {
        val request = ServiceBuilder.buildService(ReportRepository::class.java)
        val call = request.createReport(productId, report);
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200){
                    Toast.makeText(this@CreateReport, "Reporte agregado exitosamente.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@CreateReport, "Ocurrio un problema, por favor intente nuevamente.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CreateReport, "Ocurrio un problema, por favor intente nuevamente.", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun finishReportCreation() {
        createReport(productId, obtainReportModel());
    }

    fun encode(imageUri: Uri?): String {
        val input = this.getContentResolver().openInputStream(imageUri!!)
        val image = BitmapFactory.decodeStream(input , null, null)

        // Encode image to base64 string
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            imageB64 = encode(imageUri);
        }
    }
}