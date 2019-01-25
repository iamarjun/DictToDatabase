package com.example.dicttodatabase.utils

interface DBCallBack {
    fun onWordAdded()
    fun onWordNotAvailable(message: String)
}