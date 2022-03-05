package com.example.gallery2.features.registration.domain

import com.example.gallery2.api.models.RegistrationClientModel
import com.example.gallery2.api.models.RegistrationUserModel
import io.reactivex.rxjava3.core.Single

interface RegistrationRepository {

    fun registration(registrationUserModel: RegistrationUserModel): Single<CreateUserResponseModel>

    fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel>

    fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel>
}