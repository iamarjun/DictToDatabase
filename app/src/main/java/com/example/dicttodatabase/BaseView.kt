package com.example.dicttodatabase

interface BaseView<in T> {
    fun setPresenter(presenter: T)
}