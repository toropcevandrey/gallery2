package com.example.gallery2.api.services

import com.example.gallery2.api.models.registrationauthorization.GetTokensModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationUserModel
import com.example.gallery2.features.registration.domain.CreateClientResponseModel
import com.example.gallery2.features.registration.domain.CreateUserResponseModel
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
}