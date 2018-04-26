package com.filgueirasdeveloper.evocit

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

        getSharedPreference()
    }

    fun login(v: View){
        var usuario = username.text.toString()
        var senha   = password.text.toString()
        val checked = manter_conectado.isChecked()

        if(usuario.isNotEmpty() && usuario !=null  && senha.isNotEmpty() && senha != null){
            try {

                var  conn = UserRetro()

                conn.sendNewPost(usuario, senha, this, object : AsyncCallback(){
                    override fun onSuccess(result: String) {
                        if(result.isNotEmpty()){
                            if(checked == true){
                                sharedPreference(result)
                            }
                            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
                            finish()
                        }
                        else{
                            username.error = "Usuário ou senha invalido!"
                            Toast.makeText(this@LoginActivity, "Usuário ou senha invalido!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(result: String) {
                        Toast.makeText(this@LoginActivity, result, Toast.LENGTH_SHORT).show()
                    }
                })
            }
            catch (e: Exception){

            }

        }
        else{
            checkedUser()
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

    private fun checkedUser(){
        var usuario = username.text.toString()
        var senha   = password.text.toString()

        if(usuario.isEmpty()){
            username.error = "Por Favor Informe o usuario"
        }

        if(senha.isEmpty()){
            password.error = "Por Favor Informe a senha!"
        }
    }

    private fun sharedPreference( token: String){
        val myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE)
        val editor = myPreference.edit()
        editor.putString("_token", token)
        editor.apply()
    }

    private fun getSharedPreference(){
        val myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE)
        val token        = myPreference.getString("_token", "")

        if(token.toString().isNotEmpty())
        {
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}
