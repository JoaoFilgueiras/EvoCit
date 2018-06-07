package com.filgueirasdeveloper.evocit

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOEvent
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.R.drawable.ic_directions_bike_black_24dp
import com.filgueirasdeveloper.evocit.receiver.MapaReceiver
import com.filgueirasdeveloper.evocit.receiver.MyApplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_menu.*

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, MapaReceiver.ConnectionReceiverListener {

    private lateinit var progress: ProgressDialog
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var latLng: LatLng
    private lateinit var lastLocation: Location

    private lateinit var latLong: String

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoEvent : DAOEvent = DAOEvent(dbHelper.connectionSource)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
        public const val REQUEST_LATLNG_EXTRA = "extra_lng"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        //check connection network
        baseContext.registerReceiver(MapaReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        MyApplication.instance.setConnectionListener(this)

        //set fragment map
        fragmentMap()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
//        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setOnMapClickListener(this@MenuActivity)

        setUpMap()
    }

    private fun fragmentMap(){
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MenuActivity.LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        googleMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

        var eventList: List<Event> = daoEvent.queryBuilder().orderBy("titulo", false).query()

        eventList.forEach{
            googleMap.addMarker(createMarkers(LatLng(it.lat, it.lng), it.titulo, it.endereco))
        }

    }
    override fun onMapClick(p0: LatLng?) {
        val builder = AlertDialog.Builder(this@MenuActivity)
        builder.setTitle("Adicionar um evento")
        builder.setMessage("Deseja realmente adicionar um evento ?")

        builder.setPositiveButton("Sim") { dialog, which ->
            latLong = p0.toString()
            val intent = Intent(this@MenuActivity, CadastroEventoActivity::class.java)
            intent.putExtra(REQUEST_LATLNG_EXTRA, latLong)
            startActivity(intent)
        }
        builder.setNeutralButton("Cancelar"){_,_ ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun createMarkers(latLng: LatLng, title: String, snippet: String): MarkerOptions{



        return MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(bitmapDescriptorFromVector(this@MenuActivity, R.drawable.ic_directions_bike_black_24dp))
    }

    fun bitmapDescriptorFromVector(context: Context, @DrawableRes int: Int): BitmapDescriptor? {

        var background: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_directions_bike_black_24dp)
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight())

        var vectorDrawable: Drawable = ContextCompat.getDrawable(context, int)
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);

        var bitmap: Bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
        var canvas: Canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_createEvent -> {
                val intent = Intent(this@MenuActivity, AllEventsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                val myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE)
                val editor = myPreference.edit().remove("_token").apply()
                startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(!isConnected){
            networkChange.setText("Sem conexão !! Para melhor experiência habilete a conexão")
        }
        else{
            networkChange.setText("")
        }
    }
}
