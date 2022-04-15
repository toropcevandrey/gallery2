package com.example.domain.repositories.settings

import com.example.domain.models.user.ApiUserData
import com.example.domain.models.user.UpdateResponse
import com.example.domain.models.user.User
import com.example.domain.models.user.UserPassword
import io.reactivex.rxjava3.core.Single

interface SettingsRepository {
    fun getUserInfo(): Single<User>

    fun saveUserInfo(userId: Int, apiUserData: ApiUserData): Single<User>

    fun deleteUser(userId: Int): Single<String>

    fun updatePassword(userId: Int, userPassword: UserPassword): Single<UpdateResponse>
}