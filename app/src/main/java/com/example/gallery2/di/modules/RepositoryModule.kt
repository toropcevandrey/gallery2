package com.example.gallery2.di.modules

import android.content.Context
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.api.services.RegistrationApiService
import com.example.gallery2.api.services.UserApiService
import com.example.gallery2.features.addphoto.data.AddPhotoRepositoryImpl
import com.example.gallery2.features.addphoto.domain.AddPhotoRepository
import com.example.gallery2.features.authorization.data.AuthorizationRepositoryImpl
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.profile.data.ProfileRepositoryImpl
import com.example.gallery2.features.profile.domain.ProfileRepository
import com.example.gallery2.features.registration.data.RegistrationRepositoryImpl
import com.example.gallery2.features.registration.domain.RegistrationRepository
import com.example.gallery2.features.settings.data.SettingsRepositoryImpl
import com.example.gallery2.features.settings.domain.SettingsRepository
import com.example.gallery2.features.sharedpreference.data.SharedPreferenceRepositoryImpl
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.features.tabfragment.data.TabRepositoryImpl
import com.example.gallery2.features.tabfragment.domain.TabRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class, AppContextModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRegistrationRepository(registrationApiService: RegistrationApiService): RegistrationRepository =
        RegistrationRepositoryImpl(registrationApiService)

    @Provides
    @Singleton
    fun provideAuthorizationRepository(registrationApiService: RegistrationApiService): AuthorizationRepository =
        AuthorizationRepositoryImpl(registrationApiService)

    @Provides
    @Singleton
    fun provideTabRepository(photoApiService: PhotoApiService): TabRepository =
        TabRepositoryImpl(photoApiService)

    @Provides
    @Singleton
    fun provideAddPhotoRepository(photoApiService: PhotoApiService): AddPhotoRepository =
        AddPhotoRepositoryImpl(photoApiService)

    @Provides
    @Singleton
    fun provideProfileRepository(
        photoApiService: PhotoApiService,
        userApiService: UserApiService
    ): ProfileRepository = ProfileRepositoryImpl(photoApiService, userApiService)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        userApiService: UserApiService
    ): SettingsRepository = SettingsRepositoryImpl(userApiService)

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(context: Context): SharedPreferenceRepository =
        SharedPreferenceRepositoryImpl(context)
}