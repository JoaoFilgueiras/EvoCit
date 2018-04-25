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
    private var id  : Int      = 0

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var name       : String   = String()

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false, unique = true)
    var email      : String   = String()

    @DatabaseField(canBeNull = false)
    var password   : String   = String()

    constructor()

    constructor(name:String, email:String, password:String)
    {
        this.name       = name
        this.email      = email
        this.password   = password
    }
}