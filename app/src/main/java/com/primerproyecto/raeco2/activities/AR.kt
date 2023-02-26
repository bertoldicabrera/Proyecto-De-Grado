package com.primerproyecto.raeco2.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.primerproyecto.raeco2.*
import java.net.HttpURLConnection
import java.net.URL

class AR : AppCompatActivity() {


    private var titulo: Boolean? = null
    private var link: Boolean? = null
    private var renderizado: Boolean? = null
    private var sonido: Boolean? = null
    private var ar_btn : Button? = null;
    private var atras_btn : Button? = null;
    private val  TAGGPS = "UBICACION GPS"
    private val fachada:Facade = Facade()

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var voAni: VoAnimal= solicitarUbicacionGPS(fusedLocationClient,REQUEST_LOCATION_PERMISSION)



 // Obtener una referencia al objeto Switch desde la vista
        val SwitchTitulo = findViewById<Switch>(R.id.switch1)
        val SwitchLink = findViewById<Switch>(R.id.switch2)
        val SwitchRenderizado = findViewById<Switch>(R.id.switch3)
        val SwitchSonido = findViewById<Switch>(R.id.switch4)
        SwitchTitulo.setOnCheckedChangeListener { buttonView, isChecked ->
            titulo = isChecked
        }
        SwitchLink.setOnCheckedChangeListener { buttonView, isChecked ->
            link = isChecked
        }
        SwitchRenderizado.setOnCheckedChangeListener { buttonView, isChecked ->
            renderizado = isChecked
        }
        SwitchSonido.setOnCheckedChangeListener { buttonView, isChecked ->
            sonido = isChecked
        }
       // var Configuracion : Configuracion = Configuracion(titulo, link, renderizado, sonido)
         var con = Configuracion.apply(titulo, link, renderizado, sonido)

        ar_btn = findViewById(R.id.button2)
        ar_btn?.setOnClickListener {
            crearAnimal3dExplicito(con, voAni)
        }
        atras_btn = findViewById(R.id.button3)
        atras_btn?.setOnClickListener {
            onBackPressed()
        }


        }

    private fun crearAnimal3dExplicito(config:Configuracion, voAni:VoAnimal)
    {
        //https://developers.google.com/ar/develop/scene-viewer
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        //string para el
        val intentUri = createIntentUriExplicito(config, voAni)
        sceneViewerIntent.setData(intentUri);
        sceneViewerIntent.setPackage("com.google.ar.core");
        startActivity(sceneViewerIntent);

    }

    private fun createIntentUriExplicito(config:Configuracion, voAnimalMostrar:VoAnimal) : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()

        //obtener estos datos del usuario
        //SEGUIR ACÁ, ARREGLAR LAS CONSULTAS A LA BASE DE DATOS,
        //DADO QUE CAMBIAMOS EL NOMBRE DE GETS Y SETS DE ANIMAL

        val params = cargarParametrosDelAnimal(config, voAnimalMostrar)
        params.forEach {
                (key, value) -> intentUri.appendQueryParameter(key, value)
        }
        return intentUri.build()
    }

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
        if (config.esLinkActivado() == true){
            map["link"] = voAnimalMostrar.obtenerLinkAnimal().toString()
        }
        //si
        if (config.esTituloActivado()==true){
            map["title"] = voAnimalMostrar.obtenerNombreAnimal().toString()//si
        }
        if (config.esSonidoActivado()==true){
            /*
           Una URL a una pista de audio en bucle que se sincroniza con la primera animación
            incorporada en un archivo glTF. Se debe proporcionar junto con un glTF
            con una animación que coincida con la longitud. Si está presente, el sonido se repite
             una vez cargado el modelo.
        */
            map["sound"] = voAnimalMostrar.obtenerSonido().toString()//si ver de cargar en la bd
        }

        if (config.esRenderizadoActivado()==true){
            map["resizable"] = config.esRenderizadoActivado().toString()// si
        }
        /*
         Cuando se configura en true, los objetos ubicados en la escena
          siempre aparecen delante de objetos reales de la escena.
         */
        map["disable_occlusion"] = true.toString()
        return map
    }

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


    //devolver region
    //Devulve un animal en null si no anda bien
    private fun solicitarUbicacionGPS(fusedLocationClient : FusedLocationProviderClient,REQUEST_LOCATION_PERMISSION:Int): VoAnimal {
        var voAni: VoAnimal=  VoAnimal(null,null,null,null,null,null)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permiso concedido, puedes acceder a la ubicación
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        // Usa la ubicación del usuario
                        var latitude = location.latitude
                        var longitude = location.longitude
                        Log.d(TAGGPS, "Latitud: ${location.latitude}, Longitud: ${location.longitude}")
                     //Llugar con fachada   esCercano10KMDeUmPunto()

                        var voLoc: VoLocalizacion= VoLocalizacion(latitude,longitude )
                       voAni= fachada.buscarAnimal(voLoc)

                    }
                }
        } else {
            // El permiso no ha sido concedido, solicita permiso al usuario
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
        }

                            return voAni

    }



    }


