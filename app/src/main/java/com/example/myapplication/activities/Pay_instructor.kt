package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pay_instructor : AppCompatActivity() {

    private lateinit var availableAmount: TextView
    private lateinit var payAmount: EditText
    private lateinit var payButton: Button
    private lateinit var dash: AppCompatButton

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_instructor)

        availableAmount = findViewById(R.id.editTextTextPersonName9)
        payAmount = findViewById(R.id.editTextTextPersonName11)
        payButton = findViewById(R.id.pay)
        dash = findViewById(R.id.maths_fragment)

        db = FirebaseFirestore.getInstance()

        val ref = db.collection("adminBankAccount").document("G6CYAI5rVMjI61bxzw7I")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val eAmount = it.data?.get("amount")?.toString()

                availableAmount.text = eAmount

            }
        }

        payButton.setOnClickListener {

            val amountAvailable: Int = availableAmount.text.toString().trim().toInt()
            val amountPaid: Int = payAmount.text.toString().trim().toInt()

            val sum = (amountAvailable - amountPaid)

            val updatedMap = mapOf(
                "amount" to sum
            )

            val ref = db.collection("adminBankAccount").document("G6CYAI5rVMjI61bxzw7I")
            ref.update(updatedMap)

            val walletMap = hashMapOf(
                "balance" to amountPaid
            )

            db.collection("instructorBankAccount").document().set(walletMap)

                .addOnSuccessListener {

                Toast.makeText(this, "Paid Success!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Dashbord_view::class.java))
                finish()
            }

                .addOnFailureListener{
                    Toast.makeText(this, "Paid Failed!", Toast.LENGTH_SHORT).show()

                }

        }

        dash.setOnClickListener {
            startActivity(Intent(this, Dashbord_view::class.java))
            finish()
        }
    }
}
