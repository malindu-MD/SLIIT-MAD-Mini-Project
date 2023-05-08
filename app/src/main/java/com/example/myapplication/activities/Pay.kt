package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pay : AppCompatActivity() {

    private lateinit var totalShow: TextView
    private lateinit var payNowButton: Button
    private lateinit var orderID: TextView

    private lateinit var pb: ProgressBar

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        totalShow = findViewById(R.id.textView260)
        payNowButton = findViewById(R.id.button11)
        orderID = findViewById(R.id.textView290)
        pb = findViewById(R.id.progressBar9)

        auth = FirebaseAuth.getInstance()

        pb.visibility = View.INVISIBLE

        totalShow.text = intent.getStringExtra("totalPrice").toString().toEditable()
        val t = intent.getStringExtra("totalPrice").toString().toEditable()

        payNowButton.text = "Pay Rs. $t"
        val oID = auth.currentUser?.uid.toString()
        orderID.text = oID

        payNowButton.setOnClickListener {

            pb.visibility = View.VISIBLE

            val db = FirebaseFirestore.getInstance()
            val parentDocRef = db.collection("cart").document(oID)
            val subCollectionRef = parentDocRef.collection("singleUser")

            subCollectionRef.get()
                .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    document.reference.delete()

                    pb.visibility = View.INVISIBLE
                    Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, Payment_Successful::class.java)
                    intent.putExtra("paidPrice", payNowButton.text)
                    startActivity(intent)
                    finish()
                }

            }
                .addOnFailureListener {
                    pb.visibility = View.INVISIBLE
                    Toast.makeText(this, "Payment Failed! Try Again!", Toast.LENGTH_SHORT).show()

                }
        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}