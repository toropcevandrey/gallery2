package com.example.gallery2.api.models.registrationauthorization

data class RegistrationClientModel(
    val allowedGrantTypes: List<String>,
    val name: String
)