package com.primerproyecto.raeco2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle

import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.primerproyecto.raeco2.bd.DbHelper

import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var message: TextView? = null
    private var btnCrear : Button? = null;
    private var simple_btn : Button? = null;


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.menssage)
        message?.text = "Puto" //Carga este dato en la pantalla
        permisosIniciarLocalizacion()

        simple_btn = findViewById(R.id.button)
        simple_btn?.setOnClickListener {
            crearAnimal()
        }

        btnCrear = findViewById(R.id.btnCrear)

        btnCrear?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                crearBD()
            }
        })

    }

    //lamado por el boton
    private fun crearBD(){
        val dbHelper = DbHelper(this@MainActivity)
        val db: SQLiteDatabase = dbHelper.getWritableDatabase()
        if (db != null) {
            Toast.makeText(this@MainActivity, "BASE DE DATOS CREADA", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(
                this@MainActivity,
                "ERROR AL CREAR BASE DE DATOS",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    //llamado al inciar requiere los permisos e inicia la localizacion
private fun permisosIniciarLocalizacion()
{
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        !== PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        !== PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1000
        )
    } else {
        iniciarLocalizacion()
    }
}

    private fun crearAnimal()
    {
    //https://developers.google.com/ar/develop/scene-viewer
            val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
            sceneViewerIntent.data = Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf")
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
            startActivity(sceneViewerIntent)
    }

    //hacer un get y devuelve true si el sitio retorna un response code200
    private fun sitioUp(urlValidar:String): Boolean {
        var estaUp= false
        val gfgThread = Thread {
            try {
                val connection = URL(urlValidar).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    estaUp= true
                }
                connection.disconnect()
            } catch (e: java.lang.Exception) {
            println("****GET request responseCode $e")
        }
        }

        gfgThread.start()

        return estaUp
    }

    private fun iniciarLocalizacion() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val localization = Localization()
        localization.setMainActivity(this, message)
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsEnabled) {
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, localization)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, localization)
        message?.text = "Localizacion agregada"
    }


    private fun createIntentUriExplicito() : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()

//obtener estos datos del usuario
        //SEGUIR ACÁ, ARREGLAR LAS CONSULTAS A LA BASE DE DATOS,
         //DADO QUE CAMBIAMOS EL NOMBRE DE GETS Y SETS DE ANIMAL
        var config:Configuracion= TODO()
        var voAnimalMostrar:voAnimal = TODO()
        var cualURL:Boolean = TODO()

        val params = cargarParametrosDelAnimal(config, voAnimalMostrar, cualURL)
        params.forEach { (key, value) -> intentUri.appendQueryParameter(key, value) }
        return intentUri.build()
    }

    //Pasar a configracion
    //configuracion, voAnimal

    private fun cargarParametrosDelAnimal(config:Configuracion, voAnimalMostrar: voAnimal, cualURL:Boolean) : HashMap<String, String> {

        //https://developers.google.com/ar/develop/scene-viewer
        val map = HashMap<String, String> ()
        map["file"] = {
            if(cualURL==true)
            {
                voAnimalMostrar.obtenerObjetoAnimal()
            }else
            {
                voAnimalMostrar.obtenerObjetoBackUpAnimal()
            }
        }.toString()

        map["title"] = voAnimalMostrar.obtenerNombreAnimal().toString()//si

        map["link"] = voAnimalMostrar.obtenerLinkAnimal().toString()//si

        map["mode"] = "ar_preferred"

        map["resizable"] = config.obtenerTamano().toString()// si

        return map
    }


}