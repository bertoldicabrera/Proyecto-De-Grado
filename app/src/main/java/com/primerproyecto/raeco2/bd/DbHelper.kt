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
            "CREATE TABLE IF NOT EXISTS " + TABLE_ANIMALES + "(" + KEY_IDAnimal+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME+
                    " TEXT NOT NULL," + KEY_DES+
                    " TEXT NOT NULL," + KEY_URL+
                    " TEXT NOT NULL," + KEY_OBJ+
                    " TEXT NOT NULL," + KEY_REG+
                    " TEXT NOT NULL," + KEY_SONIDO+
                    " TEXT NOT NULL)"
        )


        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOCALIZACIONES + "(" + KEY_IDLocalizacion+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOMBRE_REGION+
                    " TEXT NOT NULL," + KEY_LONGITUD+
                    " REAL NOT NULL," + KEY_LATITUD+
                    " REAL NOT NULL)"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALES)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALIZACIONES)
        onCreate(sqLiteDatabase)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NOMBRE = "Raeco2.db"
        const val TABLE_ANIMALES = "t_animales"
        const val KEY_IDAnimal= "id"
        const val KEY_NAME= "name"
        const val KEY_DES= "descripcion"
        const val KEY_URL= "url"
        const val KEY_URLBACKUP= "urlBackUp"
        const val KEY_OBJ= "objeto"
        const val KEY_SONIDO= "sonido"
        const val KEY_REG= "registro"
        const val TABLE_LOCALIZACIONES = "t_Localizaciones"
        const val KEY_IDLocalizacion= "idLocalizacion"
        const val KEY_NOMBRE_REGION= "nombreRegion"
        const val KEY_LATITUD= "latitud"
        const val KEY_LONGITUD= "longitud"
    }

    fun existeAnimalDbHelper(nombre: String?): Boolean {
var existe=true
        val TABLE_NAME =TABLE_ANIMALES
        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_NAME where nombre = '$nombre'"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.getCount() <= 0) {
            existe= false
        }
        cursor.close()
        return existe
    }
    fun insertarAnimalDbHelper(animalNew:Animal):Long{

        val nombre = animalNew.obtenerNombreAnimal()
        val descripcion= animalNew.obtenerDescripcionAnimal()
        val url= animalNew.obtenerLinkAnimal()
        val urlBack= animalNew.obtenerObjetoBackUpAnimal()
        val objeto3D= animalNew.obtenerObjetoAnimal()
        val region=animalNew.obtenerRegionAnimal()
        val sonido=animalNew.obtenerSonido()
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, nombre)
        contentValues.put(KEY_DES,descripcion )
        contentValues.put(KEY_URL,url )
        contentValues.put(KEY_URLBACKUP,urlBack )
        contentValues.put(KEY_OBJ,objeto3D )
        contentValues.put(KEY_REG,region )
        contentValues.put(KEY_SONIDO,sonido )

        val success = db.insert(TABLE_ANIMALES, null, contentValues)
        //2nd argument is String containing nullColumnHack(ver teorico)
        db.close() // Closing database connection
        return success
    }
    fun eliminarAnimalDbHelper(nombre:String):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, nombre) //  Id
        // Deleting Row
        val success = db.delete(TABLE_ANIMALES, "$KEY_NAME=$nombre",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    fun obtenerAnimalDbHelperxRegion(region: String?): Animal {
        val animal = Animal(null,null,null,null,null, null, null)
        animal.setearRegionAnimal(region)
        val db = writableDatabase
        var index: Int
        var consulta =R.string.queryObtenerAnimalDadaRegion

        val selectQuery = "$consulta $region ORDER BY random() \n" +
                "LIMIT 1;"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {

                index = cursor.getColumnIndexOrThrow(KEY_NAME)
                animal.setearNombreAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_DES)
                animal.setearDescripcionAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_URL)
                animal.setearLinkAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_URLBACKUP)
                animal.setearUrlBackUpAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_OBJ)
                animal.setearObjetoAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_SONIDO)
                animal.setearSonido(cursor.getString(index))
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
        val consulta =R.string.queryObtenerLocalizacionxcoordenadas
        val selectQuery = "$consulta $KEY_LATITUD=$latitud AND $KEY_LONGITUD=$longitud"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {

                index = cursor.getColumnIndexOrThrow(KEY_IDLocalizacion)
                localizacion.setearId(cursor.getInt(index))
                index = cursor.getColumnIndexOrThrow(KEY_NOMBRE_REGION)
                localizacion.setearNombreRegion(cursor.getString(index))
            }
        }
        cursor.close()

        return localizacion
    }

    fun existeLocalizacion(latitud: Double?, longitud: Double?): Boolean {
        var existe= true
        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_LOCALIZACIONES where $KEY_LATITUD=$latitud AND $KEY_LONGITUD=$longitud"

        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.getCount() <= 0) {
            existe= false
        }
        cursor.close()
        return existe
    }

    fun insertarLocalizacion(localizacion: Localization):Long {

        val nombre = localizacion.obtenerNombreRegion()
        val latitud= localizacion.obtenerLatitud()
        val longitud= localizacion.obtenerLongitud()

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_NOMBRE_REGION, nombre)
        contentValues.put(KEY_LATITUD,latitud )
        contentValues.put(KEY_LONGITUD,longitud )

        // Inserting Row
        //https://www.javatpoint.com/kotlin-android-sqlite-tutorial
        val success = db.insert(TABLE_LOCALIZACIONES, null, contentValues)
        //2nd argument is String containing nullColumnHack(ver teorico)
        db.close() // Closing database connection
        return success //el ID de fila de la fila recién insertada, o -1 si ocurrió un error
    }

    fun eliminarLocalizacion(latitud: Double?, longitud: Double?):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_LATITUD, latitud) //  Id
        contentValues.put(KEY_LONGITUD, longitud)
        // Deleting Row
        val success = db.delete(TABLE_LOCALIZACIONES,KEY_LATITUD+"="+latitud+"AND"+ KEY_LONGITUD+"="+longitud,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
        /*
        el número de filas afectadas si se pasa una cláusula where,
         0 en caso contrario. Para eliminar todas las filas y obtener un recuento,
         pase "1" como cláusula where.
         */
    }


}