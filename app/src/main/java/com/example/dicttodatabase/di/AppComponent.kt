package com.example.dicttodatabase.di

import com.example.dicttodatabase.ApiCaller
import com.example.dicttodatabase.Presenter
import com.example.dicttodatabase.di.module.NetworkModule
import com.example.dicttodatabase.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(RepositoryModule::class), (NetworkModule::class)])
interface AppComponent {
    fun inject(presenter: Presenter)
    fun inject(apiCaller: ApiCaller)
}