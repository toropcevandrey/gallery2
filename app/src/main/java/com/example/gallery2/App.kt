package com.example.gallery2

import android.app.Application
import com.example.gallery2.di.AppComponent
import com.example.gallery2.di.DaggerAppComponent
import com.example.gallery2.di.modules.NetworkModule
import com.example.gallery2.di.modules.SharedPreferenceModule

class App {
    class App : Application() {
        override fun onCreate() {
            super.onCreate()
            component = DaggerAppComponent.builder().networkModule(NetworkModule(this))
                .sharedPreferenceModule(
                    SharedPreferenceModule(this)
                ).build()
        }

        companion object {
            private lateinit var component: AppComponent

            fun getComponent(): AppComponent {
                return component
            }
        }
    }
}