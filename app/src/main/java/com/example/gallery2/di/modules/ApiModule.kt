package com.example.gallery2.di.modules

import com.example.data.api.services.PhotoApiService
import com.example.data.api.services.RegistrationApiService
import com.example.data.api.services.UserApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class ApiModule {

    @Singleton
    @Provides
    fun provideRegistration(retrofit: Retrofit): RegistrationApiService {
        return retrofit.create(RegistrationApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePhotos(retrofit: Retrofit): PhotoApiService {
        return retrofit.create(PhotoApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUser(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}