package com.example.domain.models.registrationauthorization

data class RegistrationUserModel(
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String,
    val username: String
)