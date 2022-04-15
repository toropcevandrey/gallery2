package com.example.data.api.services

import com.example.domain.models.user.ApiUserData
import com.example.domain.models.user.UpdateResponse
import com.example.domain.models.user.User
import com.example.domain.models.user.UserPassword
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserApiService {

    @GET("/api/users/current")
    fun getCurrentUserInfo(): Single<User>

    @GET("/api/users/{id}")
    fun getUserInfo(
        @Path("id") id: Int
    ): Single<User>

    @PUT("/api/users/{id}")
    fun saveUserInfo(
        @Path("id") id: Int,
        @Body apiUserData: ApiUserData
    ): Single<User>

    @DELETE("/api/user/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ): Single<String>

    @PUT("/api/users/update_password/{id}")
    fun updatePassword(
        @Path("id") id: Int,
        @Body userPassword: UserPassword
    ): Single<UpdateResponse>
}