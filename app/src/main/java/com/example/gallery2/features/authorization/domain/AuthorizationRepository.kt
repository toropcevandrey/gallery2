package com.example.gallery2.features.authorization.domain

import com.example.gallery2.api.models.RegistrationClientModel
import com.example.gallery2.features.registration.domain.CreateClientResponseModel
import com.example.gallery2.api.models.GetTokensModel
import io.reactivex.rxjava3.core.Single

interface AuthorizationRepository {

    fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel>

    fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel>
}