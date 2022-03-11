package com.example.gallery2.di

import com.example.gallery2.di.modules.NetworkModule
import com.example.gallery2.di.modules.RepositoryModule
import com.example.gallery2.di.modules.SharedPreferenceModule
import com.example.gallery2.di.modules.ViewModelModule
import com.example.gallery2.features.authorization.presentation.AuthorizationFragment
import com.example.gallery2.features.registration.presentation.RegistrationFragment
import com.example.gallery2.features.tabfragment.presentation.TabFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        SharedPreferenceModule::class]
)
interface AppComponent {

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: TabFragment)
}