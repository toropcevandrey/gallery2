package com.example.gallery2.features.authorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.api.models.ValidateState
import com.example.gallery2.api.models.registrationauthorization.RegistrationClientModel
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants
import com.example.gallery2.utils.Constants.APP_PREFERENCE_ID
import com.example.gallery2.utils.Constants.APP_PREFERENCE_SECRET
import com.example.gallery2.utils.Constants.APP_PREFERENCE_TOKEN_TIME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val tokenTime = System.currentTimeMillis()
    private val _authorizationLiveData: MutableLiveData<AuthorizationState> =
        MutableLiveData(AuthorizationState.FirstInit)
    val authorizationLiveData: LiveData<AuthorizationState> = _authorizationLiveData
    private val _validateLiveData: MutableLiveData<ValidateState> = MutableLiveData()
    val validateLiveData: LiveData<ValidateState> = _validateLiveData

    fun checkNotNullFields(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            postDataToRepository(email, password)
        } else {
            _validateLiveData.value = ValidateState.IsEmpty
        }
        _validateLiveData.value = ValidateState.Clear
    }

    private fun postDataToRepository(email: String, password: String) {
        authorizationRepository.getClientToken(
            RegistrationClientModel(name = email, allowedGrantTypes = ALLOWED_GRANT_TYPES)
        )
            .flatMap {
                sharedPreferenceRepository.saveStringToPreference(
                    APP_PREFERENCE_ID,
                    "${it.id}_" + it.randomId
                )
                sharedPreferenceRepository.saveStringToPreference(APP_PREFERENCE_SECRET, it.secret)
                authorizationRepository.loginClient(
                    id = "${it.id}_" + it.randomId,
                    grantType = "password",
                    email = email,
                    password = password,
                    clientSecret = it.secret
                )
            }
            .doOnSubscribe {
                _authorizationLiveData.postValue(AuthorizationState.Loading)
            }
            .doOnError {
                _authorizationLiveData.postValue(AuthorizationState.Error)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _authorizationLiveData.value = AuthorizationState.Success
                sharedPreferenceRepository.saveLongToPreference(
                    APP_PREFERENCE_TOKEN_TIME,
                    tokenTime
                )
                sharedPreferenceRepository.saveStringToPreference(
                    Constants.APP_PREFERENCE_LOGIN_TOKEN,
                    it.accessToken
                )
                sharedPreferenceRepository.saveStringToPreference(
                    Constants.APP_PREFERENCE_REFRESH_TOKEN,
                    it.refreshToken
                )
            }, {
                _authorizationLiveData.value = AuthorizationState.Error
                _authorizationLiveData.value = AuthorizationState.FirstInit
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