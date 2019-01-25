package com.example.dicttodatabase.utils


interface CallBack<in T> {
    fun onSuccess(t: T)
    fun onFailure(message: String)
}