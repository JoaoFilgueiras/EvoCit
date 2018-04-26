package com.filgueirasdeveloper.evocit.Service

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.Login
import com.filgueirasdeveloper.evocit.Model.User
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRetro{
    fun sendNewPost(login: String, senha: String, context: Context, callback: AsyncCallback){

        val call : UserService = RetrofitInitializer().retrofit.create(UserService::class.java)
        val service = call.getLogin(login, senha)
        service.enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                response?.let {
                    response.body()?.let {
                        if(response.isSuccessful && response.code() == 200)
                        {
                            val result = response.body()
                            var str = ""

                            if(result?.status == "ok")
                            {
                                str = "${result?.token}"
                            }


                            callback.onSuccess(str)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<Login>?, t: Throwable?) {
                callback.onFailure("Connection Erro!" + t?.message)
            }
        })

    }
}