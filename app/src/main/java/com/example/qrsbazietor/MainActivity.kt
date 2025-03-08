package com.example.qrsbazietor

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urbanTickets: LinearLayout = findViewById(R.id.urbanTickets)
        val extraurbanTickets: LinearLayout = findViewById(R.id.extraurbanTickets)
        val wallet: LinearLayout = findViewById(R.id.wallet)
        val info: LinearLayout = findViewById(R.id.info)

        urbanTickets.setOnClickListener {
            val intent = Intent(this, UrbanTicketsActivity::class.java)
            startActivity(intent)
        }

        extraurbanTickets.setOnClickListener {
            val intent = Intent(this, ExtraurbanTicketsActivity::class.java)
            startActivity(intent)
        }

        wallet.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }

        info.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}