package com.primerproyecto.raeco2

import com.primerproyecto.raeco2.bd.DbHelper
//Necesitamos ValueObjects?
private  val animal : Animal = Animal(null, null,null,null,null)
private val dataBase : DbHelper = DbHelper(context = null)// ver si no es esto
class Facade {


    fun agregarAnimal(nuevoAnimal: Animal) {
        dataBase.insertarAnimalDbHelper(nuevoAnimal) // se cambio en dataBase
        //animal.setearNombreAnimal(nuevoAnimal.nombre) //Pasar el set ?
        //animal.setearDescripcionAnimal(nuevoAnimal.descripcion)
        //animal.setearUrlAnimal(nuevoAnimal.url)
       // animal.setearObjetoAnimal(nuevoAnimal.objeto3D)
        //animal.setearRegionAnimal(nuevoAnimal.region)
    }

    fun eliminarAnimal(nombre: String) {
        dataBase.eliminarAnimalDbHelper(nombre)
    }

 fun buscarAnimal(localiza: Int){
     dataBase.obtenerAnimalDbHelperxRegion(localiza)
 }

}