package com.example.gallery2.di.modules

import com.example.gallery2.api.services.RegistrationApiService
import com.example.gallery2.features.authorization.data.AuthorizationRepositoryImpl
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.registration.data.RegistrationRepositoryImpl
import com.example.gallery2.features.registration.domain.RegistrationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RegistrationModule {

    @Provides
    @Singleton
    fun provideRegistrationRepository(registrationApiService: RegistrationApiService): RegistrationRepository =
        RegistrationRepositoryImpl(registrationApiService)

    @Provides
    @Singleton
    fun provideAuthorizationRepository(registrationApiService: RegistrationApiService): AuthorizationRepository =
        AuthorizationRepositoryImpl(registrationApiService)
}