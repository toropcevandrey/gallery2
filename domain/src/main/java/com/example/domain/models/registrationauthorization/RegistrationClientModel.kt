package com.example.domain.models.registrationauthorization

data class RegistrationClientModel(
    val allowedGrantTypes: List<String>,
    val name: String
)