package com.filgueirasdeveloper.evocit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.filgueirasdeveloper.evocit.Model.User
import com.orm.SugarDb
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SugarDb(this).db.close()

        btn_login.setOnClickListener {
            var user:User = User()
            user.name = username.text.toString()
            user.password = password.text.toString()
            user.email = password.text.toString()
            user.save()
//            if(username.text.toString().isNotEmpty()){
//                user.name = username.text.toString()
//            }
//            if(password.text.toString().isNotEmpty()) {
//                user.password = password.text.toString()
//            }
//            user.email = "teste"
//            user.save()
//
            Toast.makeText(this, "Slavou : ", Toast.LENGTH_LONG).show()
        }
    }
}
