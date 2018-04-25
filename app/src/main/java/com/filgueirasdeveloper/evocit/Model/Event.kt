package com.filgueirasdeveloper.evocit.Model

import com.google.gson.annotations.Expose
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "event")
class Event {
    @Expose
    @DatabaseField(generatedId = true)
    var id  : Int  = 0

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var titulo : String   = String()

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var  date : Date = Date()

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var endereco : String = String()

    constructor(titulo:String, date:Date, endereco:String)
    {
        this.titulo       = titulo
        this.date         = date
        this.endereco     = endereco
    }
}