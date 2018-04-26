package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventCore {
    @GET("/18sqmv")
    fun getAllEvents() : Call<List<Event>>
}