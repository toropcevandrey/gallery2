package com.example.gallery2.features.sharedpreference.data

import android.content.Context
import android.content.SharedPreferences
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.NAME_APP_PREFERENCE
import javax.inject.Inject

class SharedPreferenceRepositoryImpl @Inject constructor(context: Context) :
    SharedPreferenceRepository {

    private val preference: SharedPreferences =
        context.getSharedPreferences(NAME_APP_PREFERENCE, Context.MODE_PRIVATE)

    override fun saveStringToPreference(tag: String, data: String) {
        preference.edit().putString(tag, data).apply()
    }

    override fun getStringFromPreference(tag: String): String = preference.getString(tag, "") ?: ""
}