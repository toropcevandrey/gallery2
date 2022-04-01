package com.example.gallery2.features.settings.domain

import com.example.gallery2.api.models.User
import io.reactivex.rxjava3.core.Single

interface SettingsRepository {
    fun getUserInfo(): Single<User>

    fun saveUserInfo(name: String, email: String, phone: String, password: String): Single<String>
}