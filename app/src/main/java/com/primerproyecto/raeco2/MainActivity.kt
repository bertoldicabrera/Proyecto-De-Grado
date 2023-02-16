package com.primerproyecto.raeco2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
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
import com.primerproyecto.raeco2.activities.Bienvenida

import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var message: TextView? = null
    private var btn_Crear : Button? = null;
    private var simple_btn : Button? = null;
    private var bienvenida : Button? = null;
    private var facade: Facade =Facade()
    private var animal: Animal? = null
    private var voAnimal: VoAnimal? = null
    private var config: Configuracion? = null
    private var localizacion: Localization? =null


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.menssage)
        message?.text = "xxxxxxxxxxxxxxxx" //Carga este dato en la pantalla
        permisosIniciarLocalizacion()
        animal= Animal ("Pepito","desc","link","objbk","obj3d","region", "sonido")
        voAnimal =VoAnimal("Pepito","desc","link","objbk","obj3d","region", "sonido")
        config = Configuracion(true,false,true,true)
        localizacion = Localization ( "cuba", 1.0, 2.0 )

        message?.text =localizacion!!.obtenerNombreRegion()+" "+localizacion!!.obtenerLatitud()+" "+localizacion!!.obtenerLongitud()

        simple_btn = findViewById(R.id.button)
        simple_btn?.setOnClickListener {
            crearAnimal3dImplisito()
            //crearAnimal3DExplisito() //llamar a este cuando terminemos
        }

        btn_Crear = findViewById(R.id.btnCrear)

        btn_Crear?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                crearBdLocal()
            }
        })

        bienvenida = findViewById(R.id.bienvenida)
        bienvenida?.setOnClickListener {
            val intent = Intent(this, Bienvenida::class.java )
            startActivity(intent)
        }

    }
    //lamado por el boton
    private fun crearBdLocal(){
        var mensaje: String? = facade.crearBd(this@MainActivity)

        Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_LONG)
            .show()

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

    private fun crearAnimal3dImplisito()
    {
        //https://developers.google.com/ar/develop/scene-viewer
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        sceneViewerIntent.data = Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf")
        sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
        startActivity(sceneViewerIntent)
    }

    private fun crearAnimal3dExplisito()
    {
        //https://developers.google.com/ar/develop/scene-viewer
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        //string para el
        val intentUri = createIntentUriExplicito()
        sceneViewerIntent.setData(intentUri);
        sceneViewerIntent.setPackage("com.google.ar.core");
        startActivity(sceneViewerIntent);
    }

    private fun createIntentUriExplicito() : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()

//obtener estos datos del usuario
        //SEGUIR ACÁ, ARREGLAR LAS CONSULTAS A LA BASE DE DATOS,
        //DADO QUE CAMBIAMOS EL NOMBRE DE GETS Y SETS DE ANIMAL
        var config:Configuracion= TODO()
        var voAnimalMostrar:VoAnimal = TODO()

        val params = cargarParametrosDelAnimal(config, voAnimalMostrar)
        params.forEach {
                (key, value) -> intentUri.appendQueryParameter(key, value)
        }
        return intentUri.build()
    }

    //Pasar a configracion
    //configuracion, voAnimal

    private fun cargarParametrosDelAnimal(config:Configuracion, voAnimalMostrar: VoAnimal) : HashMap<String, String> {

        var url1= voAnimalMostrar.obtenerObjetoAnimal()
        var sitioOK=sitioUp(url1)

        //https://developers.google.com/ar/develop/scene-viewer
        val map = HashMap<String, String> ()
        map["file"] = {
            if(sitioOK)
            {
                url1
            }else
            {
                voAnimalMostrar.obtenerObjetoBackUpAnimal()
            }
        }.toString()

        map["mode"] = "ar_preferred"

        map["link"] = voAnimalMostrar.obtenerLinkAnimal().toString()
        //si
        map["title"] = voAnimalMostrar.obtenerNombreAnimal().toString()//si
        map["sound"] = voAnimalMostrar.obtenerSonido().toString()//si ver de cargar en la bd
        /*
        	Una URL a una pista de audio en bucle que se sincroniza con la primera animación
        	 incorporada en un archivo glTF. Se debe proporcionar junto con un glTF
        	 con una animación que coincida con la longitud. Si está presente, el sonido se repite
        	  una vez cargado el modelo.
         */
        map["resizable"] = config.esRenderizadoActivado().toString()// si
        map["disable_occlusion"] = true.toString()
        /*
        Cuando se configura en true, los objetos ubicados en la escena
         siempre aparecen delante de objetos reales de la escena.
         */

        return map
    }

    //hacer un get y devuelve true si el sitio retorna un response code200
    private fun sitioUp(urlValidar:String?): Boolean {
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


}