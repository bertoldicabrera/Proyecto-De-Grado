package com.primerproyecto.raeco2

import android.location.Location
import android.location.LocationListener
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class voLocalizacion constructor(
    private var id: Int? = null,
    private var nombreRegion: String? = null,
    private var latitud: Double? = null,
    private var longitud: Double? =null
): LocationListener {

    var mainActivity: MainActivity? = null
    var menssage: TextView? = null


    fun obtenerID():Int?{
        return this.id
    }
    fun obtenerNombreRegion():String?{
        return this.nombreRegion
    }
    fun obtenerLongitud(): Double? {
        return this.longitud
    }
    fun obtenerLatitud(): Double? {
        return this.latitud
    }
    fun setearID(identificador:Int?){
        this.id=identificador
    }
    fun setearNombreRegion(nombreRegionAnimal:String?){
        this.nombreRegion =nombreRegionAnimal
    }
    fun setearLongitud(longitudx:Double?){
        this.longitud =longitudx
    }
    fun setearLatitud(latitudy:Double?){
        this.latitud =latitudy
    }


    fun setMainActivity(mainActivity: MainActivity?, menssage: TextView?) {
        this.mainActivity = mainActivity
        this.menssage = menssage
    }

    override fun onLocationChanged(location: Location) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        setearLongitud(location.longitude)
        setearLatitud(location.longitude)
        println("****Localización 58, Coordena recibida")

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