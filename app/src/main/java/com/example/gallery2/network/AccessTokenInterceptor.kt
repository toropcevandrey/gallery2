package com.example.gallery2.network

import com.example.gallery2.App
import com.example.domain.repositories.authorization.AuthorizationRepository
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.APP_PREFERENCE_ID
import com.example.gallery2.utils.Constants.APP_PREFERENCE_LOGIN_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_REFRESH_TOKEN
import com.example.gallery2.utils.Constants.APP_PREFERENCE_SECRET
import com.example.gallery2.utils.Constants.APP_PREFERENCE_TOKEN_TIME
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

        val tokenTime = System.currentTimeMillis()

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
                        it.printStackTrace()
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