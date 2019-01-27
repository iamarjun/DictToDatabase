package com.example.dicttodatabase.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface DictionaryDAO {
    @Insert(onConflict = REPLACE)
    fun insert(dictionary: Dictionary)

    @Query("SELECT * FROM dictionary")
    fun getAllMessages(): List<Dictionary>

    @Query("SELECT * FROM dictionary ORDER BY id DESC")
    fun getLastEntries(): List<Dictionary>

    @Query("SELECT * FROM dictionary ORDER BY id DESC LIMIT 1")
    fun getLastRecord(): Dictionary
}