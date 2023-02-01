package com.primerproyecto.raeco2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.primerproyecto.raeco2.bd.DaoAnimales
import com.primerproyecto.raeco2.bd.DaoLocalizaciones
import com.primerproyecto.raeco2.bd.DbHelper

private val animales : DaoAnimales = TODO() //Ver si va así
private val localizaciones : DaoLocalizaciones = TODO() //Ver si va así

class Facade {
    fun agregarAnimal(voNuevoAnimal: voAnimal) {

        if(!animales.member(voNuevoAnimal.obtenerNombreAnimal()))
        {
            val animal=  voAnimalParaAnimal(voNuevoAnimal)
            animales.insert(animal)
        }else
        {
            println("El animal ya existe * ver fachada")
        }
    }
    fun eliminarAnimal(nombre: String) {


        if(animales.member(nombre))
        {
            animales.delete(nombre)
        }else
        {
            println("El animal no existe * ver fachada")
        }

    }
    fun buscarAnimal(localizacion: voLocalizacion): voAnimal {

     var region= obtenerRegionAnimal(localizacion)
     var animal = animales.find(region) // ver que pasa si es null
     var animalSalida = animalParavoAnimal(animal)
     return animalSalida
    }

    fun crearBD(context: MainActivity): String? {
        var mensaje: String? = null
        val dbHelper = DbHelper(context)
        val db: SQLiteDatabase = dbHelper.getWritableDatabase()
        if (db != null) {
            mensaje= "BASE DE DATOS CREADA"
        } else {
            mensaje= "ERROR AL CREAR BASE DE DATOS"
        }
        return mensaje
    }
}


private fun obtenerRegionAnimal(localizacion: voLocalizacion):String?{
    var latitud = localizacion.obtenerLatitud()
    var longitud =localizacion.obtenerLongitud()
    var local :String?=null

    if(localizaciones.member(latitud,longitud ))
    {
        local = localizaciones.find(latitud,longitud).obtenerNombreRegion()

    }

    return local
}

private fun animalParavoAnimal(bicho:Animal):voAnimal{

    var voAnimalSalida = voAnimal(null,null,null,null,null, null, null)
    voAnimalSalida.setearNombreAnimal(bicho.obtenerNombreAnimal() )
    voAnimalSalida.setearDescripcionAnimal(bicho.obtenerDescripcionAnimal())
    voAnimalSalida.setearLinkAnimal(bicho.obtenerLinkAnimal())
    voAnimalSalida.setearUrlBackUpAnimal(bicho.obtenerObjetoBackUpAnimal())
    voAnimalSalida.setearObjetoAnimal(bicho.obtenerObjetoAnimal())
    voAnimalSalida.setearRegionAnimal(bicho.obtenerRegionAnimal())
    voAnimalSalida.setearSonido(bicho.obtenerSonido())
    return voAnimalSalida
}
private fun voAnimalParaAnimal(voBicho:voAnimal):Animal{

    val animalSalida: Animal= Animal(null, null,null,null,null, null, null)
    animalSalida.setearNombreAnimal(voBicho.obtenerNombreAnimal() )
    animalSalida.setearDescripcionAnimal(voBicho.obtenerDescripcionAnimal())
    animalSalida.setearLinkAnimal(voBicho.obtenerLinkAnimal())
    animalSalida.setearUrlBackUpAnimal(voBicho.obtenerObjetoBackUpAnimal())
    animalSalida.setearObjetoAnimal(voBicho.obtenerObjetoAnimal())
    animalSalida.setearRegionAnimal(voBicho.obtenerRegionAnimal())
    animalSalida.setearSonido(voBicho.obtenerSonido())
    return animalSalida
}