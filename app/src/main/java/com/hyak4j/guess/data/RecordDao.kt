package com.hyak4j.guess.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordDao {
    // onConflict = OnConflictStrategy.REPLACE => 如果有相同資料時做Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record :Record)

    @Query("SELECT * FROM record")
    fun getAll() :List<Record>
}