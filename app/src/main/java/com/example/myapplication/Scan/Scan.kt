package com.example.myapplication.Scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.myapplication.R
import java.lang.Integer.parseInt

class Scan : AppCompatActivity() {
    private lateinit var codescanner: CodeScanner
    private lateinit var text_id: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        }else{
            //val textView = findViewById<TextView>(R.id.text_id)
            //textView.text ="Hellow there.";
            startScanning()
        }

    }

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codescanner = CodeScanner(this, scannerView)
        codescanner.camera = CodeScanner.CAMERA_BACK
        codescanner.formats= CodeScanner.ALL_FORMATS

        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled=true
        codescanner.isFlashEnabled=false

        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                //Toast.makeText(this, "Product scanned successfully.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProductActions::class.java)
                intent.putExtra("productId",parseInt(it.text))
                startActivity(intent)
            }
        }

        codescanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Problema al inicializar la cámara: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codescanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos de cámara otorgados.", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Permisos de cámara denegados.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        if(::codescanner.isInitialized){
            codescanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::codescanner.isInitialized){
            codescanner?.releaseResources()
        }
        super.onPause()
    }
}