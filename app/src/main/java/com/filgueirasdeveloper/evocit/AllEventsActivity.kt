package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.filgueirasdeveloper.evocit.Adapter.Adapter
import com.filgueirasdeveloper.evocit.DAO.DAOEvent
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import kotlinx.android.synthetic.main.activity_all_events.*

class AllEventsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoEvent : DAOEvent = DAOEvent(dbHelper.connectionSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        listView = findViewById<ListView>(R.id.lista_eventos)

        var eventList: List<Event> = daoEvent.queryBuilder().orderBy("titulo", false).query()

        val adapter = Adapter(this, eventList)
        listView?.adapter = adapter
    }
}
