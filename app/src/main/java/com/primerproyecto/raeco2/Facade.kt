package com.primerproyecto.raeco2

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.primerproyecto.raeco2.bd.DaoAnimales
import com.primerproyecto.raeco2.bd.DaoLocalizaciones
import com.primerproyecto.raeco2.bd.DbHelper



class Facade (context: Context?){

    private val animales : DaoAnimales = DaoAnimales(context) //Ver si va así
     private var localizaciones :  DaoLocalizaciones = DaoLocalizaciones(context) //Ver si va así



    @SuppressLint("SuspiciousIndentation")
    fun buscarAnimal(localizacion: VoLocalizacion, esPrehistorico:Boolean): VoAnimal {
        Log.d("BuscarAnimal-Fachada", "37 Lat ${localizacion.obtenerLatitud()} Long ${localizacion.obtenerLongitud()}  ")
        var animalSalida=animalPorDefecto(esPrehistorico) //Si es -1 le carga un animall por defecto
        var iDLocalizacion= obtenerRegionAnimal(localizacion,esPrehistorico)
if(iDLocalizacion!=-1)
{
    Log.d("BuscarAnimal-Fachada 46 IdLocalizacion", "${iDLocalizacion}")
    var animal = animales.find(iDLocalizacion,esPrehistorico) // ver que pasa si es null
    Log.d("BuscarAnimal-Fachada 50 obtuveAnimal", "${animal.obtenerNombreAnimal()}" )
    animalSalida = animalParaVoAnimal(animal)

}else{
    Log.d("BuscarAnimal-Fachada 54 es null y devuelve por defecto uno", "${animalSalida.obtenerNombreAnimal()}" )
}


     return animalSalida
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

    private fun obtenerRegionAnimal(localizacion: VoLocalizacion, esPrehistorico: Boolean):Int{
        var latitud = localizacion.obtenerLatitud()
        var longitud =localizacion.obtenerLongitud()
        var localizacionID=-1
        Log.d("obtenerRegionAnimal-Fachada 70","Cargo variables")
//Devulve tru si es cercano a 10km

        if(localizaciones.esCercano10KMDeUmPunto(latitud,longitud ))
        {
            Log.d("ObtemerRegionAnimal","EntroesCercano10km")
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
    private fun voAnimalParaAnimal(voAni:VoAnimal):Animal{

        val animalSalida: Animal= Animal(null, null,null,null,null, null)
        animalSalida.setearNombreAnimal(voAni.obtenerNombreAnimal() )
        animalSalida.setearDescripcionAnimal(voAni.obtenerDescripcionAnimal())
        animalSalida.setearLinkAnimal(voAni.obtenerLinkAnimal())
        animalSalida.setearUrlBackUpAnimal(voAni.obtenerObjetoBackUpAnimal())
        animalSalida.setearObjetoAnimal(voAni.obtenerObjetoAnimal())
        animalSalida.setearSonido(voAni.obtenerSonido())
        return animalSalida
    }
}




