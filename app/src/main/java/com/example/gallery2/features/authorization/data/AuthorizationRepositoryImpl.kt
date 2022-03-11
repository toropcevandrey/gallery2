package com.example.gallery2.features.authorization.data

import com.example.gallery2.api.models.registrationauthorization.GetTokensModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.api.services.RegistrationApiService
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.registration.domain.CreateClientResponseModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val registrationApiService: RegistrationApiService
) : AuthorizationRepository {

    override fun getClientToken(registrationClientModel: RegistrationClientModel): Single<CreateClientResponseModel> =
        registrationApiService.createClient(registrationClientModel)

    override fun loginClient(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ): Single<GetTokensModel> =
        registrationApiService.loginClient(id, grantType, email, password, clientSecret)

}