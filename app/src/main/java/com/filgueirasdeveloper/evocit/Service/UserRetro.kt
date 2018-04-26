package com.filgueirasdeveloper.evocit.Service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRetro{
    fun sendNewPost(user: User, context: Context, callback: AsyncCallback){

        val call : UserService = RetrofitInitializer().retrofit.create(UserService::class.java)
        val service = call.getLogin(user)
        service.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>?, response: Response<User>) {
                response.body()?.let{
                    if (response.isSuccessful && response.code() == 200)
                    {
                        callback.onSuccess("ok")
                    }
                    else{
                        callback.onFailure("não")
                    }
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                callback.onFailure("ahvsdg")
                Log.e("erro", t?.message)
            }
        })

    }
}