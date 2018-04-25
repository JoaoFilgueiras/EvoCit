package com.filgueirasdeveloper.evocit.DAO

import com.filgueirasdeveloper.evocit.Model.User
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class DAOUser : BaseDaoImpl<User, Int>{
    constructor(connectionSource: ConnectionSource?) : super(User::class.java){
        setConnectionSource(connectionSource)
        initialize()
    }
}