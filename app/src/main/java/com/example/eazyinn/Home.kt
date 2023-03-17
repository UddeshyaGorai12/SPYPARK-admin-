package com.example.eazyinn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.eazyinn.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    //lateinit var sharedPrefaManager: SharedPrefaManager

    private lateinit var binding: ActivityHomeBinding

    private lateinit var database: DatabaseReference

    private lateinit var sharedPreferences: SharedPreferences

    var Phone: String = ""

    var Email: String = ""

    var USN: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MySP", Context.MODE_PRIVATE)
        var name = sharedPreferences.getString("USN","")

        auth = FirebaseAuth.getInstance()
        checkUser()

//        Reference
        val logout = findViewById<ImageButton>(R.id.idLogout)


        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        RoomAllotmentCardView.setOnClickListener{
            startActivity(Intent(this,RoomAllotment::class.java))
        }
        RequestsCardView.setOnClickListener{
            startActivity(Intent(this,Requests::class.java))
        }
        MedicalSupportCardView.setOnClickListener{
            startActivity(Intent(this,MedicalSupport::class.java))
        }
        PermissionsCardView.setOnClickListener{
            startActivity(Intent(this,Permissions::class.java))
        }
        ComplaintsCardView.setOnClickListener{
            startActivity(Intent(this,Complaints::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}