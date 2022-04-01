package com.example.gallery2.di

import com.example.gallery2.api.interceptors.authenticator.AccessTokenInterceptor
import com.example.gallery2.di.modules.*
import com.example.gallery2.features.addphoto.presentation.AddPhotoFragment
import com.example.gallery2.features.authorization.presentation.AuthorizationFragment
import com.example.gallery2.features.profile.presentation.ProfileFragment
import com.example.gallery2.features.registration.presentation.RegistrationFragment
import com.example.gallery2.features.settings.presentation.SettingsFragment
import com.example.gallery2.features.tabfragment.presentation.TabFragment
import com.example.gallery2.features.welcome.presentation.WelcomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: TabFragment)
    fun inject(fragment: WelcomeFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: AddPhotoFragment)
    fun inject(target: AccessTokenInterceptor)
}