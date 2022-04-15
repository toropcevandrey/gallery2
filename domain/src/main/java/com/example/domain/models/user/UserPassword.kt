package com.example.domain.models.user

data class UserPassword(
    val newPassword: String,
    val oldPassword: String
)