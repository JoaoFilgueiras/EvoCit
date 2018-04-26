package com.filgueirasdeveloper.evocit.Service

import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("aulas/UserAuth/auth/")
    fun getLogin(@Body user: User) : Call<User>
}