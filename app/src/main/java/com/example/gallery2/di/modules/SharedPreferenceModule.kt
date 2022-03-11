package com.example.gallery2.di.modules

import android.content.Context
import com.example.gallery2.features.sharedpreference.data.SharedPreferenceRepositoryImpl
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferenceRepository =
        SharedPreferenceRepositoryImpl(context)
}