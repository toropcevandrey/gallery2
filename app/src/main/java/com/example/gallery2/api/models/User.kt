package com.example.gallery2.api.models

data class User(
    val birthday: Any,
    val email: String,
    val enabled: Boolean,
    val fullName: String,
    val id: Int,
    val phone: String,
    val roles: List<String>,
    val username: String
)