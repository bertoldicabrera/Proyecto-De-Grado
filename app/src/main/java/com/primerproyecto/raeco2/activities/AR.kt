package com.primerproyecto.raeco2.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Button
import android.widget.Switch
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.primerproyecto.raeco2.*
import com.primerproyecto.raeco2.R
import java.net.HttpURLConnection
import java.net.URL

class AR : AppCompatActivity() {

    private var titulo: Boolean=false
    private var link: Boolean=false
    private var renderizado: Boolean=false
    private var sonido: Boolean=false
    private var arEcoturismo_btn : Button? = null;
    private var arPreHistoria_btn : Button? = null;
    private var atras_btn : Button? = null;
    private var esPrehistorico: Boolean=false
    private val fachada:Facade = Facade(this)
   private var configu = Configuracion(titulo, link, renderizado, sonido)

    private val REQUEST_LOCATION_PERMISSION : Int = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        val switchTitulo = findViewById<Switch>(R.id.switch1)
        val switchLink = findViewById<Switch>(R.id.switch2)
        val switchRenderizado = findViewById<Switch>(R.id.switch3)
        val switchSonido = findViewById<Switch>(R.id.switch4)
        switchTitulo.setOnCheckedChangeListener { buttonView, isChecked ->
            titulo = isChecked
            configu.seterTitulo(titulo)
        }
        switchLink.setOnCheckedChangeListener { buttonView, isChecked ->
            link = isChecked
            configu.seterLink(link)
         }
        switchRenderizado.setOnCheckedChangeListener { buttonView, isChecked ->
            renderizado = isChecked
            configu.seterRenderizado(renderizado)
        }
        switchSonido.setOnCheckedChangeListener { buttonView, isChecked ->
            sonido = isChecked
            configu.seterSonido(sonido)
         }



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
            smallestDisplacement = 100f


        }
        var voLoc  = solicitarUbicacionGPS(fusedLocationClient, locationRequest, REQUEST_LOCATION_PERMISSION)

        arEcoturismo_btn = findViewById(R.id.buttonEcoturismo)
        arEcoturismo_btn?.setOnClickListener {

            esPrehistorico = false;
            if(voLoc.obtenerLatitud()!=null){

               var voAni= fachada.buscarAnimal(voLoc,esPrehistorico)
                crearAnimal3dExplicito(configu, voAni)
            }


        }
        arPreHistoria_btn=  findViewById(R.id.buttonPreHistoria)
        arPreHistoria_btn?.setOnClickListener {
            esPrehistorico = true;
            if(voLoc.obtenerLatitud()!=null){
                var voAni= fachada.buscarAnimal(voLoc, esPrehistorico)  //va a buscar el animal y vuelve null

                crearAnimal3dExplicito(configu, voAni)
            }


        }


        atras_btn = findViewById(R.id.buttonAtras)
        atras_btn?.setOnClickListener {
            onBackPressed()
        }


        }

    private fun crearAnimal3dExplicito(config:Configuracion, voAni:VoAnimal)
    {
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        val intentUri = createIntentUriExplicito(config, voAni)
        sceneViewerIntent.setData(intentUri);
        sceneViewerIntent.setPackage("com.google.ar.core");
         startActivity(sceneViewerIntent);

    }

    private fun createIntentUriExplicito(config:Configuracion, voAnimalMostrar:VoAnimal) : Uri {
        val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()


        var params = cargarParametrosDelAnimal(config, voAnimalMostrar)
        for ((key, value) in params) {
            intentUri.appendQueryParameter(key, value)
        }
        return intentUri.build()
    }

    private fun cargarParametrosDelAnimal(config:Configuracion, voAnimalMostrar: VoAnimal) : HashMap<String, String> {
        var url1 = voAnimalMostrar.obtenerObjetoAnimal()
        var sitioOK = sitioUp(url1)
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
            map["file"] = url1.toString()
        } else {
            map["file"] = voAnimalMostrar.obtenerObjetoBackUpAnimal().toString()
        }
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

            }
        }

        gfgThread.start()

        return estaUp

    }

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
                        voLoc.setearLongitud(longitud)
                        voLoc.setearLatitud(latitud)
                    }
                }, handler.looper)

            }
           return voLoc

    }
    }


