package com.filgueirasdeveloper.evocit.Service

import android.content.Context
import com.filgueirasdeveloper.evocit.Model.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService {
    fun sendNewPost(login: String, senha: String, context: Context, callback: AsyncCallback){

        val call : LoginCore = RetrofitInitializer("https://ludy.tech/").retrofit.create(LoginCore::class.java)
        val service = call.login(login, senha)
        service.enqueue(object : Callback<Login> {
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