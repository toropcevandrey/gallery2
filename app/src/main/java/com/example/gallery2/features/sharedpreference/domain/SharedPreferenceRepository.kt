package com.example.gallery2.features.sharedpreference.domain

interface SharedPreferenceRepository {

    fun saveStringToPreference(tag: String, data: String)

    fun getStringFromPreference(tag: String): String
}