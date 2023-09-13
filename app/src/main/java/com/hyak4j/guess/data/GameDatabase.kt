package com.hyak4j.guess.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Record::class], version = 1)
abstract class GameDatabase :RoomDatabase(){
    // 讓user呼叫資料庫的抽象類別
    abstract fun recordDao() :RecordDao

    // DB單例化
    companion object{
        // Database Singleton
        private var instance :GameDatabase? = null
        fun getInstance(context :Context) :GameDatabase?{
            if (instance == null){
                instance = Room.databaseBuilder(context,
                    GameDatabase::class.java,
                    "game.db").build()
            }
            return instance
        }
    }
}