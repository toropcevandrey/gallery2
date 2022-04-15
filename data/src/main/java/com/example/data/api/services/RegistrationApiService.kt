package com.example.data.api.services

import com.example.domain.models.registrationauthorization.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistrationApiService {

    @POST("/api/users")
    fun createUser(@Body registrationUserModel: RegistrationUserModel): Single<CreateUserResponseModel>

    @POST("/api/clients")
    fun createClient(@Body registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel>

    @GET("/oauth/v2/token")
    fun loginClient(
        @Query("client_id") id: String,
        @Query("grant_type") grantType: String,
        @Query("username") email: String,
        @Query("password") password: String,
        @Query("client_secret") clientSecret: String
    ): Single<GetTokensModel>

    @GET("/oauth/v2/token")
    fun refreshClient(
        @Query("client_id") id: String,
        @Query("grant_type") grantType: String,
        @Query("refresh_token") refreshToken: String,
        @Query("client_secret") clientSecret: String
    ): Single<GetTokensModel>
}