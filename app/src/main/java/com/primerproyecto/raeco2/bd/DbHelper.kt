package com.primerproyecto.raeco2.bd

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.Localization

public class DbHelper (context: Context?) :
    SQLiteOpenHelper(context,
        DATABASE_NOMBRE,
        null,
        DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {

        CrearTablas(sqLiteDatabase)

        //Localizaciones
        insertarLocalizacion (sqLiteDatabase, "Fernando",37.4219983,-122.084000,false)
        insertarLocalizacion (sqLiteDatabase, "Sebastian",-34.886360,-56.147075, false)
        insertarLocalizacion (sqLiteDatabase, "Sebastian",-34.886360,-56.147075, true)
        insertarLocalizacion (sqLiteDatabase, "Ramiro",-34.886366,-56.147075, true)
//Carpincho
        insertarAnimalDbHelper(sqLiteDatabase,"Carpincho", "Carpincho o Capivara","https://es.wikipedia.org/wiki/Hydrochoerus_hydrochaeris", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Carpincho/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/Carpincho/scene.gltf","https://github.com/bertoldicabrera/RecursosRaeco/blob/main/Carpincho/carpincho.mp3?raw=true", false)
       insertarRegion(sqLiteDatabase, 1, 1)

//Pinguino
        insertarAnimalDbHelper(sqLiteDatabase,"Pinguino", "Pinguino", "https://es.wikipedia.org/wiki/Spheniscidae", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Pinguino/scene.gltf", "https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/Pinguino/scene.gltf",  "https://github.com/bertoldicabrera/RecursosRaeco/blob/main/Pinguino/pinguino.mp3?raw=true", false)
        insertarRegion(sqLiteDatabase, 2, 2)
//Aveztruz
        insertarAnimalDbHelper(sqLiteDatabase,"Avestruz", "Avestruz", "https://es.wikipedia.org/wiki/Struthio_camelus", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/avestruz/scene.gltf", "https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/avestruz/scene.gltf",  "https://github.com/bertoldicabrera/RecursosRaeco/blob/main/avestruz/avestruz.mp3?raw=true", false)
        insertarRegion(sqLiteDatabase, 2, 3)
//Danta
        insertarAnimalDbHelper(sqLiteDatabase,"Danta", "Danta", "https://es.wikipedia.org/wiki/Tapirus_terrestris", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/danta/scene.gltf", "https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/danta/scene.gltf",  "https://github.com/bertoldicabrera/RecursosRaeco/blob/main/danta/danta.mp3?raw=true", false)
        insertarRegion(sqLiteDatabase, 2, 4)
//Lion
        insertarAnimalDbHelper(sqLiteDatabase,"Leon", "Leon", "https://es.wikipedia.org/wiki/Panthera_leo", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/lion/scene.gltf", "https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/lion/scene.gltf",  "https://github.com/bertoldicabrera/RecursosRaeco/blob/main/lion/lion.mp3?raw=true", false)
        insertarRegion(sqLiteDatabase, 1, 5)

        insertarAnimalDbHelper(sqLiteDatabase,"Ankylosaurus magniventris", "Ankylosaurus magniventris","https://es.wikipedia.org/wiki/Ankylosaurus_magniventris", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/ankylosaurus/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/ankylosaurus/scene.gltf","", true)
        insertarRegion(sqLiteDatabase, 3, 6)

        insertarAnimalDbHelper(sqLiteDatabase,"Brachiosaurus altithorax", "Brachiosaurus altithorax","https://es.wikipedia.org/wiki/Brachiosaurus_altithorax", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/brachiosaurus/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/brachiosaurus/scene.gltf","", true)
        insertarRegion(sqLiteDatabase, 3, 7)

        insertarAnimalDbHelper(sqLiteDatabase,"Mammoth", "Mammoth","https://en.wikipedia.org/wiki/Mammoth", "https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/mammoth/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/mammoth/scene.gltf","", true)
        insertarRegion(sqLiteDatabase, 3, 8)

        insertarAnimalDbHelper(sqLiteDatabase,"Parasaurolophus", "Parasaurolophus","https://es.wikipedia.org/wiki/Parasaurolophus","https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/parasaurolophus/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/parasaurolophus/scene.gltf", "",true)
        insertarRegion(sqLiteDatabase, 3, 9)

        insertarAnimalDbHelper(sqLiteDatabase,"Velociraptor", "Velociraptor","https://es.wikipedia.org/wiki/Velociraptor","https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/prehistoria/velociraptor/scene.gltf","https://gitlab.com/bertoldicabrera/animales3d/-/raw/main/preHistoria/velociraptor/scene.gltf","", true)
        insertarRegion(sqLiteDatabase, 3, 10)

         }

    private fun CrearTablas(sqLiteDatabase: SQLiteDatabase){
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_ANIMALES + "(" + KEY_IDANIMAL+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME+
                    " TEXT NOT NULL," + KEY_DES+
                    " TEXT NOT NULL," + KEY_URL+
                    " TEXT NOT NULL," + KEY_OBJ+
                    " TEXT NOT NULL," + KEY_OBJBACKUP+
                    " TEXT NOT NULL," + KEY_SONIDO+
                    " TEXT NOT NULL," + KEY_ESPREHIS+
                    " BOOLEAN NOT NULL)"
        )

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOCALIZACIONES+ " ("
                    +KEY_LOCALIZACION_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NOMBRE_LOCALIZACION+ " TEXT,"
                    + KEY_LONGITUD+ " REAL NOT NULL,"
                    + KEY_LATITUD+ " REAL NOT NULL,"
                    + KEY_ESPREHIS+" BOOLEAN NOT NULL)"
        )

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_REGION " +
                "($KEY_REGION INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_LOCALIZACION_ID_EN_REGION INTEGER, " +
                "$KEY_ANIMAL_ID_EN_REGION INTEGER, " +
                "FOREIGN KEY ($KEY_ANIMAL_ID_EN_REGION) REFERENCES $TABLE_ANIMALES($KEY_IDANIMAL), " +
                "FOREIGN KEY ($KEY_LOCALIZACION_ID_EN_REGION) REFERENCES $TABLE_LOCALIZACIONES($KEY_LOCALIZACION_ID))"
        )
    }


    private fun insertarLocalizacion(sqLiteDatabase: SQLiteDatabase, nombreLocal:String, latitud:Double, longitud:Double, esPrehistorico: Boolean) {
        var esPrehistoricoInt= 0
        if(esPrehistorico)
            esPrehistoricoInt=1
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_LOCALIZACIONES ($KEY_NOMBRE_LOCALIZACION, $KEY_LATITUD, $KEY_LONGITUD, $KEY_ESPREHIS)"+
                "VALUES ('${nombreLocal.toString()}', '${latitud.toDouble()}', '${longitud.toDouble()}','${esPrehistoricoInt.toInt()}')"
        )
    }



    private fun insertarAnimalDbHelper(sqLiteDatabase: SQLiteDatabase,nombreAni: String,descrip: String, linkDescr: String, obj3D:String, obj3DbackUp: String, sonido: String, esPrehistorico: Boolean ){
       var esPrehistoricoInt= 0
        if(esPrehistorico)
            esPrehistoricoInt=1

        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ANIMALES ($KEY_NAME, $KEY_DES, $KEY_URL,"+
                " $KEY_OBJ, $KEY_OBJBACKUP, $KEY_SONIDO, $KEY_ESPREHIS) VALUES ('${nombreAni.toString()}', '${descrip.toString()}'," +
                " '${linkDescr.toString()}', '${obj3D.toString()}', '${obj3DbackUp.toString()}', '${sonido.toString()}', '${esPrehistoricoInt.toInt()}')"

        )
    }


    private fun insertarRegion(sqLiteDatabase: SQLiteDatabase, idloc:Int, idAnimalReg:Int){

        sqLiteDatabase.execSQL("INSERT INTO $TABLE_REGION ($KEY_LOCALIZACION_ID_EN_REGION, $KEY_ANIMAL_ID_EN_REGION)"+
                " VALUES ('${idloc.toInt()}', '${idAnimalReg.toInt()}')")

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REGION)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALES)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALIZACIONES)
                onCreate(sqLiteDatabase)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NOMBRE = "Raeco2.db"
        const val TABLE_ANIMALES = "t_animales"
        const val KEY_IDANIMAL= "id_animal"
        const val KEY_NAME= "nombre"
        const val KEY_DES= "descripcion"
        const val KEY_URL= "url"
        const val KEY_OBJBACKUP= "objetoBackUp"
        const val KEY_OBJ= "objeto"
        const val KEY_SONIDO= "sonido"
        const val KEY_ESPREHIS="esprehis"
        const val KEY_LOCALIZACION= "localizacion"
        const val TABLE_LOCALIZACIONES = "t_Localizaciones"
        const val KEY_LOCALIZACION_ID= "localizacionId"
        const val KEY_NOMBRE_LOCALIZACION= "nombreLocalizacion"
        const val KEY_LATITUD= "latitud"
        const val KEY_LONGITUD= "longitud"
        const val TABLE_REGION= "t_region"
        const val KEY_REGION= "id_region"
        const val KEY_LOCALIZACION_ID_EN_REGION= "idLocalizacionRegion"
        const val KEY_ANIMAL_ID_EN_REGION= "idAnimalRegion"

    }


    fun existeAnimalDbHelper(nombre: String?): Boolean {
        var existe=true

        val db = this.writableDatabase
        val selectQuery = "Select * from $TABLE_ANIMALES where $KEY_NAME = '$nombre'"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.getCount() <= 0) {
            existe= false
        }
        cursor.close()
        return existe
    }


@SuppressLint("SuspiciousIndentation")
fun obtenerAnimalTablaRelacionXIdLocalizacion(idLocalizacion: Int, esPrehistorico:Boolean): Animal {

        val animal = Animal(null,null,null,null,null, null)
        val db = this.writableDatabase
        var index: Int
        var esPrehistoricoInt=0
    if(esPrehistorico)
        esPrehistoricoInt=1

        val selectQuery="SELECT A.* FROM $TABLE_ANIMALES AS A INNER JOIN $TABLE_REGION AS R "+
                        "ON R.$KEY_LOCALIZACION_ID_EN_REGION=$idLocalizacion AND R.$KEY_ANIMAL_ID_EN_REGION=A.$KEY_IDANIMAL AND A.$KEY_ESPREHIS=$esPrehistoricoInt"+
                        " order by random() limit 1;"
        val cursor = db.rawQuery(selectQuery, null)

    if (cursor != null) {
            while (cursor.moveToNext()) {

                index = cursor.getColumnIndexOrThrow(KEY_NAME)
                animal.setearNombreAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_DES)
                animal.setearDescripcionAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_URL)
                animal.setearLinkAnimal(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_OBJBACKUP)
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

    fun obtenerLocalizacion(latitud:Double?, longitud:Double?, esPrehistorico:Boolean): Int {
        var IdLocalizacion =-1
        val db = this.writableDatabase
        var index: Int
        var esPrehistoricoInt=0
        if(esPrehistorico)
            esPrehistoricoInt=1

        val selectQuery ="select * from $TABLE_LOCALIZACIONES where $KEY_LATITUD=$latitud AND $KEY_LONGITUD=$longitud AND $KEY_ESPREHIS=$esPrehistoricoInt"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {

            while (cursor.moveToNext()) {
                index = cursor.getColumnIndexOrThrow(KEY_LOCALIZACION_ID)
                IdLocalizacion= cursor.getInt(index)
            }
        }
        cursor.close()

        return IdLocalizacion
    }

    fun devolverLocalizacionesBd(): MutableList<Localization> {

        var listaLocalizacionesSalida = mutableListOf<Localization>()
        val db = this.writableDatabase
        var cursor = db.rawQuery("SELECT * FROM t_Localizaciones", null)
        if (cursor != null) {

            while (cursor.moveToNext()) {
                var localizacionSalida:Localization= Localization()
                var index = cursor.getColumnIndexOrThrow(KEY_NOMBRE_LOCALIZACION)
                localizacionSalida.setearNombreLocalizacion(cursor.getString(index))
                index = cursor.getColumnIndexOrThrow(KEY_LATITUD)
                localizacionSalida.setearLatitud(cursor.getDouble(index))
                index = cursor.getColumnIndexOrThrow(KEY_LONGITUD)
                localizacionSalida.setearLongitud(cursor.getDouble(index))
                listaLocalizacionesSalida.add(localizacionSalida)
            }
            cursor.close()
        }

        return listaLocalizacionesSalida
    }

}