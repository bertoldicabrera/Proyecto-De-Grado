package com.primerproyecto.raeco2.bd

import android.location.Location
import android.util.Log
import com.primerproyecto.raeco2.Localization
import com.primerproyecto.raeco2.MainActivity

public class DaoLocalizaciones(context: MainActivity)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(latitud:Double?, longitud:Double?): Int {

        val miLista: MutableList<Any> = calcularDistancia(latitud,longitud )
        var localizacioMasCercana:Location= miLista[0] as Location//TipoLocation

        return dataBase.obtenerLocalizacion(localizacioMasCercana.latitude, localizacioMasCercana.longitude)
    }


    fun esCercano10KMDeUmPunto (latitud:Double?, longitud:Double?): Boolean{

        var miLista: MutableList<Any> = calcularDistancia(latitud,longitud )

        var distanciaMetros: Float =miLista[1] as Float
        return distanciaMetros<10000.0f
    }


    private fun findAll(): MutableList<Localization> {

        return dataBase.devolverLocalizacionesBd()
    }


    private fun calcularDistancia(latitud: Double?,longitud: Double? ): MutableList<Any> {

        val localizacion1 = Location("Fernando")
        if (latitud != null) {
            localizacion1.latitude=latitud
        }
        if (longitud != null) {
            localizacion1.longitude=longitud
        }

        var distanceMinima=Float.MAX_VALUE//10
        var localizacioDevolver= Location("Salida")
        var devolverPar: MutableList<Any> = mutableListOf(localizacioDevolver, distanceMinima)
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
                devolverPar[0]=localizacioDevolver
                devolverPar[1]=distanciaElemento
            }
        }
        return devolverPar
    }
    fun insert (localizacion: Localization){

    //    dataBase.insertarLocalizacion(localizacion) // se cambio en dataBase
    }

    fun delete (latitud:Double?, longitud:Double?){

     //  val ok= dataBase.eliminarLocalizacion(latitud, longitud)

    }


}