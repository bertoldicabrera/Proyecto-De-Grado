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



    fun obtenerNombreLocalizacion():String?{
        return this.nombreLocalizacion
    }
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
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        setearLongitud(location.longitude)
        setearLatitud(location.longitude)

   // var salidaDisyancia=calcularDistancia(location.longitude, location.longitude)



      //  println("****$salidaDisyancia  metros dentro de OnLocationChanged")


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