package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
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
        setListView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        searchEvent(menu)
        return true
    }

    fun searchEvent(menu: Menu?){
        var searchItem : MenuItem? = menu?.findItem(R.id.action_search)
        var searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                setListView(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                setListView(query)
                return false
            }

        })
    }

    fun setListView(){
        var eventList: List<Event> = daoEvent.queryBuilder().orderBy("titulo", false).query()

        val adapter = Adapter(this, eventList)
        listView?.adapter = adapter
    }

    fun setListView(query: String?){
        var eventList: List<Event> = daoEvent.queryBuilder()
                                             .orderBy("titulo", false)
                                             .where()
                                             .like("titulo", "%" + query + "%")
                                             .or()
                                             .like("endereco", "%" + query + "%")
                                             .or()
                                             .like("observacao", "%" + query + "%")
                                             .query()

        val adapter = Adapter(this, eventList)
        listView?.adapter = adapter
    }
}
