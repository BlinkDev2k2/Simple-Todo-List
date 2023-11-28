package com.blink.todolistsimple.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

internal class TodoDatabase(context: Context,
                   name: String,
                   cursorFactory: CursorFactory?,
                   version: Int ) : SQLiteOpenHelper(context, name, cursorFactory, version) {

    internal fun getAllData() : Cursor {
        return readableDatabase.rawQuery("select * from Tasks", null)
    }

    internal fun editNameTask(id: Short, name: String) {
        writableDatabase.execSQL("update Tasks set Name_Task = ? where ID = ?", arrayOf(name, id))
    }

    internal fun delTaskByName(id: Short) {
        writableDatabase.execSQL("delete from Tasks where ID = ?", arrayOf(id))
    }

    internal fun editStatementTask(id: Short, state: Byte) {
        writableDatabase.execSQL("update Tasks set State = ? where ID = ?", arrayOf(state, id))
    }

    internal fun addNewTask(name: String) {
        writableDatabase.execSQL("insert into Tasks values(null, ?, 0)", arrayOf(name))
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table if not exists Tasks(ID integer primary key autoincrement, Name_Task varchar not null, State boolean default 0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}