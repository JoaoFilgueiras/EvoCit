package com.filgueirasdeveloper.evocit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapaActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    override fun onMapClick(p0: LatLng?)  {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this@MapaActivity)

        val jampa = LatLng(-7.161954, -34.858543)

        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val cameraPosition = CameraPosition.Builder().zoom(15f).target(jampa).build()

        // Add a marker in Sydney and move the camera

        mMap.addMarker(MarkerOptions().position(jampa).title("Sua Localização"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jampa))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}