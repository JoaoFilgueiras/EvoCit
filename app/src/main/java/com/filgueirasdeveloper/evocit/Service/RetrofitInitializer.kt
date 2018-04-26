package com.filgueirasdeveloper.evocit.Service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    val BASE_URL: String = "https://api.myjson.com/bins/rkfm7/"

    val client : OkHttpClient = OkHttpClient()

    val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
}