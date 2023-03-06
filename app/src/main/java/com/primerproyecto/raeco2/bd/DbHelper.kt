package com.primerproyecto.raeco2.bd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.primerproyecto.raeco2.Animal
import com.primerproyecto.raeco2.Localization

public class DbHelper (context: Context?) :
    SQLiteOpenHelper(context,
        DATABASE_NOMBRE,
        null,
        DATABASE_VERSION) {


    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        println("Inicio crear tablas XXXXXXXXXXXX")


        //Tabla Animales
       sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_ANIMALES + "(" + KEY_IDANIMAL+
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME+
                    " TEXT NOT NULL," + KEY_DES+
                    " TEXT NOT NULL," + KEY_URL+
                    " TEXT NOT NULL," + KEY_OBJ+
                    " TEXT NOT NULL," + KEY_OBJBACKUP+
                    " TEXT NOT NULL," + KEY_SONIDO+
                    " TEXT NOT NULL)"
        )

        //tabla Localizaciones
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOCALIZACIONES+ " ("
                    +KEY_LOCALIZACION_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NOMBRE_LOCALIZACION+ " TEXT,"
                    + KEY_LONGITUD+ " REAL NOT NULL,"
                    + KEY_LATITUD+ " REAL NOT NULL)"
        )
        //Tabla Region (Relacion)
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_REGION " +
                "($KEY_REGION INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_LOCALIZACION_ID_EN_REGION INTEGER, " +
                "$KEY_ANIMAL_ID_EN_REGION INTEGER, " +
                "FOREIGN KEY ($KEY_ANIMAL_ID_EN_REGION) REFERENCES $TABLE_ANIMALES($KEY_IDANIMAL), " +
                "FOREIGN KEY ($KEY_LOCALIZACION_ID_EN_REGION) REFERENCES $TABLE_LOCALIZACIONES($KEY_LOCALIZACION_ID))"
        )


        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ANIMALES ($KEY_NAME, $KEY_DES, $KEY_URL,"+
                " $KEY_OBJ, $KEY_OBJBACKUP, $KEY_SONIDO) VALUES ('Carpincho', 'Animal  popular'," +
                " 'https://es.wikipedia.org/wiki/Canis_familiaris', 'https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Carpincho/scene.gltf', 'https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Carpincho/scene.gltf', 'https://github.com/bertoldicabrera/RecursosRaeco/blob/main/Perro/perro.mp3?raw=true')"

        )
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_LOCALIZACIONES ($KEY_NOMBRE_LOCALIZACION, $KEY_LONGITUD,$KEY_LATITUD)"+
                "VALUES ('Fernando',-34.902355,-56.187859)"
        )

        sqLiteDatabase.execSQL("INSERT INTO $TABLE_REGION ($KEY_LOCALIZACION_ID_EN_REGION, $KEY_ANIMAL_ID_EN_REGION)"+
        "VALUES (1, 1)"
        )
//Otro animal
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ANIMALES ($KEY_NAME, $KEY_DES, $KEY_URL,"+
                " $KEY_OBJ, $KEY_OBJBACKUP, $KEY_SONIDO) VALUES ('Vaca', 'Animal de pastoreo'," +
                " 'https://es.wikipedia.org/wiki/Bos_taurus', 'https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Carpincho/scene.gltf', 'https://raw.githubusercontent.com/bertoldicabrera/RecursosRaeco/main/Carpincho/scene.gltf',  'https://github.com/bertoldicabrera/RecursosRaeco/blob/main/Vaca/vaca.mp3?raw=true')"

        )
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_LOCALIZACIONES ($KEY_NOMBRE_LOCALIZACION, $KEY_LONGITUD,$KEY_LATITUD)"+
                "VALUES ('Sebastian',-34.886360,-56.147075)"
        )

        sqLiteDatabase.execSQL("INSERT INTO $TABLE_REGION ($KEY_LOCALIZACION_ID_EN_REGION, $KEY_ANIMAL_ID_EN_REGION)"+
                "VALUES (2, 2)"
        )

        println("Fin Crear tablas")

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



//Puede devolver un animal nulo, con solo la region seteada.
    fun obtenerAnimalTablaRelacionXIdLocalizacion(idLocalizacion: Int): Animal {

        val animal = Animal(null,null,null,null,null, null)
        val db = this.writableDatabase
        var index: Int

        val selectQuery="SELECT A.* FROM $TABLE_ANIMALES AS A INNER JOIN $TABLE_REGION AS R "+
                        "ON R.$KEY_LOCALIZACION_ID_EN_REGION=$idLocalizacion AND R.$KEY_ANIMAL_ID_EN_REGION=A.$KEY_IDANIMAL" +
                        " order by random() limit 1;"
//Suponiendo que trae un animal
        Log.d("DBHelper BuscoAnimal 140", "${selectQuery}")
        val cursor = db.rawQuery(selectQuery, null)
        Log.d("DBHelper BuscoAnimal 142", "${cursor.count}")

    if (cursor != null) {
          //  cursor.moveToFirst()
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
         Log.d("DBHelper BuscoAnimal 162", "${animal.obtenerNombreAnimal()}")
        return animal
    }

    //Devuelve -1 Si no encuentra la Localizacion
    fun obtenerLocalizacion(latitud:Double?, longitud:Double?): Int {
        var IdLocalizacion =-1
        val db = this.writableDatabase
        var index: Int
        //falta acá
        val selectQuery ="select * from $TABLE_LOCALIZACIONES where $KEY_LATITUD=$latitud AND $KEY_LONGITUD=$longitud"
        Log.d("DBHelper obtenerlocalizacion-176","$selectQuery")
        val cursor = db.rawQuery(selectQuery, null)
        Log.d("DBHelper obtenerlocalizacion-176","${cursor.count}")
        if (cursor != null) {
          //  cursor.moveToFirst()
            while (cursor.moveToNext()) {
                index = cursor.getColumnIndexOrThrow(KEY_LOCALIZACION_ID)
                IdLocalizacion= cursor.getInt(index)
                Log.d("DBHelper obtenerlocalizacion-181","$IdLocalizacion")
            }
        }
        cursor.close()

        return IdLocalizacion
    }

    //Creo no es necesario
    fun esCercanoALocalizacion(latitud: Double?, longitud: Double?): Boolean {
        var existe= true
        val db = this.writableDatabase
        val selectQuery = "Select * from $TABLE_LOCALIZACIONES where $KEY_LATITUD=$latitud AND $KEY_LONGITUD=$longitud"

        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.getCount() <= 0) {
            existe= false
        }
        cursor.close()
        return existe
    }

    fun devolverLocalizacionesBd(): MutableList<Localization> {

        Log.d("DBHelper-devolverLocalizaiones 204","Entro")
        var listaLocalizacionesSalida = mutableListOf<Localization>()
        Log.d("DBHelper-devolverLocalizaiones 206","${listaLocalizacionesSalida.size}")

        val db = this.writableDatabase
        Log.d("devolverlocalizaciones -209","$db")
        var selectQuery = "SELECT * FROM " + "$TABLE_LOCALIZACIONES"
        Log.d("DBHelper-devolverLocalizaiones 209","$selectQuery")
        //var cursor = db.rawQuery(selectQuery, null)
        var cursor = db.rawQuery("SELECT * FROM t_Localizaciones", null)
         Log.d("DBHelper-devolverLocalizaiones 211","${ cursor.count}")
        if (cursor != null) {
          //  cursor.moveToFirst()
            while (cursor.moveToNext()) {
                var localizacionSalida:Localization= Localization()
                var index = cursor.getColumnIndexOrThrow(KEY_NOMBRE_LOCALIZACION)
                localizacionSalida.setearNombreLocalizacion(cursor.getString(index))
                Log.d("DBHelper-devolverLocalizaciones 223","${ localizacionSalida.obtenerNombreLocalizacion()} index${index}")
                index = cursor.getColumnIndexOrThrow(KEY_LATITUD)
                localizacionSalida.setearLatitud(cursor.getDouble(index))
                index = cursor.getColumnIndexOrThrow(KEY_LONGITUD)
                localizacionSalida.setearLongitud(cursor.getDouble(index))

                listaLocalizacionesSalida.add(localizacionSalida)
                Log.d("DBHelper-devolverLocalizaciones 229","${ listaLocalizacionesSalida.size}")
            }
            cursor.close()
        }
        Log.d("DBHelper-devolverLocalizaciones 233","${ listaLocalizacionesSalida[0].obtenerNombreLocalizacion()}")
        Log.d("DBHelper-devolverLocalizaciones 234","${ listaLocalizacionesSalida[0].obtenerLongitud()}")
        Log.d("DBHelper-devolverLocalizaciones 235","${ listaLocalizacionesSalida[0].obtenerLatitud()}")
        Log.d("DBHelper-devolverLocalizaciones 236","${ listaLocalizacionesSalida.size}")

        return listaLocalizacionesSalida
    }


    ///Para abajo no se usan....

   private fun insertarLocalizacion(localizacion: Localization):Long {

        val nombre = localizacion.obtenerNombreLocalizacion()
        val latitud= localizacion.obtenerLatitud()
        val longitud= localizacion.obtenerLongitud()

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_NOMBRE_LOCALIZACION, nombre)
        contentValues.put(KEY_LATITUD,latitud )
        contentValues.put(KEY_LONGITUD,longitud )

        // Inserting Row
        //https://www.javatpoint.com/kotlin-android-sqlite-tutorial
        val success = db.insert(TABLE_LOCALIZACIONES, null, contentValues)
        //2nd argument is String containing nullColumnHack(ver teorico)
        db.close() // Closing database connection
        return success //el ID de fila de la fila recién insertada, o -1 si ocurrió un error
    }

    private fun eliminarLocalizacion(latitud: Double?, longitud: Double?):Int {
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

    private fun insertarAnimalDbHelper(animalNew:Animal):Long{

        val nombre = animalNew.obtenerNombreAnimal()
        val descripcion= animalNew.obtenerDescripcionAnimal()
        val url= animalNew.obtenerLinkAnimal()
        val objetoBack= animalNew.obtenerObjetoBackUpAnimal()
        val objeto3D= animalNew.obtenerObjetoAnimal()
        val sonido=animalNew.obtenerSonido()
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, nombre)
        contentValues.put(KEY_DES,descripcion )
        contentValues.put(KEY_URL,url )
        contentValues.put(KEY_OBJBACKUP,objetoBack )
        contentValues.put(KEY_OBJ,objeto3D )
        contentValues.put(KEY_SONIDO,sonido )

        val success = db.insert(TABLE_ANIMALES, null, contentValues)
        //2nd argument is String containing nullColumnHack(ver teorico)
        db.close() // Closing database connection
        return success
    }
    private fun eliminarAnimalDbHelper(nombre:String):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, nombre) //  Id
        // Deleting Row
        val success = db.delete(TABLE_ANIMALES, "$KEY_NAME=$nombre",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    private fun insertarRegion(idLocalizacion:Int,idAnimal:Int):Long {



        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_LOCALIZACION_ID_EN_REGION,idLocalizacion )
        contentValues.put(KEY_ANIMAL_ID_EN_REGION,idAnimal )
        val success = db.insert(TABLE_REGION, null, contentValues)

        db.close() // Closing database connection
        return success //el ID de fila de la fila recién insertada, o -1 si ocurrió un error
    }


}