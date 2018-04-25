package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/")
    fun allUser() : Call<List<User>>

    @GET("/{idUser}")
    fun getUser(@Path("idUser") idUser : Int) : Call<List<User>>

}