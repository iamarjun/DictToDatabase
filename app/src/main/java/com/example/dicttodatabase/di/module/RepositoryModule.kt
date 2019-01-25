package com.example.dicttodatabase.di.module

import android.content.Context
import com.example.dicttodatabase.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository() : Repository = Repository()
}