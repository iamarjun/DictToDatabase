package com.example.dicttodatabase.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.dicttodatabase.App

@Database(entities = [Dictionary::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDAO(): DictionaryDAO

    companion object {
        private var INSTANCE: DictionaryDatabase? = null

        fun getInstance(): DictionaryDatabase? {
            if (INSTANCE == null) {
                synchronized(DictionaryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        App.appContext,
                        DictionaryDatabase::class.java, "dictionary.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}