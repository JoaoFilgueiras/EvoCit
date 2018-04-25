package com.filgueirasdeveloper.evocit.Model

import com.orm.SugarRecord

class Evento (): SugarRecord(){
    var  name:String = ""
    var hora_inicio:String = ""
    var local:String = ""
}