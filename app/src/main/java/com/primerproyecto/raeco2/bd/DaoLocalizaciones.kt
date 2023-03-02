package com.primerproyecto.raeco2.bd

import android.content.Context
import android.location.Location
import android.util.Log
import com.primerproyecto.raeco2.Localization

public class DaoLocalizaciones(context: Context?)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(latitud:Double?, longitud:Double?): Int {
        Log.d("DaoLocalizaciones-find 14","Entro")

        var localizacioMasCercana:Location= localizacionxdistancia(latitud, longitud)
        Log.d("DaoLocalizaciones-find 17","${localizacioMasCercana.latitude}")
        Log.d("DaoLocalizaciones-find 17","${localizacioMasCercana.longitude}")
        return dataBase.obtenerLocalizacion(localizacioMasCercana.latitude, localizacioMasCercana.longitude)
    }


    fun esCercano10KMDeUmPunto (latitud:Double?, longitud:Double?): Boolean{
        Log.d("DaoLocalizaiones-23","esCercano10KMDeUmPunto")
        var distanciaMetros =calcularDistancia(latitud,longitud )
        Log.d("DaoLocalizaiones-28 distanciaenmetros","${distanciaMetros}")
        var bool = distanciaMetros<3000000.0f
        Log.d("DaoLocalizaiones-30 distanciaenmetros","${bool}")
        return distanciaMetros<3000000.0f // cambiar por menor
    }


    private fun findAll(): MutableList<Localization> {
         Log.d("DaoLocalizaciones-32","Entro")
        return dataBase.devolverLocalizacionesBd()
    }


    private fun calcularDistancia(latitud: Double?,longitud: Double? ): Float {

        val localizacion1 = Location("Fernando")
        if (latitud != null) {
            localizacion1.latitude=latitud
        }
        if (longitud != null) {
            localizacion1.longitude=longitud
        }

        var distanceMinima=Float.MAX_VALUE//10
        var localizacioDevolver= Location("Salida")
        //var devolverPar: MutableList<Any> = mutableListOf(localizacioDevolver, distanceMinima)
      //  Log.d("DaoLocalizaciones -devolverPar -48","${devolverPar.size}")
         Log.d("calcularDistancia -DaoLocalizaciones","48")
        var listaLocalizaciones= findAll()// Se trae todas las localizaciones
        Log.d("calcularDistancia -DaoLocalizaciones -50","${listaLocalizaciones.size}")
        for (elemento in listaLocalizaciones) {

            var localizacion2 = Location("Sebastian")
            localizacion2.latitude= elemento.obtenerLatitud()!!
            localizacion2.longitude= elemento.obtenerLongitud()!!

            var distanciaElemento = localizacion1.distanceTo(localizacion2)

            if(distanciaElemento<distanceMinima)//0<10
            {
                distanceMinima=distanciaElemento
                localizacioDevolver.latitude=localizacion2.latitude
                localizacioDevolver.longitude=localizacion2.longitude

            }
        }
        return distanceMinima
    }


    private fun localizacionxdistancia(latitud: Double?,longitud: Double? ): Location {

        val localizacionEntrada = Location("Entrada")
        if (latitud != null) {
            localizacionEntrada.latitude=latitud
        }
        if (longitud != null) {
            localizacionEntrada.longitude=longitud
        }
        var distanceMinima=Float.MAX_VALUE//10
        var localizacioDevolver= Location("Salida")
        var listaLocalizaciones= findAll()// Se trae todas las localizaciones
        Log.d("calcularDistancia -DaoLocalizaciones 88 debe de traer todas las localizaciones:","${listaLocalizaciones.size}")
        for (elemento in listaLocalizaciones) {

            var localizacionesBD = Location("ElementoBD")
            localizacionesBD.latitude= elemento.obtenerLatitud()!!
            localizacionesBD.longitude= elemento.obtenerLongitud()!!

            var distanciaElemento = localizacionEntrada.distanceTo(localizacionesBD)

            if(distanciaElemento<distanceMinima)//0<10
            {
                distanceMinima=distanciaElemento
                localizacioDevolver.latitude=localizacionesBD.latitude
                localizacioDevolver.longitude=localizacionesBD.longitude
            }
        }
        return localizacioDevolver
    }
    fun insert (localizacion: Localization){

    //    dataBase.insertarLocalizacion(localizacion) // se cambio en dataBase
    }

    fun delete (latitud:Double?, longitud:Double?){

     //  val ok= dataBase.eliminarLocalizacion(latitud, longitud)

    }


}