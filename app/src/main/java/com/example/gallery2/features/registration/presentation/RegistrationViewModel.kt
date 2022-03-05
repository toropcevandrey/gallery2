package com.example.gallery2.features.registration.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.api.models.RegistrationClientModel
import com.example.gallery2.api.models.RegistrationUserModel
import com.example.gallery2.features.registration.domain.RegistrationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {

    private val ALLOWED_GRANT_TYPES = listOf("refresh_token", "password")
    val registrationLiveData: MutableLiveData<RegistrationState> =
        MutableLiveData(RegistrationState.FirstInit)
    private val compositeDisposable = CompositeDisposable()

    private var vmEmail: String = ""
    private var vmPassword: String = ""

    private var loginToken: String = ""
    private var refreshToken: String = ""


    fun postDataToRepository(name: String, phone: String, email: String, password: String) {
        registrationRepository.registration(
            RegistrationUserModel(
                username = name,
                email = email,
                password = password,
                phone = phone,
                fullName = name
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                registrationLiveData.value = RegistrationState.Loading
                getClientToken(it.username)
                vmPassword = password
                vmEmail = email
            }, {
                registrationLiveData.value = RegistrationState.Error
            })
            .let(compositeDisposable::add)
    }

    private fun getClientToken(name: String) {
        registrationRepository.getClientToken(
            RegistrationClientModel(
                name = name,
                allowedGrantTypes = ALLOWED_GRANT_TYPES
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getLoginToken(
                    id = "${it.id}_" + it.randomId,
                    grantType = "password",
                    email = vmEmail,
                    password = vmPassword,
                    clientSecret = it.secret
                )
                registrationLiveData.value = RegistrationState.Loading
            }, {
                registrationLiveData.value = RegistrationState.Error
            })
            .let(compositeDisposable::add)
    }

    private fun getLoginToken(
        id: String,
        grantType: String,
        email: String,
        password: String,
        clientSecret: String
    ) {
        registrationRepository.loginClient(id, grantType, email, password, clientSecret)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginToken = it.access_token
                refreshToken = it.refresh_token
                registrationLiveData.value = RegistrationState.Success

            }, {
                registrationLiveData.value = RegistrationState.Error
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}