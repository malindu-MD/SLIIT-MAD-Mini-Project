package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.validation.PayForEventValidation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WalletActivity : AppCompatActivity() {

    private lateinit var eventsFragment: Button

    private lateinit var balance: TextView
    private lateinit var eventName: EditText
    private lateinit var paidAmount: EditText

    private lateinit var payNow: Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val payForEventValidation = PayForEventValidation()

        eventsFragment = findViewById(R.id.events_fragment2)
        balance = findViewById(R.id.balance)
        eventName = findViewById(R.id.editTextTextPersonName)
        paidAmount = findViewById(R.id.editTextTextPersonName3)

        payNow = findViewById(R.id.payForEvent)

        db = FirebaseFirestore.getInstance()

        val ref = db.collection("instructorBankAccount").document("9dbtlbpD3IDYo5tM2EvI")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val eAmount = it.data?.get("balance")?.toString()

                balance.text = eAmount

            }
        }

        eventsFragment.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        payNow.setOnClickListener {

            val bBalance: Int = balance.text.toString().trim().toInt()
            val amountPaid: Int = paidAmount.text.toString().trim().toInt()
            println(amountPaid)
            val evenTName = eventName.text.toString().trim()

            if(payForEventValidation.payForEvent(evenTName,amountPaid,bBalance)) {

                val sum = (bBalance - amountPaid)

                val updatedMap = mapOf(
                    "balance" to sum
                )

                val ref = db.collection("instructorBankAccount").document("9dbtlbpD3IDYo5tM2EvI")
                ref.update(updatedMap)

                val paidEventMap = hashMapOf(
                    "eventName" to evenTName,
                    "paidAmount" to amountPaid
                )

                db.collection("paidEventsDetails").document().set(paidEventMap)

                    .addOnSuccessListener {

                        Toast.makeText(this, "Paid Success!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, WalletActivity::class.java))
                        finish()
                    }

                    .addOnFailureListener {
                        Toast.makeText(this, "Paid Failed!", Toast.LENGTH_SHORT).show()

                    }
            }else{
                Toast.makeText(this, "Error!! Check Details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}