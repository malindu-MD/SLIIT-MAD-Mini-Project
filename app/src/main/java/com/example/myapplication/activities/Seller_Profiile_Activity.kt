package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R

class Seller_Profiile_Activity : AppCompatActivity() {

    private lateinit var sellerItem: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_profiile)

        sellerItem = findViewById(R.id.sellerItem)


        sellerItem.setOnClickListener(){
            val i = Intent(this, Item_Details_Activity::class.java)
            startActivity(i)
        }
    }

}
