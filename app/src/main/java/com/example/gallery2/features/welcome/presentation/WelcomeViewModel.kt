package com.example.gallery2.features.welcome.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.APP_PREFERENCE_ID
import com.example.gallery2.utils.Constants.APP_PREFERENCE_LOGIN_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_REFRESH_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_SECRET
import com.example.gallery2.utils.Constants.APP_PREFERENCE_TOKEN_TIME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository,
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _welcomeLiveData: MutableLiveData<WelcomeState> =
        MutableLiveData(WelcomeState.Loading)
    val welcomeLiveData: LiveData<WelcomeState> = _welcomeLiveData
    private val tokenTime = sharedPreferenceRepository.getLongFromPreference(APP_PREFERENCE_TOKEN_TIME)

    fun checkAvailabilityTokens() {
        if (tokenTime != 0L && tokenTime + 3600000L > System.currentTimeMillis()) {
            _welcomeLiveData.value = WelcomeState.Success
        } else {
            _welcomeLiveData.value = WelcomeState.Loading
            authorizationRepository.refreshClient(
                id = sharedPreferenceRepository.getStringFromPreference(APP_PREFERENCE_ID),
                grantType = "refresh_token",
                refreshToken = sharedPreferenceRepository.getStringFromPreference(
                    APP_PREFERENCE_REFRESH_TOKEN
                ),
                clientSecret = sharedPreferenceRepository.getStringFromPreference(
                    APP_PREFERENCE_SECRET
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _welcomeLiveData.value = WelcomeState.Success
                    sharedPreferenceRepository.saveLongToPreference(
                        APP_PREFERENCE_TOKEN_TIME, tokenTime
                    )

                    sharedPreferenceRepository.saveStringToPreference(
                        APP_PREFERENCE_LOGIN_TOKEN, it.accessToken
                    )
                    sharedPreferenceRepository.saveStringToPreference(
                        APP_PREFERENCE_REFRESH_TOKEN, it.refreshToken
                    )
                },
                    {
                        _welcomeLiveData.value = WelcomeState.Error
                        it.printStackTrace()
                    })
                .let(compositeDisposable::add)
        }
    }
}
