package com.example.gallery2.di.modules

import com.example.gallery2.api.services.RegistrationApiService
import com.example.gallery2.features.registration.data.RegistrationRepositoryImpl
import com.example.gallery2.features.registration.domain.RegistrationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RegistrationModule {

    @Provides
    @Singleton
    fun provideSignUpRepository(registrationApiService: RegistrationApiService): RegistrationRepository =
        RegistrationRepositoryImpl(registrationApiService)
}