package com.akmalmf.scanulator.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.akmalmf.scanulator.BuildConfig
import com.akmalmf.scanulator.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val theme = BuildConfig.THEME

        when(theme){

            "green" -> setTheme(R.style.Theme_Scanulator_Green)
        }
    }
}