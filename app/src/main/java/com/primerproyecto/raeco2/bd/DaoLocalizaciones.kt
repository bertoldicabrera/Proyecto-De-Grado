package com.primerproyecto.raeco2.bd

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.primerproyecto.raeco2.Localization

public class DaoLocalizaciones(context: Context?)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(latitud:Double?, longitud:Double?,esPrehistorico:Boolean): Int {
        var localizacioMasCercana:Location= localizacionxdistancia(latitud, longitud)
        return dataBase.obtenerLocalizacion(localizacioMasCercana.latitude, localizacioMasCercana.longitude,esPrehistorico)
    }


    @SuppressLint("SuspiciousIndentation")
    fun esCercano10KMDeUmPunto (latitud:Double?, longitud:Double?): Boolean{
        var distanciaMetros =calcularDistancia(latitud,longitud )
          return distanciaMetros<10000f
    }


    private fun findAll(): MutableList<Localization> {
        return dataBase.devolverLocalizacionesBd()
    }


    private fun retornarLocalizacionPropria(latitud: Double,longitud: Double ):Location{
        val localizacion1 = Location("A")
        localizacion1.latitude = latitud
        localizacion1.longitude = longitud
        return localizacion1
    }
    private fun calcularDistancia(latitud: Double?,longitud: Double? ): Float {
        var distanceMinima=Float.MAX_VALUE//10

        if (latitud != null && longitud != null) {
            val localizacionUsuario = retornarLocalizacionPropria(latitud, longitud)
            var listaLocalizaciones= findAll()
            for (elemento in listaLocalizaciones) {

                var localizacion2 = Location("B")
                localizacion2.latitude= elemento.obtenerLatitud()!!
                localizacion2.longitude= elemento.obtenerLongitud()!!

                var distanciaElemento = localizacionUsuario.distanceTo(localizacion2)
                if(distanciaElemento<distanceMinima)//0<10
                {
                    distanceMinima=distanciaElemento
                }
            }

        }

        return distanceMinima
    }


    private fun localizacionxdistancia(latitud: Double?,longitud: Double? ): Location {

        val localizacionEntrada = Location("Entrada")
        if (latitud != null && longitud != null) {
            localizacionEntrada.latitude=latitud
             localizacionEntrada.longitude=longitud
        }
        var distanceMinima=Float.MAX_VALUE//10
        var localizacioDevolver= Location("Salida")
        var listaLocalizaciones= findAll()// Se trae todas las localizaciones
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


}