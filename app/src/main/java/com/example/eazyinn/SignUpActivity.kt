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
import com.example.eazyinn.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    //Viewinding
    private lateinit var binding: ActivitySignUpBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //database
    private lateinit var database : DatabaseReference

    private lateinit var sharedPreferences: SharedPreferences

    //public var Email = binding.loginEmail.text.toString().trim()
    private var email=""
    private var password=""
    private var USN=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar=supportActionBar!!
        actionBar.title = "SignUp"
        //enable back button
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)

        // configure progress dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.signupButton.setOnClickListener {


            //validate data
            validateData()
        }
        binding.navigateToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }





//        //OTP Redirect to veirfication tab
//        otpbutton.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    private fun validateData() {
        //Get data
        email= binding.loginEmail.text.toString()
        password= binding.loginPassword.text.toString().trim()
        USN = binding.USN.text.toString().trim().toUpperCase()
        val firstName = binding.Name.text.toString().trim()
        val phone = binding.phone.text.toString().trim()

        //creating User in data base
        database = FirebaseDatabase.getInstance().getReference().child("User")

        //putting values in User database
        val user = User(firstName, USN, phone, email)
        database.child(USN).setValue(user)


            .addOnSuccessListener {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }


        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email Format
            binding.loginEmail.setError("Invalid Email format")
        }

        else if(TextUtils.isEmpty(password)){
            // no password entered
            binding.loginPassword.error=("Please enter password")
        }
        else if (password.length<6){
            // password length is less than 6
            binding.loginPassword.error="Password must be atleast 6 characters long"
        }
        else{

            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //Show progress
        progressDialog.show()


        // create account
        firebaseAuth.createUserWithEmailAndPassword( email, password)
            .addOnSuccessListener {


                //signUp success
                progressDialog.dismiss()

                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                //sharedPreferances
                sharedPreferences= getSharedPreferences("MySP", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("USN",USN)
                editor.putBoolean("hasLoggedIn",true)
                editor.apply()
                // Open Profile
                val intent = Intent(this@SignUpActivity, Home::class.java)
                //intent.putExtra("userName",userName)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{e->
                // signUp failed
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp  Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}