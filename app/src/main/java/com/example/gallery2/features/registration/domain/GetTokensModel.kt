package com.example.gallery2.features.registration.domain

data class GetTokensModel(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: Any,
    val token_type: String
)