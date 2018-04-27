package com.filgueirasdeveloper.evocit.Service

import android.content.Context
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventService {

    fun sendNewGet(context: Context, callback: AsyncCallback) {
        val call : EventCore = RetrofitInitializer("https://api.myjson.com/bins/").retrofit.create(EventCore::class.java)
        val service = call.getAllEvents()
        service.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {
                response?.let {
                    response.body()?.let {
                        val result = response.body()
                        var str = "ok"
                        callback.onSuccess(str)
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