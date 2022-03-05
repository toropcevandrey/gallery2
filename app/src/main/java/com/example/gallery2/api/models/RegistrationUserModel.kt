package com.example.gallery2.api.models

data class RegistrationUserModel(
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String,
    val username: String
)