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



    override fun onLocationChanged(location: Location) {
        setearLongitud(location.longitude)
        setearLatitud(location.longitude)

    }




}