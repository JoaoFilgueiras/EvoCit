package com.filgueirasdeveloper.evocit.DAO

import com.filgueirasdeveloper.evocit.Model.Event
import com.filgueirasdeveloper.evocit.Model.User
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

class DAOEvent: BaseDaoImpl<Event, Int> {

    constructor(connectionSource: ConnectionSource?) : super(Event::class.java){
        setConnectionSource(connectionSource)
        initialize()
    }
}