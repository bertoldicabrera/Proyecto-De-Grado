package com.primerproyecto.raeco2

import android.location.Location
import android.location.LocationListener
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class VoLocalizacion constructor(

    private var latitud: Double? = null,
    private var longitud: Double? =null
): LocationListener {

    var mainActivity: MainActivity? = null
    var menssage: TextView? = null

    fun obtenerLongitud(): Double? {
        return this.longitud
    }
    fun obtenerLatitud(): Double? {
        return this.latitud
    }

    fun setearLongitud(longi:Double?){
        this.longitud =longi
    }
    fun setearLatitud(lati:Double?){
        this.latitud =lati
    }


    fun setMainActivity(mainActivity: MainActivity?, menssage: TextView?) {
        this.mainActivity = mainActivity
        this.menssage = menssage
    }

    override fun onLocationChanged(location: Location) {
        setearLongitud(location.longitude)
        setearLatitud(location.longitude)

    }

    //Es necesaria esta funcion ?
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
        menssage?.text = "GPS activado"
    }

    override fun onProviderDisabled(provider: String) {
        menssage?.text ="GPS desactivado"
    }


}