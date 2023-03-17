package com.example.eazyinn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_room_allotment.*

class RoomAllotment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_allotment)
        BoyCardView.setOnClickListener {

            startActivity(Intent(this, RoomAllocationBoy::class.java ))
        }
        GirlCardView.setOnClickListener {
            startActivity(Intent(this,RoomAllocationGirl::class.java))

        }
    }
}