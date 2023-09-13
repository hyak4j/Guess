package com.hyak4j.guess.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Record(
    @NonNull
    @ColumnInfo(name="nick")
    var nickname :String,

    @NonNull
    var counter :Int) {

    @PrimaryKey(autoGenerate = true)
    var id :Long = 0
}