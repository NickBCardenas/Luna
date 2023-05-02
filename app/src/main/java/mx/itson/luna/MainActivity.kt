package mx.itson.luna

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mx.itson.luna.entidades.Ubicacion
import mx.itson.luna.utilerias.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener  {


    var mapa : GoogleMap? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapaFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment //Se importa :P
        mapaFragment.getMapAsync ( this)



        // obtenerUbicacion()
    }



    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mapa = googleMap
            mapa!!.mapType = GoogleMap.MAP_TYPE_HYBRID


            //Verificamos tener el permiso de la ubicacion exacta otorgado
            val estaPermitido = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if(estaPermitido) {
                googleMap.isMyLocationEnabled = true
            //Si no, Solicitamos que lo otorgue
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 1)
            }

            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(Criteria(), true)!!)

            /*if (location != null ) {
                onLocationChanged(location)
            } Es la misma que la linea de abajo */

            location?.let { onLocationChanged(it)}


        } catch (ex: Exception) {
            Log.e("Ocurrio un error al cargar el mapa", ex.toString())
        }
    }

    fun obtenerUbicacion() {
        val llamada : Call<Ubicacion> = RetrofitUtil.getApi().getClima("27.9678037", "-110.9186940", true)

        llamada.enqueue(object: Callback<Ubicacion>{
            override fun onResponse(call: Call<Ubicacion>, response: Response<Ubicacion>) {
                val ubicacion: Ubicacion? = response.body()

                var a : Int = 1
            }

            override fun onFailure(call: Call<Ubicacion>, t: Throwable) {
                var a : Int = 1
            }
        })
    }

    override fun onLocationChanged(location: Location) {
        val latitud : Double = location.latitude
        val longitud : Double = location.longitude
        //Fusionamos Latitud y longitud en una sola variable
        val latLng = LatLng(latitud, longitud)

        //Limpiar markers que pudiesen existir en el mapa
        mapa?.clear()

        //Agrega un marker a la posicion de latitud y longitud
        mapa?.addMarker(MarkerOptions().position(latLng).draggable(true))

        //Mueve la vista del mapa a la posicion del marker
        mapa?.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        //Aplica zoom al mapa en la ubicacion
        mapa?.animateCamera(CameraUpdateFactory.zoomTo(10f))


        //Listener de Arrastrado
        mapa?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(marker: Marker) {
                val latLng = marker.position
                //Efectos de prueba
                    Toast.makeText(this@MainActivity, "La latitud es "
                            + latLng.latitude + " y la longitud es " + latLng.longitude, Toast.LENGTH_SHORT).show()
            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })
    }
}