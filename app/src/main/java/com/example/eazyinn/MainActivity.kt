package com.example.eazyinn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({

            val loginIntent = Intent(this@MainActivity,LoginActivity::class.java)
            val homeIntent = Intent(this,Home::class.java)
            val sharedPreferences= getSharedPreferences("MySP", Context.MODE_PRIVATE)
            val hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false)

            if(hasLoggedIn){
            startActivity(homeIntent)
            finish()}
            else{
                startActivity(loginIntent)
                finish()
            }
        }, 2000)
    }
}