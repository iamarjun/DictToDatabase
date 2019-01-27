package com.example.dicttodatabase

import android.app.Application
import android.content.Context
import com.example.dicttodatabase.di.AppComponent
import com.example.dicttodatabase.di.DaggerAppComponent
import com.example.dicttodatabase.di.module.NetworkModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appContext : Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }

}