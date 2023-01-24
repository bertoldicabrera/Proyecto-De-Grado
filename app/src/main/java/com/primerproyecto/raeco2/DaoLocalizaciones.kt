package com.primerproyecto.raeco2

import com.primerproyecto.raeco2.bd.DbHelper

class DaoLocalizaciones constructor(
    var localizaciones: DaoLocalizaciones){

    private val dataBase : DbHelper = DbHelper(context = null)// ver si no es esto

    fun find(latitud:Double?, longitud:Double?): Localization {

        return dataBase.obtenerLocalizacion(latitud, longitud)
    }



    fun member (latitud:Double?, longitud:Double?): Boolean{

        return dataBase.existeLocalizacion(latitud,longitud )
    }
    fun insert (localizacion: Localization){

        dataBase.insertarLocalizacion(localizacion) // se cambio en dataBase
    }

    fun delete (latitud:Double?, longitud:Double?){

       val ok= dataBase.eliminarLocalizacion(latitud, longitud)

    }


}