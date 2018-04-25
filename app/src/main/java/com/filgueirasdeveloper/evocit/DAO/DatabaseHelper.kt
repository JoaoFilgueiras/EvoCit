package com.filgueirasdeveloper.evocit.DAO

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.filgueirasdeveloper.evocit.Model.User
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DatabaseHelper : OrmLiteSqliteOpenHelper{
    companion object {
        private val DB_NAME     = "evo.db"
        private val DB_VERSION   = 1
    }

    constructor(context: Context) : super(context, DB_NAME, null, DB_VERSION)

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, User::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
    }

    override fun close() {
        super.close()
    }
}