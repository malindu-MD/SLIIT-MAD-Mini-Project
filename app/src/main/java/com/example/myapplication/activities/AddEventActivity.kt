package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.validation.AddEventValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class AddEventActivity : AppCompatActivity() {
//declare the variables
    private lateinit var addEventTitle: EditText
    private lateinit var addEventPlace: EditText
    private lateinit var addEventDate: EditText
    private lateinit var addEventTime: EditText
    private lateinit var addEventButton: Button
    private lateinit var pBar: ProgressBar

//db variable is used to initialize the Firebase Firestore database
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth //auth variable is used to authenticate the user.

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

//created to validate the user input.
        val addEventValidation = AddEventValidation()
        addEventTitle = findViewById(R.id.addEventTitle)
        addEventPlace = findViewById(R.id.addEventPlace)
        addEventDate = findViewById(R.id.addEventDate)
        addEventTime = findViewById(R.id.addEventTime)

        addEventButton = findViewById(R.id.addEventButton)
        pBar = findViewById(R.id.progressBar)
        pBar.visibility = View.INVISIBLE

        auth = FirebaseAuth.getInstance()

        addEventButton.setOnClickListener{

            val title = addEventTitle.text.toString().trim()
            val place = addEventPlace.text.toString().trim()
            val date = addEventDate.text.toString().trim()
            val time = addEventTime.text.toString().trim()

            if (addEventValidation.addEventValidateFields(title,place,date,time)) {

                pBar.visibility = View.VISIBLE
                val eventID =auth.currentUser?.uid.toString()
                val eventRandomID = UUID.randomUUID().toString()

                val addETitle = addEventTitle.text.toString().trim()
                val addEPlace = addEventPlace.text.toString().trim()
                val addEDate = addEventDate.text.toString().trim()
                val addETime = addEventTime.text.toString().trim()

//eventMap is used to create a new document in the Firestore database, and the input data is added as fields in the document
                val eventMap = hashMapOf(
                    "eventID" to eventID,
                    "eid" to eventRandomID,
                    "title" to addETitle,
                    "place" to addEPlace,
                    "date" to addEDate,
                    "time" to addETime
                )



                db.collection("events").document(eventRandomID).set(eventMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Event Added Success!", Toast.LENGTH_SHORT).show()
                        addEventTitle.text.clear()
                        addEventPlace.text.clear()
                        addEventDate.text.clear()
                        addEventTime.text.clear()
                        pBar.visibility = View.INVISIBLE
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Event Added Failed!", Toast.LENGTH_SHORT).show()
                        pBar.visibility = View.INVISIBLE
                    }

                db.collection("eventsByInstructorID").document(eventID).collection("singleEvents")
                    .document(eventRandomID).set(eventMap)

            }else{
                Toast.makeText(this, "All Fields are Required!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}