package com.filgueirasdeveloper.evocit

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOUser
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.User
import com.filgueirasdeveloper.evocit.Service.*
import com.filgueirasdeveloper.evocit.receiver.MapaReceiver
import com.filgueirasdeveloper.evocit.receiver.MyApplication
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() , MapaReceiver.ConnectionReceiverListener {

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoUser  : DAOUser        =  DAOUser(dbHelper.connectionSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check connection network
        baseContext.registerReceiver(MapaReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        MyApplication.instance.setConnectionListener(this)

        getSharedPreference()
    }

    fun login(v: View){
        var usuario = username.text.toString()
        var senha   = password.text.toString()
        val checked = manter_conectado.isChecked()

        if(usuario.isNotEmpty() && usuario !=null  && senha.isNotEmpty() && senha != null){
            try {

                var  conn = LoginService()

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
                        Toast.makeText(this@LoginActivity, "Não foi possivel se autenticar !!", Toast.LENGTH_SHORT).show()
                    }
                })

                var  conn2 = EventService()
                conn2.sendNewGet(this@LoginActivity,  object : AsyncCallback(){
                    override fun onFailure(result: String) {
                        Toast.makeText(this@LoginActivity, "Não foi possivel sincronizar!!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess(result: String) {
                        return
                    }
                })
            }
            catch (e: Exception){
                Toast.makeText(this@LoginActivity, "Ocorreu um erro !!", Toast.LENGTH_SHORT).show()
            }

        }
        else{
            checkedUser()
        }
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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(!isConnected){
            Toast.makeText(this@LoginActivity, "Sem conexão com a internet", Toast.LENGTH_LONG).show()
        }
    }

}
