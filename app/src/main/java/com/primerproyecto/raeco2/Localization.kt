package com.primerproyecto.raeco2

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.widget.TextView


public class Localization : LocationListener {

    var mainActivity: MainActivity? = null
    var menssage: TextView? = null


    fun setMainActivity(mainActivity: MainActivity?, menssage: TextView?) {
        this.mainActivity = mainActivity
        this.menssage = menssage
    }

    override fun onLocationChanged(location: Location) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas

        println("1111111111111111111111111111111111111111111111111111111111")
        val text = """
              My ubication is: 
              Latitude = ${location.latitude}
              Longitude = ${location.longitude}
              """.trimIndent()
        menssage?.text = text
        println(text)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        when (status) {

            LocationProvider.AVAILABLE -> Log.d("debug", "LocationProvider.AVAILABLE")
            LocationProvider.OUT_OF_SERVICE -> Log.d("debug", "LocationProvider.OUT_OF_SERVICE")
            LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.d(
                "debug",
                "LocationProvider.TEMPORARILY_UNAVAILABLE"
            )
        }
    }



    override fun onProviderEnabled(provider: String) {
        menssage?.text = "GPS activated"
    }

    override fun onProviderDisabled(provider: String) {
        menssage?.text ="GPS deactived"
    }


}