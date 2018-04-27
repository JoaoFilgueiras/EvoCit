package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOEvent
import com.filgueirasdeveloper.evocit.DAO.DAOUser
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import kotlinx.android.synthetic.main.activity_cadastro_evento.*

class CadastroEventoActivity : AppCompatActivity() {

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoEvent : DAOEvent = DAOEvent(dbHelper.connectionSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_evento)
    }

    fun saveEvent(v:View){
        var name: String = nameEventoInput.text.toString()
        var date: String = dateInput.text.toString()
        var observacao: String = obsInput.text.toString()
        var endereco: String = enderecoInput.text.toString()

        val event: Event = Event()
        event.titulo = name
        event.date = date
        event.endereco = endereco
        event.observacao = observacao



        var evento = daoEvent.create(event)
        if(evento == 1){
            Toast.makeText(this, "Salvo com successo", Toast.LENGTH_SHORT)
            finish()
        }
        else{
            Toast.makeText(this, "Erro ao Salvar", Toast.LENGTH_SHORT)
        }

    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
