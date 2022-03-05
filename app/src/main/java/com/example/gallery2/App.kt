package com.example.gallery2

import android.app.Application
import com.example.gallery2.di.AppComponent
import com.example.gallery2.di.DaggerAppComponent
import com.example.gallery2.di.modules.NetworkModule

class App {
    class App : Application() {
        override fun onCreate() {
            super.onCreate()
            component = DaggerAppComponent.builder().networkModule(NetworkModule(applicationContext)).build()
        }

        companion object {
            private lateinit var component: AppComponent

            fun getComponent(): AppComponent {
                return component
            }
        }
    }
}