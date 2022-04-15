package com.example.gallery2.di.modules

import android.content.Context
import com.example.data.api.services.PhotoApiService
import com.example.data.api.services.RegistrationApiService
import com.example.data.api.services.UserApiService
import com.example.data.repositories.addphoto.AddPhotoRepositoryImpl
import com.example.domain.repositories.addphoto.AddPhotoRepository
import com.example.data.repositories.authorization.AuthorizationRepositoryImpl
import com.example.domain.repositories.authorization.AuthorizationRepository
import com.example.data.repositories.openphoto.OpenPhotoRepositoryImpl
import com.example.domain.repositories.openphoto.OpenPhotoRepository
import com.example.data.repositories.profile.ProfileRepositoryImpl
import com.example.domain.repositories.profile.ProfileRepository
import com.example.data.repositories.registration.RegistrationRepositoryImpl
import com.example.domain.repositories.registration.RegistrationRepository
import com.example.data.repositories.settings.SettingsRepositoryImpl
import com.example.domain.repositories.settings.SettingsRepository
import com.example.data.repositories.sharedpreference.SharedPreferenceRepositoryImpl
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository
import com.example.data.repositories.tabfragment.PhotoRepositoryImpl
import com.example.domain.repositories.tabfragment.PhotoRepository
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
    fun provideTabRepository(photoApiService: PhotoApiService): PhotoRepository =
        PhotoRepositoryImpl(photoApiService)

    @Provides
    @Singleton
    fun provideOpenPhoto(
        photoApiService: PhotoApiService,
        userApiService: UserApiService
    ): OpenPhotoRepository =
        OpenPhotoRepositoryImpl(photoApiService, userApiService)

    @Provides
    @Singleton
    fun provideAddPhotoRepository(photoApiService: PhotoApiService): AddPhotoRepository =
        AddPhotoRepositoryImpl(photoApiService)

    @Provides
    @Singleton
    fun provideProfileRepository(
        photoApiService: PhotoApiService,
        userApiService: UserApiService
    ): ProfileRepository =
        ProfileRepositoryImpl(photoApiService, userApiService)

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