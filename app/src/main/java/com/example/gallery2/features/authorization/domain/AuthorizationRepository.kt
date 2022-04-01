package com.example.gallery2.features.authorization.domain

import com.example.gallery2.api.models.registrationauthorization.GetTokensModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.features.registration.domain.CreateClientResponseModel
import io.reactivex.rxjava3.core.Single

interface AuthorizationRepository {

    fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel>

    fun refreshClient(
        id: String,
        grantType: String,
        refreshToken: String,
        clientSecret: String
    ): Single<GetTokensModel>

    fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel>
}