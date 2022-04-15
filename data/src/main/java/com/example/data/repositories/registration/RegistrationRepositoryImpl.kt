package com.example.data.repositories.registration

import com.example.data.api.services.RegistrationApiService
import com.example.domain.models.registrationauthorization.*
import com.example.domain.repositories.registration.RegistrationRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val registrationApiService: RegistrationApiService
) : RegistrationRepository {

    override fun registrationUser(
        registrationUserModel: RegistrationUserModel
    ): Single<CreateUserResponseModel> =
        registrationApiService.createUser(registrationUserModel)

    override fun getClientToken(
        registrationClientModel: RegistrationClientModel
    ): Single<CreateClientResponseModel> =
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
