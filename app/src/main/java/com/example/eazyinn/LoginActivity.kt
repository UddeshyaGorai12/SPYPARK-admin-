package com.example.eazyinn

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.eazyinn.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    //Viewinding
    private lateinit var binding: ActivityLoginBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: DatabaseReference

    private lateinit var sharedPreferences: SharedPreferences



    var password=""

    var Email=""
//    USN
    var USN=""
//    Name
  //  var name=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //configure actionbar
        actionBar=supportActionBar!!
        actionBar.title = "Login"

        //Configure Progress Dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In")
        progressDialog.setCanceledOnTouchOutside(false)


        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
       // checkUser()

        //handle click, open register activity

        binding.navigateToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        // handle click, begin login
        binding.loginButton.setOnClickListener{
            //before logging in, validate data
            validateData()

        }






        //Forgot password
        binding.forgotButton.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)
        }


    }

    private fun validateData() {
        //get data

        Email = binding.loginEmail.text.toString().trim()
        password= binding.loginPassword.text.toString().trim()
        USN = binding.loginUSN.text.toString().trim().toUpperCase()
        database = FirebaseDatabase.getInstance().getReference("User")
//        database.child(USN).get()
//
//            .addOnSuccessListener {
//                name = it.child("name").value.toString()
//                Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener{
//                Toast.makeText(this, "Failed to read", Toast.LENGTH_SHORT).show()
//            }

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            //invalid email Format
            binding.loginEmail.setError("Invalid Email format")
        }

        else if(TextUtils.isEmpty(password)){
            // no password entered
            binding.loginPassword.error=("Please enter password")
        }
        else if(TextUtils.isEmpty(USN)){
            binding.loginUSN.error=("Please enter USN")
        }
        else{
            //data is validated, begin login
            firebaselogin()
        }

    }

    private fun firebaselogin() {
        //show progress
        progressDialog.show()



        firebaseAuth.signInWithEmailAndPassword(Email, password)
            .addOnSuccessListener {

                //login success
                progressDialog.dismiss()

                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email= firebaseUser!!.email

                //SharedPreferances
                sharedPreferences= getSharedPreferences("MySP",Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("USN",USN)
                editor.putBoolean("hasLoggedIn",true)
                editor.apply()

                //Open profile
               val intent = Intent(this, Home::class.java)
               // intent.putExtra("userName",userName)
                startActivity(intent)

                finish()
            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}"+"$Email", Toast.LENGTH_SHORT).show()
            }

    }


}