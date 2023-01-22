package com.primerproyecto.raeco2.bd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.Localization
import com.primerproyecto.raeco2.R

public class DbHelper (context: Context?) :
    SQLiteOpenHelper(context,
        DATABASE_NOMBRE,
        null,
        DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {

        println("c DB XXXXXXXXXXXX")
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TABLE_ANIMALES + "(" + KEY_IDAnimal+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME+
                    " TEXT NOT NULL," + KEY_DES+
                    " TEXT NOT NULL," + KEY_URL+
                    " TEXT NOT NULL," + KEY_OBJ+
                    " TEXT NOT NULL," + KEY_REG+
                    " INTEGER NOT NULL)"
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
        const val KEY_IDAnimal= "id"
        const val KEY_NAME= "name"
        const val KEY_DES= "descripcion"
        const val KEY_URL= "url"
        const val KEY_OBJ= "objeto"
        const val KEY_REG= "region"
    }

    fun existeAnimalDbHelper(nombre: String?): Boolean {

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
    fun insertarAnimalDbHelper(animalNew:Animal):Long{

        val nombre = animalNew.obtenerNombreAnimal()
        var descripcion= animalNew.obtenerDescripcionAnimal()
        var url= animalNew.obtenerUrlAnimal()
        var objeto3D= animalNew.obtenerObjetoAnimal()
        var region=animalNew.obtenerRegionAnimal()

        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, nombre)
        contentValues.put(KEY_DES,descripcion )
        contentValues.put(KEY_URL,url )
        contentValues.put(KEY_OBJ,objeto3D )
        contentValues.put(KEY_REG,region )

        // Inserting Row
        //https://www.javatpoint.com/kotlin-android-sqlite-tutorial
        val success = db.insert(TABLE_ANIMALES, null, contentValues)
        //2nd argument is String containing nullColumnHack(ver teorico)
        db.close() // Closing database connection
        return success
        //Seguir acá ver como se hace el insert


    }
    fun eliminarAnimalDbHelper(nombre:String):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, nombre) //  Id
        // Deleting Row
        val success = db.delete(TABLE_ANIMALES,KEY_NAME+"="+nombre,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
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

    fun obtenerLocalizacion(latitud:Double?, longitud:Double?): Localization {
        val localizacion = Localization(null,null,null,null)
        localizacion.setearLatitud(latitud)
        localizacion.setearLongitud(longitud)
        val db = writableDatabase
        var index: Int
        //falta acá
        val selectQuery = R.string.queryObtenerRegionXLocalizacion // ver como argar el where longitud=? and latitud=?
        val cursor = db.rawQuery(selectQuery.toString(), null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
//Cambiar todos los sets por el correspondiente al de localizacion
                // probar si acepta numeros el cursor.getColumnIndex
                index = cursor.getColumnIndexOrThrow("id")
                localizacion.setearID(cursor.getInt(index))
                index = cursor.getColumnIndexOrThrow("regionNombre")
                localizacion.setearNombreRegion(cursor.getString(index))
            }
        }
        cursor.close()

        return localizacion
    }

    fun existeLocalizacion(latitud: Double?, longitud: Double?): Boolean {
        //falta desarrollar
    return true
    }

    fun insertarLocalizacion(localizacion: Localization) {
//falta desarrollar
    }

    fun eliminarLocalizacion(latitud: Double?, longitud: Double?) {
//falta desarrollar
    }


}