package com.hyak4j.guess.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Record::class], version = 1)
abstract class GameDatabase :RoomDatabase(){
    // 讓user呼叫資料庫的抽象類別
    abstract fun recordDao() :RecordDao
}