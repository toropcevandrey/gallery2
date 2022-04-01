package com.example.gallery2.api.services

import com.example.gallery2.api.models.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserApiService {
    @GET("/api/users/current")
    fun getUserInfo(): Single<User>
}