package com.primerproyecto.raeco2.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.primerproyecto.raeco2.*
import com.primerproyecto.raeco2.R
import java.net.HttpURLConnection
import java.net.URL

class AR : AppCompatActivity() {

    private var titulo: Boolean? = null
    private var link: Boolean? = null
    private var renderizado: Boolean? = null
    private var sonido: Boolean? = null
    private var ar_btn : Button? = null;
    private var atras_btn : Button? = null;
    private val  TAGGPS = "UBICACION GPS-AR "
    private val fachada:Facade = Facade(this)

    private val REQUEST_LOCATION_PERMISSION : Int = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)






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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // intervalo de actualización en milisegundos
            fastestInterval = 5000 // el intervalo más rápido en milisegundos

        }
        var voLoc  = solicitarUbicacionGPS(fusedLocationClient, locationRequest, REQUEST_LOCATION_PERMISSION)

        ar_btn = findViewById(R.id.button2)
        ar_btn?.setOnClickListener {


            Log.d("AR 85 Creo voLoc", "${voLoc.obtenerLongitud()}")
            if(voLoc.obtenerLatitud()!=null){
               var voAni= fachada.buscarAnimal(voLoc) //va a buscar el animal y vuelve null
                Log.d("AR 89 Creo voLoc", "${voAni.obtenerObjetoAnimal()}")
               // crearAnimal3dImplisito(voAni.obtenerObjetoAnimal())
                crearAnimal3dExplicito(con, voAni)
            }else{
                println("El animal es null POR ALGUNA RARON NO CARGA EL POR DEFECTO")
                Toast.makeText(this, "Error 76 al cargar animal AR", Toast.LENGTH_LONG)
                    .show()
            }


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
        println("83 AR crearAnimal3dExplicito  ${voAni.obtenerNombreAnimal()}")
        println("84 AR crearAnimal3dExplicito ${voAni.obtenerObjetoAnimal()}")
        //string para el
        val intentUri = createIntentUriExplicito(config, voAni)
        sceneViewerIntent.setData(intentUri);
        sceneViewerIntent.setPackage("com.google.ar.core");
        startActivity(sceneViewerIntent);

    }
    fun crearAnimal3dImplisito(url :String?)
    {
        //https://developers.google.com/ar/develop/scene-viewer
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        var url2 = "https://arvr.google.com/scene-viewer/1.0?file=$url"
        Log.d("AR 126 Creo voLoc", "$url2")
       // sceneViewerIntent.data = Uri.parse(url2)
        sceneViewerIntent.data = Uri.parse(url2)
        sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
        startActivity(sceneViewerIntent)
    }
    private fun createIntentUriExplicito(config:Configuracion, voAnimalMostrar:VoAnimal) : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()

        //obtener estos datos del usuario
        //SEGUIR ACÁ, ARREGLAR LAS CONSULTAS A LA BASE DE DATOS,
        //DADO QUE CAMBIAMOS EL NOMBRE DE GETS Y SETS DE ANIMAL

        /*
        var params = cargarParametrosDelAnimal(config, voAnimalMostrar)
        params.forEach {
               printl("for each $key,$value")
                (key, value) -> intentUri.appendQueryParameter(key, value)
        }

         */
        var params = cargarParametrosDelAnimal(config, voAnimalMostrar)
        for ((key, value) in params) {
            intentUri.appendQueryParameter(key, value)
            println(" 149 La clave es $key y el valor es $value")
        }
        Log.d("Parametros para Realidad aumentada 131 AR intentUri.build()","${intentUri.toString()}")
        return intentUri.build()
    }

    private fun cargarParametrosDelAnimal(config:Configuracion, voAnimalMostrar: VoAnimal) : HashMap<String, String> {


        var url1 = voAnimalMostrar.obtenerObjetoAnimal()
        println("108 AR $url1")
        var sitioOK = sitioUp(url1)
        println("110 AR $sitioOK")

        val map = HashMap<String, String>()

        map["mode"] = "ar_preferred"

        if (config.esLinkActivado() == true) {
            map["link"] = voAnimalMostrar.obtenerLinkAnimal().toString()
        }

        if (config.esTituloActivado() == true) {
            map["title"] = voAnimalMostrar.obtenerNombreAnimal().toString()
        }

        if (config.esSonidoActivado()== true) {
            map["sound"] = voAnimalMostrar.obtenerSonido().toString()
        }

        if (config.esRenderizadoActivado()== true) {
            map["resizable"] = config.esRenderizadoActivado().toString()
        }

        map["disable_occlusion"] = true.toString()

        if (!sitioOK) {
            println("186 file AR ${url1.toString()}")
            map["file"] = url1.toString()
            println("187 file AR ${map["file"]}")
        } else {
            println("189 file AR ${voAnimalMostrar.obtenerObjetoBackUpAnimal().toString()}")
            map["file"] = voAnimalMostrar.obtenerObjetoBackUpAnimal().toString()
            println("190 file AR ${map["file"]}")
        }

        return map
    }

        /*
        var url1= voAnimalMostrar.obtenerObjetoAnimal()
        println("108 AR ${url1}")
        var sitioOK=sitioUp(url1)
        println("110 AR ${sitioOK}")
        //https://developers.google.com/ar/develop/scene-viewer
        val map = HashMap<String, String> ()
        map["file"] = {
            if(!sitioOK)
            {
                url1.toString()
            }else
            {
                voAnimalMostrar.obtenerObjetoBackUpAnimal().toString()
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
 */
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
    @SuppressLint("SuspiciousIndentation")
    private fun solicitarUbicacionGPS(fusedLocationClient : FusedLocationProviderClient, locationRequest :LocationRequest, REQUEST_LOCATION_PERMISSION:Int): VoLocalizacion {
        var voLoc: VoLocalizacion= VoLocalizacion(null,null )

        var handlerThread = HandlerThread("LocationThread")
        handlerThread.start()

        var handler = Handler(handlerThread.looper)
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location = locationResult.lastLocation
                        val latitud = location.latitude
                        val longitud = location.longitude
                        Log.d("solicitarUbicacionGPS 214", "$latitud , $longitud")
                        voLoc.setearLongitud(longitud)
                        voLoc.setearLatitud(latitud)
                        Log.d(" 206 solicitarUbicacionGPS Carga VoLocalizacion", "${voLoc.obtenerLatitud()}")
                    }
                }, handler.looper)

            }
           return voLoc

    }



    }


