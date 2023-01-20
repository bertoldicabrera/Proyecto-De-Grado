package com.primerproyecto.raeco2.bd

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.R

public class DbHelper (context: Context?) :
    SQLiteOpenHelper(context,
        DATABASE_NOMBRE,
        null,
        DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {

        println("c DB XXXXXXXXXXXX")
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TABLE_ANIMALES + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "descripcion TEXT NOT NULL," +
                    "url TEXT NOT NULL," +
                    "objeto3D TEXT NOT NULL," +
                    "region INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ANIMALES)
        onCreate(sqLiteDatabase)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NOMBRE = "Raeco.db"
        const val TABLE_ANIMALES = "t_animales"

    }

    fun existeAnimalDbHelper(nombre: String): Boolean {
        val TABLE_NAME =TABLE_ANIMALES
        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_NAME where nombre = '$nombre'"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.getCount() <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
    fun insertarAnimalDbHelper(nombre: String, descripcion: String, url: String, objeto: String, region: Int){

        //Seguir acá ver como se hace el insert

    }

    fun obtenerAnimalDbHelperxRegion(region: Int): Animal {
        val animal = Animal(null,null,null,null,null)
        animal.setearRegionAnimal(region)
        val db = writableDatabase
        var index: Int


        val selectQuery = R.string.queryObtenerAnimalDadaRegion +region
        val cursor = db.rawQuery(selectQuery.toString(), null)

        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {

                // probar si acepta numeros el cursor.getColumnIndex
                index = cursor.getColumnIndexOrThrow("NAME")
                animal.setearNombreAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow("DESC")
                animal.setearDescripcionAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow("URL")
                animal.setearUrlAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow("OBJ")
                animal.setearObjetoAnimal(cursor.getString(index))
            }
        }
        cursor.close()
        return animal
    }



}