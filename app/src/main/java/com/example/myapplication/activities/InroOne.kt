package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R

class InroOne : AppCompatActivity() {

    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inro_one)

        supportActionBar?.hide()

        nextBtn=findViewById(R.id.nextBtn)
        nextBtn.setOnClickListener {

            val intent = Intent(this, IntroTwo::class.java)
            startActivity(intent)
            finish()
        }


    }
}