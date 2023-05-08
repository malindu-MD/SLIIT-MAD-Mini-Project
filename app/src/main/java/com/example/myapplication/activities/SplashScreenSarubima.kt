package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.myapplication.R

class SplashScreenSarubima : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_sarubima)  //set layout related to the splash.kt file

        supportActionBar?.hide()        //hide action bar

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,   //remove status bar
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({  // delay the splash screen for 5 seconds
            val intent = Intent(this, InroOne::class.java)
            startActivity(intent)       //start introone activity
            finish()          //finish SplashScreenSarubima
        }, 5000) // 3000 is the delayed time in milliseconds.
    }
}