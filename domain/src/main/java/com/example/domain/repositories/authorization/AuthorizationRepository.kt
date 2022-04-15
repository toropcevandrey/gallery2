package com.example.domain.repositories.authorization

import com.example.domain.models.registrationauthorization.GetTokensModel
import com.example.domain.models.registrationauthorization.RegistrationClientModel
import com.example.domain.models.registrationauthorization.CreateClientResponseModel
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