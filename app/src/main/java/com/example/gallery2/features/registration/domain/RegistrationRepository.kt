package com.example.gallery2.features.registration.domain

import com.example.gallery2.api.models.registrationauthorization.GetTokensModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationUserModel
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