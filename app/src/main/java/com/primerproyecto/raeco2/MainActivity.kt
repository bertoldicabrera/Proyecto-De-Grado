package com.primerproyecto.raeco2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {

    private var message: TextView? = null
    private var btnCrear : Button? = null;
    private var simple_btn : Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        sceneViewerIntent.data =
            Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf")
        sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
        startActivity(sceneViewerIntent)
       // simple_btn?.setOnClickListener {
//            val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
//            println("32" + sceneViewerIntent)
//            val intentUri = createIntentUri()
//            println("34" + intentUri)
//            println("Tu vieja en tanga")
            /* Alternative version */
            /* val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                    .appendQueryParameter("file", "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf")
                    .appendQueryParameter("mode", "ar_only")
                    .appendQueryParameter("title", "Duck")
                    .appendQueryParameter("resizable", "false")
                    .build() */
//            sceneViewerIntent.setData(intentUri);
//            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
//            startActivity(sceneViewerIntent)
////        //}
//        println("ON CREATE TEST  42")
//        message = findViewById(R.id.menssage)
//        message?.text = "Puto"
//        println("${message?.text}")
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            !== PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//            !== PackageManager.PERMISSION_GRANTED
//        ) {
//            println("ON CREATE TEST  31")
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                1000
//            )
//        } else {
//            println("ON CREATE TEST  38")
//            iniciarLocalizacion()
//        }
//
//        btnCrear = findViewById(R.id.btnCrear)
//
//        btnCrear?.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View?) {
//                val dbHelper = DbHelper(this@MainActivity)
//                val db: SQLiteDatabase = dbHelper.getWritableDatabase()
//                if (db != null) {
//                    Toast.makeText(this@MainActivity, "BASE DE DATOS CREADA", Toast.LENGTH_LONG)
//                        .show()
//                } else {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "ERROR AL CREAR BASE DE DATOS",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        })







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


    private fun createIntentUri() : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
        val params = getIntentParams()
        params.forEach { (key, value) -> intentUri.appendQueryParameter(key, value) }
        return intentUri.build()
    }

    private fun getIntentParams() : HashMap<String, String> {
        val map = HashMap<String, String> ()
        map["file"] = {
            "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
                }.toString()

            map["title"] = "Here is your title"


            map["link"] = "https://google.com"



            //ar_only_rbt.isChecked ->  map["mode"] = "ar_only"
            //ar_preferred.isChecked -> map["mode"] = "ar_preferred"
        map["mode"] = "ar_preferred"


            map["resizable"] = "true"

        return map
    }
}