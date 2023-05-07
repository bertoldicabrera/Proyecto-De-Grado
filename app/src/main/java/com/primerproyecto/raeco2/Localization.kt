package com.primerproyecto.raeco2

import android.location.Location
import android.location.LocationListener
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.widget.TextView


public class Localization constructor(

    private var nombreLocalizacion: String? = null,
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

    fun setearNombreLocalizacion(nombreLocalizacionAnimal:String?){
        this.nombreLocalizacion =nombreLocalizacionAnimal
    }
    fun setearLongitud(longitud:Double?){
        this.longitud =longitud
    }
    fun setearLatitud(latitud:Double?){
        this.latitud =latitud
    }

    fun setMainActivity(mainActivity: MainActivity?, menssage: TextView?) {
        this.mainActivity = mainActivity
        this.menssage = menssage
    }


    override fun onLocationChanged(location: Location) {
        setearLongitud(location.longitude)
        setearLatitud(location.longitude)

    }

}
