package com.example.dicttodatabase.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "word") var word: String,
    @ColumnInfo(name = "speech") var speech: String,
    @ColumnInfo(name = "meaning") var meaning: String
)