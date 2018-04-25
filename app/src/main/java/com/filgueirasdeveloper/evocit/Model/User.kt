package com.filgueirasdeveloper.evocit.Model

import com.orm.SugarRecord
import com.orm.dsl.Table
import com.orm.dsl.Unique

@Table
class User (): SugarRecord(){
    @Unique
    var name:String = ""
    var email:String = ""
    var password:String = ""
}