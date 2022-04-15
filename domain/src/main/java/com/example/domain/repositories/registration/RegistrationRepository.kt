package com.example.domain.repositories.registration

import com.example.domain.models.registrationauthorization.*
import io.reactivex.rxjava3.core.Single

interface RegistrationRepository {

    fun registrationUser(registrationUserModel: RegistrationUserModel): Single<CreateUserResponseModel>

    fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel>

    fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel>
}