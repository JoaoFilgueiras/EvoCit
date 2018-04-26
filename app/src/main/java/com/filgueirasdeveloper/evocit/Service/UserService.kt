package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.Login
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @FormUrlEncoded
    @POST("aulas/UserAuth/auth/")
    fun getLogin(@Field("login") login: String, @Field("senha") senha: String) : Call<Login>
}