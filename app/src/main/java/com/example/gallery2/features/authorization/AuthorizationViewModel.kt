package com.example.gallery2.features.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.base.ValidateState
import com.example.domain.models.registrationauthorization.RegistrationClientModel
import com.example.domain.repositories.authorization.AuthorizationRepository
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository
import com.example.gallery2.base.BaseViewModel
import com.example.gallery2.utils.Constants
import com.example.gallery2.utils.Constants.APP_PREFERENCE_ID
import com.example.gallery2.utils.Constants.APP_PREFERENCE_SECRET
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : BaseViewModel() {

    private val _authorizationLiveData: MutableLiveData<AuthorizationState> =
        MutableLiveData()
    val authorizationLiveData: LiveData<AuthorizationState> = _authorizationLiveData
    private val _validateLiveData: MutableLiveData<ValidateState> = MutableLiveData()
    val validateLiveData: LiveData<ValidateState> = _validateLiveData

    fun checkNotNullFields(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            postDataToRepository(email, password)
        } else {
            // Todo лучше сделать SingleLiveEvent'om
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
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    companion object {
        private val ALLOWED_GRANT_TYPES = listOf("refresh_token", "password")
    }
}