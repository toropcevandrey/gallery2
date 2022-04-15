package com.example.data.repositories.authorization

import com.example.data.api.services.RegistrationApiService
import com.example.domain.models.registrationauthorization.CreateClientResponseModel
import com.example.domain.models.registrationauthorization.GetTokensModel
import com.example.domain.models.registrationauthorization.RegistrationClientModel
import com.example.domain.repositories.authorization.AuthorizationRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val registrationApiService: RegistrationApiService
) : AuthorizationRepository {

    override fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel> =
        registrationApiService.createClient(registrationClientModel)

    override fun refreshClient(
        id: String,
        grantType: String,
        refreshToken: String,
        clientSecret: String
    ): Single<GetTokensModel> =
        registrationApiService.refreshClient(id, grantType, refreshToken, clientSecret)


    override fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel> =
        registrationApiService.loginClient(id, grantType, email, password, clientSecret)

}