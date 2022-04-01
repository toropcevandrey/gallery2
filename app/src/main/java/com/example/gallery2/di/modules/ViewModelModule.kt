package com.example.gallery2.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery2.base.ViewModelFactory
import com.example.gallery2.di.ViewModelKey
import com.example.gallery2.features.addphoto.presentation.AddPhotoViewModel
import com.example.gallery2.features.authorization.presentation.AuthorizationViewModel
import com.example.gallery2.features.profile.presentation.ProfileViewModel
import com.example.gallery2.features.registration.presentation.RegistrationViewModel
import com.example.gallery2.features.settings.presentation.SettingsViewModel
import com.example.gallery2.features.tabfragment.presentation.TabViewModel
import com.example.gallery2.features.welcome.presentation.WelcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    abstract fun bindAuthorizationViewModel(authorizationViewModel: AuthorizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(welcomeViewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TabViewModel::class)
    abstract fun bindTabViewModel(tabViewModel: TabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPhotoViewModel::class)
    abstract fun bindAddPhotoViewModel(addPhotoViewModel: AddPhotoViewModel): ViewModel


    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}