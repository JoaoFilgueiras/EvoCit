package com.filgueirasdeveloper.evocit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var latLng: LatLng
    private lateinit var googleMap: GoogleMap
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            googleMap.isMyLocationEnabled = true
        })

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

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = googleMap
        googleMap.setOnMapClickListener(this@MenuActivity)

        val jampa = LatLng(-7.161954, -34.858543)

        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val cameraPosition = CameraPosition.Builder().zoom(15f).target(jampa).build()

        // Add a marker in Sydney and move the camera

        googleMap.addMarker(MarkerOptions().position(jampa).title("Sua Localização"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(jampa))
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onMapClick(p0: LatLng?) {
        this.latLng = latLng
        progress = ProgressDialog(this)
        progress.setTitle("buscando endereço ...")
        progress.show()


    }

    fun createMarkers(latLng: LatLng, title: String, snippet: String): MarkerOptions{
        return MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
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
            R.id.nav_createUser -> {
                val intent = Intent(this@MenuActivity, CadastroActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_createEvent -> {
                val intent = Intent(this@MenuActivity, CadastroEventoActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_openMap -> {
                startActivity(Intent(this@MenuActivity, MapaActivity::class.java))
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
}
