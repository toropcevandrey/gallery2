package com.example.gallery2.features.settings.data

import com.example.gallery2.api.models.User
import com.example.gallery2.api.services.UserApiService
import com.example.gallery2.features.settings.domain.SettingsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): SettingsRepository {


    override fun getUserInfo(): Single<User> = userApiService.getUserInfo()
    override fun saveUserInfo(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Single<String> {
        TODO("Not yet implemented")
    }
}