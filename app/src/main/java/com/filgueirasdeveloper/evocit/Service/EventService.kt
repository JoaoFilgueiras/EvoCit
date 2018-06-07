package com.filgueirasdeveloper.evocit.Service

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOEvent
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventService {

    fun sendNewGet(context: Context, callback: AsyncCallback) {
        val call : EventCore = RetrofitInitializer("https://api.myjson.com/").retrofit.create(EventCore::class.java)
        val service = call.getAllEvents()
        service.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {
                response?.let {
                    response.body()?.let {
                        val result = response.body()
                        result?.forEach {

                            var dbHelper : DatabaseHelper = DatabaseHelper(context)
                            var daoEvent : DAOEvent = DAOEvent(dbHelper.connectionSource)

                            var event: Event = Event()
                            event.titulo = it.titulo
                            event.endereco = it.endereco
                            event.observacao = it.observacao
                            event.date = it.date
                            event.lng = it.lng
                            event.lat = it.lat

                            var evento = daoEvent.create(event)
                            if(evento == 1){
                                callback.onSuccess("ok")
                            }
                            else{
                                callback.onFailure("error")
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Event>>?, t: Throwable?) {
                callback.onFailure("Connection Erro!" + t?.message)
            }
        })

    }

    fun storeEvent(context: Context){
        sendNewGet(context, object  : AsyncCallback(){
            override fun onSuccess(result: String) {

            }

            override fun onFailure(result: String) {
                super.onFailure(result)
            }
        })
    }
}