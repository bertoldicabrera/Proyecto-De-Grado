package com.primerproyecto.raeco2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.primerproyecto.raeco2.activities.Bienvenida


class MainActivity : AppCompatActivity() {

    private var message: TextView? = null
    private var bienvenida : Button? = null;
    private var facade: Facade = Facade(this@MainActivity)


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crearBdLocal()
        message = findViewById(R.id.menssage)
        permisosIniciarLocalizacion()
        message?.text ="Versión 1.0"
        bienvenida = findViewById(R.id.bienvenida)
        bienvenida?.setOnClickListener {
            val intent = Intent(this, Bienvenida::class.java )
            startActivity(intent)
        }

    }
    private fun crearBdLocal(){
        var mensaje: String? = facade.crearBd(this@MainActivity)

        Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_LONG)
            .show()

    }


    private fun permisosIniciarLocalizacion()
    {
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
        } else {
            iniciarLocalizacion()
        }
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