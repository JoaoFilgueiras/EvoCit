package com.filgueirasdeveloper.evocit.Model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "users")
class User{
    @Expose
    @DatabaseField(generatedId = true)
    var id  : Int  = 0

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var login       : String   = String()

    @DatabaseField(canBeNull = false)
    var senha   : String   = String()

    constructor()

    constructor(name:String, password:String)
    {
        this.login    = name
        this.senha    = password
    }

}