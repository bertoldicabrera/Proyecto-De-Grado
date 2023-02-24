package com.primerproyecto.raeco2.bd

import android.location.Location
import com.primerproyecto.raeco2.Localization

class DaoLocalizaciones{

    private val dataBase : DbHelper = DbHelper(context = null)// ver si no es esto

    fun find(latitud:Double?, longitud:Double?): Int {

        return dataBase.obtenerLocalizacion(latitud, longitud)
    }

    // seguir aca
    fun calcularDistancia(latitud: Double,longitud: Double ): Float {

        val localizacion1 = Location("Fernando")
        localizacion1.latitude=latitud
        localizacion1.longitude=longitud
        var distanceMinima=Float.MAX_VALUE//10
        val listaLocalizaciones= findAll()

        for (elemento in listaLocalizaciones) {

            val localizacion2 = Location("Sebastian")
            localizacion2.latitude= elemento.obtenerLatitud()!!
            localizacion2.longitude= elemento.obtenerLongitud()!!

           var distanceFinal = localizacion1.distanceTo(localizacion2)
            if(distanceFinal<distanceMinima)//0<10
            {
                distanceMinima<distanceFinal
            }
        }
        return distanceMinima
    }

/*
        val location1 = Location("Localizacio1 Seba")
        location1.latitude = latitud // -34.886360 location.latitude //latitud
        location1.longitude = longitud //-56.147075 location.longitude //longitud

        //asume que viene de la base de datos
        val location2 = Location("Casa Fernando 2")
        location2.latitude = -34.902355 //latitud
        location2.longitude =-56.187859 //longitud

        val distance = location1.distanceTo(location2)
        println("****Localización 67, Coordena 1 ${location1.longitude} y ${location1.latitude}")
        println("****Localización 68, Coordena 2 ${location2.longitude} y ${location2.latitude}")
        println("****$distance en metros dentro de calcularDistancia")
        //busco en la base de datos y me traigo el mas cercano a la longitud y latitud.
*/

   private fun findAll(): MutableList<Localization> {

        return dataBase.devolverLocalizacionesBd()
    }


    fun esCercano10KMDeUmPunto (latitud:Double?, longitud:Double?): Boolean{

        return dataBase.esCercanoALocalizacion(latitud,longitud )
    }
    fun insert (localizacion: Localization){

    //    dataBase.insertarLocalizacion(localizacion) // se cambio en dataBase
    }

    fun delete (latitud:Double?, longitud:Double?){

     //  val ok= dataBase.eliminarLocalizacion(latitud, longitud)

    }


}