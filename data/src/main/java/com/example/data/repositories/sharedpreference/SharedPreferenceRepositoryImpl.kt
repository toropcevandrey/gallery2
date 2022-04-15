package com.example.data.repositories.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository

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

    override fun saveLongToPreference(tag: String, data: Long) {
        preference.edit().putLong(tag, data).apply()
    }

    override fun getLongFromPreference(tag: String): Long = preference.getLong(tag, 0)

    override fun getStringFromPreference(tag: String): String = preference.getString(tag, "") ?: ""

    override fun getIntFromPreference(tag: String): Int = preference.getInt(tag, 0)

    override fun clearPreference() {
        preference.edit().clear().apply()
    }

    private companion object{
        const val NAME_APP_PREFERENCE = "settings"
    }
}