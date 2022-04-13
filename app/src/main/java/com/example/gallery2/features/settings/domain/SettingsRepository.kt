package com.example.gallery2.features.settings.domain

import com.example.gallery2.api.models.user.ApiUserData
import com.example.gallery2.api.models.user.UpdateResponse
import com.example.gallery2.api.models.user.User
import com.example.gallery2.api.models.user.UserPassword
import io.reactivex.rxjava3.core.Single

interface SettingsRepository {
    fun getUserInfo(): Single<User>

    fun saveUserInfo(userId: Int, apiUserData: ApiUserData): Single<User>

    fun deleteUser(userId: Int): Single<String>

    fun updatePassword(userId: Int, userPassword: UserPassword): Single<UpdateResponse>
}