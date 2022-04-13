package com.example.gallery2.features.settings.data

import com.example.gallery2.api.models.user.ApiUserData
import com.example.gallery2.api.models.user.UpdateResponse
import com.example.gallery2.api.models.user.User
import com.example.gallery2.api.models.user.UserPassword
import com.example.gallery2.api.services.UserApiService
import com.example.gallery2.features.settings.domain.SettingsRepository
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