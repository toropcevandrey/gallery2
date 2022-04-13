package com.example.gallery2.features.registration.domain

data class RegistrationRepositoryModel(
    val id: String,
    val grantType: String,
    val email: String,
    val password: String,
    val clientSecret: String
)