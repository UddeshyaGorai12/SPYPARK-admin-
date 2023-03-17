package com.example.eazyinn

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference

    var name=""
    var email=""
    var USN=""
    var contact=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences("MySP", Context.MODE_PRIVATE)
        USN = sharedPreferences.getString("USN","").toString()

        database = FirebaseDatabase.getInstance().getReference("User")
        if (USN != null) {
            database.child(USN).get()

                .addOnSuccessListener {
                    name = it.child("name").value.toString()
                    email= it.child("email").value.toString()
                    contact= it.child("phone").value.toString()
                    nameTextView.setText(name)
                    contactTextView.setText(contact)
                    emailTextView.setText(email)
                    USNTextView.setText(USN)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to read", Toast.LENGTH_SHORT).show()
                }
        }


    }
}