package com.example.gallery2.api.interceptors.authenticator

import com.example.gallery2.App
import com.example.gallery2.features.authorization.domain.AuthorizationRepository
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.APP_PREFERENCE_ID
import com.example.gallery2.utils.Constants.APP_PREFERENCE_LOGIN_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_REFRESH_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_SECRET
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    var isInjected = false

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInjected) {
            App.component.inject(this)
        }

        val token =
            sharedPreferenceRepository.getStringFromPreference(APP_PREFERENCE_LOGIN_TOKEN)

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = chain.proceed(request)

        if (response.code == 401) {

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
                    sharedPreferenceRepository.saveStringToPreference(
                        APP_PREFERENCE_LOGIN_TOKEN, it.accessToken
                    )
                    sharedPreferenceRepository.saveStringToPreference(
                        APP_PREFERENCE_REFRESH_TOKEN, it.refreshToken
                    )
                },
                    {

                    })

            val newToken: String? =
                sharedPreferenceRepository.getStringFromPreference(APP_PREFERENCE_LOGIN_TOKEN)
            if (newToken != null) {
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }
}