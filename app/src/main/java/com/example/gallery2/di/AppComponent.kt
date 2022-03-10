package com.example.gallery2.di

import com.example.gallery2.di.modules.NetworkModule
import com.example.gallery2.di.modules.RegistrationModule
import com.example.gallery2.di.modules.SharedPreferenceModule
import com.example.gallery2.di.modules.ViewModelModule
import com.example.gallery2.features.authorization.presentation.AuthorizationFragment
import com.example.gallery2.features.registration.presentation.RegistrationFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class,
        ViewModelModule::class,
        RegistrationModule::class,
        SharedPreferenceModule::class]
)
interface AppComponent {

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
}