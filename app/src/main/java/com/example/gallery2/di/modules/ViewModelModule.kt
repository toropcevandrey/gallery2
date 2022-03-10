package com.example.gallery2.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery2.base.ViewModelFactory
import com.example.gallery2.di.ViewModelKey
import com.example.gallery2.features.authorization.presentation.AuthorizationViewModel
import com.example.gallery2.features.registration.presentation.RegistrationViewModel
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
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}