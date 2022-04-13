package com.example.gallery2.api.services

import com.example.gallery2.api.models.user.ApiUserData
import com.example.gallery2.api.models.user.UpdateResponse
import com.example.gallery2.api.models.user.User
import com.example.gallery2.api.models.user.UserPassword
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