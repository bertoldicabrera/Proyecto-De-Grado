package com.primerproyecto.raeco2.bd
import android.content.Context
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.MainActivity

public class DaoAnimales(context: Context?)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(localiza:Int,esPrehistorico:Boolean): Animal {

        return dataBase.obtenerAnimalTablaRelacionXIdLocalizacion(localiza,esPrehistorico)
    }

    fun member (nombre:String?): Boolean{
        //llamar a existeAnimalDbHelper(nombre)
        return dataBase.existeAnimalDbHelper(nombre)
    }


}
