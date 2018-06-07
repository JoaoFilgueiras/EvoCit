package com.filgueirasdeveloper.evocit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.R
import kotlinx.android.synthetic.main.listview_all_events.view.*
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter

class Adapter(private val context: Context,
              private val dataSource: List<Event>): BaseAdapter() {

    private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.listview_all_events, parent, false)
        val titleEvent = rowView.findViewById(R.id.tituloEvento) as TextView
        val dataEvento = rowView.findViewById(R.id.dataEvento) as TextView
        val obsEvento = rowView.findViewById(R.id.obsEvento) as TextView

        val evento = getItem(position) as Event
        titleEvent.text = evento.titulo
        dataEvento.text = evento.date
        obsEvento.text = evento.observacao

        return rowView
    }

}