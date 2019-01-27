package com.example.dicttodatabase.di

import com.example.dicttodatabase.Presenter
import com.example.dicttodatabase.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {
    fun inject(presenter: Presenter)
}