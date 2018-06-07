package com.filgueirasdeveloper.evocit

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOEvent
import com.filgueirasdeveloper.evocit.DAO.DAOUser
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_cadastro_evento.*
import java.util.*

class CadastroEventoActivity : AppCompatActivity() {

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoEvent : DAOEvent = DAOEvent(dbHelper.connectionSource)

    var latLng : String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_evento)

        configureExtra()
        getAddress()

    }

    fun configureExtra(){
        latLng = intent.getStringExtra(MenuActivity.REQUEST_LATLNG_EXTRA).replace("lat/lng: (", " ").replace(")", " ")
        latitude = latLng.split(",")[0].toDouble()
        longitude = latLng.split(",")[1].toDouble()
    }

    fun saveEvent(v:View){

        if(nameEventoInput.text.isEmpty()){
            nameEventoInput.error = "Informe um nome ao evento!"
        }
        if(dateInput.text.isEmpty()) {
            dateInput.error = "Informe uma data!"
        }

        if(nameEventoInput.text.isNotEmpty() && dateInput.text.isNotEmpty()){
            fillForm()
        }
    }

    fun fillForm(){

        var name: String = nameEventoInput.text.toString()
        var date: String = dateInput.text.toString()
        var observacao: String = obsInput.text.toString()
        var endereco: String = enderecoInput.text.toString()

        val event: Event = Event()
        event.titulo = name
        event.date = date
        event.endereco = endereco
        event.observacao = observacao

        if(latLng != null && latLng.isNotEmpty()){
            event.lat = latitude
            event.lng = longitude
        }

        var evento = daoEvent.create(event)
        if(evento == 1){
            startActivity(Intent(this@CadastroEventoActivity, MenuActivity::class.java))
            Toast.makeText(this, "Salvo com successo", Toast.LENGTH_SHORT)
            finish()
        }
        else{
            Toast.makeText(this, "Erro ao Salvar", Toast.LENGTH_SHORT)
        }

    }

    fun getAddress(){
        if(latitude != null && longitude != null){
            val geocoder = Geocoder(this, Locale.getDefault())
            val address : List<Address> =  geocoder.getFromLocation(latitude, longitude, 1)

            enderecoInput.setText(address.get(0).getThoroughfare() + ", " + address.get(0).getFeatureName()+ " - " + address.get(0).getSubLocality())
            enderecoInput.isEnabled = false
        }
    }
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
