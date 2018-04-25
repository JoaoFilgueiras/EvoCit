package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.filgueirasdeveloper.evocit.DAO.DAOUser
import com.filgueirasdeveloper.evocit.DAO.DatabaseHelper
import com.filgueirasdeveloper.evocit.Model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var dbHelper : DatabaseHelper = DatabaseHelper(this)
    var daoUser  : DAOUser        =  DAOUser(dbHelper.connectionSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener({
            register()
        })
    }

    fun register(){
        val user = User()
        user.name = username.text.toString()
        user.password = password.text.toString()
        user.email = "teste@gmail.com"

        var value = daoUser.create(user)

        if (value == 1) {
            Toast.makeText(this, "sucesso", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "falha no registo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}
