package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventCore {
    @GET("bins/6zzaq")
    fun getAllEvents() : Call<List<Event>>
}