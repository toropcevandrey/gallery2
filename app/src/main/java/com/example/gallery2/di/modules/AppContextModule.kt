package com.example.gallery2.di.modules

import android.content.Context
import com.example.gallery2.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = app.applicationContext
}