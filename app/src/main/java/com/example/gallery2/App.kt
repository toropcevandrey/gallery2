package com.example.gallery2

import android.app.Application
import com.example.gallery2.di.AppComponent
import com.example.gallery2.di.DaggerAppComponent
import com.example.gallery2.di.modules.AppContextModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .appContextModule(AppContextModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}
