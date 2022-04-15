package com.example.gallery2.features.welcome

import androidx.lifecycle.ViewModel
import com.example.gallery2.base.SingleLiveEvent
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository
import com.example.gallery2.base.BaseViewModel
import com.example.gallery2.utils.Constants.APP_PREFERENCE_REFRESH_TOKEN
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    sharedPreferenceRepository: SharedPreferenceRepository
) : BaseViewModel() {

    private val refreshToken = sharedPreferenceRepository.getStringFromPreference(APP_PREFERENCE_REFRESH_TOKEN)
    val welcomeEvents: SingleLiveEvent<WelcomeEvents> = SingleLiveEvent()

    fun checkAvailabilityTokens() {
        if (refreshToken.isEmpty()) {
            welcomeEvents.setValue(WelcomeEvents.OpenAuthScreen)
        } else {
            welcomeEvents.setValue(WelcomeEvents.OpenMainScreen)
        }
    }
}
