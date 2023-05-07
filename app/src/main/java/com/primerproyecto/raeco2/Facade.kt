package com.primerproyecto.raeco2

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.primerproyecto.raeco2.bd.DaoAnimales
import com.primerproyecto.raeco2.bd.DaoLocalizaciones
import com.primerproyecto.raeco2.bd.DbHelper

class Facade (context: Context?){

    private val animales : DaoAnimales = DaoAnimales(context)
    private var localizaciones :  DaoLocalizaciones = DaoLocalizaciones(context)

    @SuppressLint("SuspiciousIndentation")
    fun buscarAnimal(localizacion: VoLocalizacion, esPrehistorico:Boolean): VoAnimal {
        var animalSalida=animalPorDefecto(esPrehistorico)
        var iDLocalizacion= obtenerRegionAnimal(localizacion,esPrehistorico)
if(iDLocalizacion!=-1)
{
    var animal = animales.find(iDLocalizacion,esPrehistorico) // ver que pasa si es null
    animalSalida = animalParaVoAnimal(animal)
}
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

    private fun animalPorDefecto(esprehistorico:Boolean):VoAnimal{
        var voAnimalSaldia =VoAnimal(null, null, null,null,null,null)
        if(esprehistorico)
        {
            voAnimalSaldia = VoAnimal("Ankylosaurus magniventris", "Ankylosaurus magniventris","https://es.wikipedia.org/wiki/Ankylosaurus_magniventris", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/ankylosaurus/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/ankylosaurus/scene.gltf","")
        }
        else{
            voAnimalSaldia = VoAnimal("Mapache", "Mapache", "https://es.wikipedia.org/wiki/Procyon", "https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/mapache/scene.gltf", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/mapache/scene.gltf", "https://github.com/bertoldicabrera/RecursosRaeco/blob/main/mapache/mapache.mp3?raw=true")
        }
        return voAnimalSaldia
    }
    private fun obtenerRegionAnimal(localizacion: VoLocalizacion, esPrehistorico: Boolean):Int{
        var latitud = localizacion.obtenerLatitud()
        var longitud =localizacion.obtenerLongitud()
        var localizacionID=-1
        if(localizaciones.esCercano10KMDeUmPunto(latitud,longitud ))
        {
            localizacionID = localizaciones.find(latitud,longitud,esPrehistorico)
        }

        return localizacionID
    }

    private fun animalParaVoAnimal(ani:Animal):VoAnimal{

        var voAnimalSalida = VoAnimal(null,null,null,null,null, null)
        voAnimalSalida.setearNombreAnimal(ani.obtenerNombreAnimal() )
        voAnimalSalida.setearDescripcionAnimal(ani.obtenerDescripcionAnimal())
        voAnimalSalida.setearLinkAnimal(ani.obtenerLinkAnimal())
        voAnimalSalida.setearUrlBackUpAnimal(ani.obtenerObjetoBackUpAnimal())
        voAnimalSalida.setearObjetoAnimal(ani.obtenerObjetoAnimal())
        voAnimalSalida.setearSonido(ani.obtenerSonido())
        return voAnimalSalida
    }

}




