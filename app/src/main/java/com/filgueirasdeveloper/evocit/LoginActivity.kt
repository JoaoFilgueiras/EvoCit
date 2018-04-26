package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOUser
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.User
import com.filgueirasdeveloper.evocit.Service.AsyncCallback
import com.filgueirasdeveloper.evocit.Service.RetrofitInitializer
import com.filgueirasdeveloper.evocit.Service.UserRetro
import com.filgueirasdeveloper.evocit.Service.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoUser  : DAOUser        =  DAOUser(dbHelper.connectionSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener({
            login()
        })
    }

    fun login(){
        var usuario = username.text.toString()
        var senha   = password.text.toString()
        val conectado = manter_conectado.isChecked()

        if(usuario.isNotEmpty() && senha.isNotEmpty()){

            var  conn = UserRetro()
            conn.sendNewGet(this, object : AsyncCallback(){
                override fun onSuccess(result: String) {
                  Toast.makeText(this@LoginActivity, result, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String) {
                    Toast.makeText(this@LoginActivity, result, Toast.LENGTH_SHORT).show()
                }
            })
        }



//        var value = daoUser.create(user)
//
//        if (value == 1) {
//            Toast.makeText(this, "sucesso", Toast.LENGTH_LONG).show()
//            finish()
//        } else {
//            Toast.makeText(this, "falha no registo", Toast.LENGTH_LONG).show()
//        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}
