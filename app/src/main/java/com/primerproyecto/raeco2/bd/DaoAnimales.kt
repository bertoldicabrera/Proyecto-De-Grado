package com.primerproyecto.raeco2.bd
import android.content.Context
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.MainActivity

public class DaoAnimales(context: MainActivity)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(localiza:Int): Animal {

        return dataBase.obtenerAnimalTablaRelacionXIdLocalizacion(localiza)
    //Retorna un animal aleatorio en base a region en que vive

    }
    fun member (nombre:String?): Boolean{
        //llamar a existeAnimalDbHelper(nombre)
        return dataBase.existeAnimalDbHelper(nombre)
    }
    fun insert (animal: Animal){
        //llamar a insertarAnimalDbHelper(nombre)
       // dataBase.insertarAnimalDbHelper(animal) // se cambio en dataBase
    }

    fun delete (nombre:String){
       // dataBase.eliminarAnimalDbHelper(nombre)
        //llamar a eliminarAnimalDbHelper(nombre)
    }

}
