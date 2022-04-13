package com.example.gallery2.features.sharedpreference.domain

interface SharedPreferenceRepository {

    fun saveStringToPreference(tag: String, data: String)

    fun saveIntToPreference(tag: String, data: Int)

    fun saveLongToPreference(tag: String, data: Long)

    fun getLongFromPreference(tag: String): Long

    fun getStringFromPreference(tag: String): String

    fun getIntFromPreference(tag: String): Int

    fun clearPreference()
}