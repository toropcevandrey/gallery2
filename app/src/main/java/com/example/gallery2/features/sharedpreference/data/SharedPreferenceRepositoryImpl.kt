package com.example.gallery2.features.sharedpreference.data

import android.content.Context
import android.content.SharedPreferences
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants.NAME_APP_PREFERENCE

class SharedPreferenceRepositoryImpl(context: Context) :
    SharedPreferenceRepository {

    private val preference: SharedPreferences =
        context.getSharedPreferences(NAME_APP_PREFERENCE, Context.MODE_PRIVATE)

    override fun saveStringToPreference(tag: String, data: String) {
        preference.edit().putString(tag, data).apply()
    }

    override fun saveIntToPreference(tag: String, data: Int) {
        preference.edit().putInt(tag, data).apply()
    }

    override fun getStringFromPreference(tag: String): String = preference.getString(tag, "") ?: ""

    override fun getIntFromPreference(tag: String): Int = preference.getInt(tag, 0)

    override fun clearPreference() {
        preference.edit().clear().apply()
    }
}