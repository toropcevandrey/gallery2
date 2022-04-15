package com.example.domain.models.registrationauthorization

data class CreateClientResponseModel(
    val allowedGrantTypes: List<String>,
    val id: Int,
    val name: String,
    val randomId: String,
    val secret: String
)