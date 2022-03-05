package com.example.gallery2.features.registration.domain

data class CreateClientResponseModel(
    val allowedGrantTypes: List<String>,
    val id: Int,
    val name: String,
    val randomId: String,
    val secret: String
)