package com.primerproyecto.raeco2.bd
import android.content.Context
import com.primerproyecto.raeco2.Animal

public class DaoAnimales(context: Context?)  {

    private val dataBase : DbHelper = DbHelper(context)// ver si no es esto

    fun find(localiza:Int,esPrehistorico:Boolean): Animal {

        return dataBase.obtenerAnimalTablaRelacionXIdLocalizacion(localiza,esPrehistorico)
    }




}
