package com.example.data.repositories.settings

import com.example.data.api.services.UserApiService
import com.example.domain.models.user.ApiUserData
import com.example.domain.models.user.UpdateResponse
import com.example.domain.models.user.User
import com.example.domain.models.user.UserPassword
import com.example.domain.repositories.settings.SettingsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : SettingsRepository {

    override fun getUserInfo(): Single<User> = userApiService.getCurrentUserInfo()

    override fun saveUserInfo(userId: Int, apiUserData: ApiUserData): Single<User> =
        userApiService.saveUserInfo(userId, apiUserData)

    override fun deleteUser(userId: Int) = userApiService.deleteUser(userId)

    override fun updatePassword(userId: Int, userPassword: UserPassword): Single<UpdateResponse> =
        userApiService.updatePassword(userId, userPassword)
}