package com.primerproyecto.raeco2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.primerproyecto.raeco2.R


class Bienvenida : AppCompatActivity() {

    private var ir_btn : Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)


        ir_btn = findViewById(R.id.ir)
        ir_btn?.setOnClickListener {
            val intent = Intent(this,AR::class.java )
            startActivity(intent)
        }


    }
}