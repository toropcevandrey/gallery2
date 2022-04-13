package com.example.gallery2.features.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.api.models.ValidateState
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.api.models.registrationauthorization.RegistrationUserModel
import com.example.gallery2.features.registration.domain.RegistrationRepository
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.APP_PREFERENCE_EMAIL
import com.example.gallery2.utils.Constants.APP_PREFERENCE_LOGIN_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_PASSWORD
import com.example.gallery2.utils.Constants.APP_PREFERENCE_REFRESH_TOKEN
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import isValidEmail
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _registrationLiveData: MutableLiveData<RegistrationState> =
        MutableLiveData(RegistrationState.FirstInit)
    val registrationLiveData: LiveData<RegistrationState> = _registrationLiveData
    private val _regValidateLiveData: MutableLiveData<ValidateState> = MutableLiveData()
    val regValidateLiveData: LiveData<ValidateState> = _regValidateLiveData


    fun validateData(
        name: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (email.isValidEmail()) {
                if (password == confirmPassword) {

                    postDataToRepository(name, phone, email, password)
                    _regValidateLiveData.value = ValidateState.Success

                } else {
                    _regValidateLiveData.value = ValidateState.ComparePassword
                }
            } else {
                _regValidateLiveData.value = ValidateState.WrongEmail
            }
        } else {
            _regValidateLiveData.value = ValidateState.IsEmpty
        }
        _regValidateLiveData.value = ValidateState.Clear
    }

    private fun postDataToRepository(
        name: String,
        phone: String,
        email: String,
        password: String
    ) {
        registrationRepository.registrationUser(
            RegistrationUserModel(
                username = name,
                email = email,
                password = password,
                phone = phone,
                fullName = name
            )
        )
            .flatMap {
                sharedPreferenceRepository.saveStringToPreference(APP_PREFERENCE_EMAIL, email)
                sharedPreferenceRepository.saveStringToPreference(APP_PREFERENCE_PASSWORD, password)
                registrationRepository.getClientToken(
                    RegistrationClientModel(
                        name = name,
                        allowedGrantTypes = ALLOWED_GRANT_TYPES
                    )
                )
            }
            .flatMap {
                registrationRepository.loginClient(
                    id = "${it.id}_" + it.randomId,
                    grantType = "password",
                    email = sharedPreferenceRepository.getStringFromPreference(APP_PREFERENCE_EMAIL),
                    password = sharedPreferenceRepository.getStringFromPreference(
                        APP_PREFERENCE_PASSWORD
                    ),
                    clientSecret = it.secret
                )
            }
            .doOnSubscribe {
                _registrationLiveData.postValue(RegistrationState.Loading)
            }
            .doOnError {
                _registrationLiveData.postValue(RegistrationState.Error)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _registrationLiveData.value = RegistrationState.Success
                sharedPreferenceRepository.saveStringToPreference(
                    APP_PREFERENCE_LOGIN_TOKEN,
                    it.accessToken
                )
                sharedPreferenceRepository.saveStringToPreference(
                    APP_PREFERENCE_REFRESH_TOKEN,
                    it.refreshToken
                )
            }, {
                _registrationLiveData.value = RegistrationState.Error
                _registrationLiveData.value = RegistrationState.FirstInit
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private val ALLOWED_GRANT_TYPES = listOf("refresh_token", "password")
    }
}