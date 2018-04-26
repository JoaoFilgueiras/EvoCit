package com.filgueirasdeveloper.evocit.Model

import com.google.gson.annotations.Expose

class Login {

    @Expose
    var status: String = String()
    @Expose
    var token: String = String()

    constructor(status:String, token:String)
    {
        this.status       = status
        this.token         = token
    }
}