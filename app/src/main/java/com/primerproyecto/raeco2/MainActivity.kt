package com.primerproyecto.raeco2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.primerproyecto.raeco2.bd.DbHelper

class MainActivity : AppCompatActivity() {

    private var message: TextView? = null
    private var btnCrear : Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("ON CREATE TEST  42")
        message = findViewById(R.id.menssage)
        message?.text = "Puto"
        println("${message?.text}")

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            println("ON CREATE TEST  31")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        } else {
            println("ON CREATE TEST  38")
            iniciarLocalizacion()
        }

        btnCrear = findViewById(R.id.btnCrear)

        btnCrear?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val dbHelper = DbHelper(this@MainActivity)
                val db: SQLiteDatabase = dbHelper.getWritableDatabase()
                if (db != null) {
                    Toast.makeText(this@MainActivity, "BASE DE DATOS CREADA", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "ERROR AL CREAR BASE DE DATOS",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })




    }




    private fun iniciarLocalizacion() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val localization = Localization()
        localization.setMainActivity(this, message)
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsEnabled) {
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, localization)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, localization)
        message?.text = "Localizacion agregada"
    }
}