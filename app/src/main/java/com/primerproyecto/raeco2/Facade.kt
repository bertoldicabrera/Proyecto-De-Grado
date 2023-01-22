package com.primerproyecto.raeco2

import com.primerproyecto.raeco2.bd.DaoAnimales
import com.primerproyecto.raeco2.bd.DbHelper
//Necesitamos ValueObjects?
private  val vo_animal : voAnimal = voAnimal(null, null,null,null,null)
private val animales : DaoAnimales = TODO() //Ver si va así

class Facade {


    fun agregarAnimal(nuevoAnimal: voAnimal) {
        val animal: Animal= Animal(null, null,null,null,null)


        if(!animales.member(nuevoAnimal.obtenerNombreAnimal()))
        {
            animal.setearNombreAnimal(nuevoAnimal.obtenerNombreAnimal() )//Pasar el set ?
            animal.setearDescripcionAnimal(nuevoAnimal.obtenerDescripcionAnimal())
            animal.setearUrlAnimal(nuevoAnimal.obtenerUrlAnimal())
            animal.setearObjetoAnimal(nuevoAnimal.obtenerObjetoAnimal())
            animal.setearRegionAnimal(nuevoAnimal.obtenerRegionAnimal())
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

 fun buscarAnimal(localiza: Int): Animal {
     //controlar si la localizacion existe
     return animales.find(localiza)
 }

}