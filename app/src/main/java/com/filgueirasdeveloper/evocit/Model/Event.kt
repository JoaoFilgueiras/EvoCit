package com.filgueirasdeveloper.evocit.Model

import com.google.android.gms.maps.model.LatLng
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
    var  date : String = String()

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var endereco : String = String()

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var lat : Double = 0.0

    @Expose(serialize = false)
    @DatabaseField(canBeNull = false)
    var lng : Double = 0.0

    @Expose
    @DatabaseField
    var observacao : String = String()

    constructor()

    constructor(titulo:String, date:String, endereco:String, lat: Double, long: Double, observacao:String)
    {
        this.titulo       = titulo
        this.date         = date
        this.endereco     = endereco
        this.observacao   = observacao
        this.lat          = lat
        this.lng          = long
    }
}