package com.example.eazyinn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_room_allocation_girl.*

class RoomAllocationGirl : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_allocation_girl)
        TwoCardView.setOnClickListener {
            startActivity(Intent(this, GirlHostel::class.java))
        }
        ThreeCardView.setOnClickListener {
            startActivity(Intent(this, GirlHostel::class.java))
        }
        FourCardView.setOnClickListener {
            startActivity(Intent(this, GirlHostel::class.java))
        }
    }
}