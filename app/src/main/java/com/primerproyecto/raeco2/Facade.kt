package com.primerproyecto.raeco2

import android.database.sqlite.SQLiteDatabase
import com.primerproyecto.raeco2.bd.DaoAnimales
import com.primerproyecto.raeco2.bd.DaoLocalizaciones
import com.primerproyecto.raeco2.bd.DbHelper

private val animales : DaoAnimales = DaoAnimales() //Ver si va así
private val localizaciones : DaoLocalizaciones = DaoLocalizaciones() //Ver si va así

class Facade {
    fun agregarAnimal(voNuevoAnimal: VoAnimal) {

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
    fun buscarAnimal(localizacion: VoLocalizacion): VoAnimal {

     var region= obtenerRegionAnimal(localizacion)
     var animal = animales.find(region) // ver que pasa si es null
     var animalSalida = animalParaVoAnimal(animal)
     return animalSalida
    }

    fun crearBd(context: MainActivity): String? {
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


private fun obtenerRegionAnimal(localizacion: VoLocalizacion):String?{
    var latitud = localizacion.obtenerLatitud()
    var longitud =localizacion.obtenerLongitud()
    var local :String?=null

    if(localizaciones.member(latitud,longitud ))
    {
        local = localizaciones.find(latitud,longitud)

    }

    return local
}

private fun animalParaVoAnimal(ani:Animal):VoAnimal{

    var voAnimalSalida = VoAnimal(null,null,null,null,null, null, null)
    voAnimalSalida.setearNombreAnimal(ani.obtenerNombreAnimal() )
    voAnimalSalida.setearDescripcionAnimal(ani.obtenerDescripcionAnimal())
    voAnimalSalida.setearLinkAnimal(ani.obtenerLinkAnimal())
    voAnimalSalida.setearUrlBackUpAnimal(ani.obtenerObjetoBackUpAnimal())
    voAnimalSalida.setearObjetoAnimal(ani.obtenerObjetoAnimal())
    voAnimalSalida.setearRegionAnimal(ani.obtenerRegionAnimal())
    voAnimalSalida.setearSonido(ani.obtenerSonido())
    return voAnimalSalida
}
private fun voAnimalParaAnimal(voAni:VoAnimal):Animal{

    val animalSalida: Animal= Animal(null, null,null,null,null, null, null)
    animalSalida.setearNombreAnimal(voAni.obtenerNombreAnimal() )
    animalSalida.setearDescripcionAnimal(voAni.obtenerDescripcionAnimal())
    animalSalida.setearLinkAnimal(voAni.obtenerLinkAnimal())
    animalSalida.setearUrlBackUpAnimal(voAni.obtenerObjetoBackUpAnimal())
    animalSalida.setearObjetoAnimal(voAni.obtenerObjetoAnimal())
    animalSalida.setearRegionAnimal(voAni.obtenerRegionAnimal())
    animalSalida.setearSonido(voAni.obtenerSonido())
    return animalSalida
}