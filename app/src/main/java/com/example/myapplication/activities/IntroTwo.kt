package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R

class IntroTwo : AppCompatActivity() {

    private lateinit var letBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_two)

        supportActionBar?.hide()
        letBtn=findViewById(R.id.letBtn)
        letBtn.setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
            finish()
        }


    }
}