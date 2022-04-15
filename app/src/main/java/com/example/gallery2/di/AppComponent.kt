package com.example.gallery2.di

import com.example.gallery2.network.AccessTokenInterceptor
import com.example.gallery2.di.modules.RepositoryModule
import com.example.gallery2.di.modules.ViewModelModule
import com.example.gallery2.features.addphoto.AddPhotoFragment
import com.example.gallery2.features.authorization.AuthorizationFragment
import com.example.gallery2.features.openphoto.OpenPhotoFragment
import com.example.gallery2.features.profile.ProfileFragment
import com.example.gallery2.features.registration.RegistrationFragment
import com.example.gallery2.features.settings.SettingsFragment
import com.example.gallery2.features.tabfragment.new_tab.NewTabFragment
import com.example.gallery2.features.tabfragment.popular_tab.PopularTabFragment
import com.example.gallery2.features.welcome.WelcomeFragment
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
    fun inject(fragment: OpenPhotoFragment)
    fun inject(fragment: NewTabFragment)
    fun inject(fragment: PopularTabFragment)
    fun inject(fragment: WelcomeFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: AddPhotoFragment)
    fun inject(target: AccessTokenInterceptor)
}