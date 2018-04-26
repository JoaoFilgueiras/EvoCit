package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.Login
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginCore {
    @FormUrlEncoded
    @POST("aulas/UserAuth/auth/")
    fun login(@Field("login") login: String, @Field("senha") senha: String) : Call<Login>
}