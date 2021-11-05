package com.example.myapplication.Footer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Home.Home
import com.example.myapplication.R
import com.example.myapplication.Scan.Scan
import com.google.android.material.bottomnavigation.BottomNavigationView

class Footer: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun configureNavigation(bottom_navigation: BottomNavigationView) {
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
}

