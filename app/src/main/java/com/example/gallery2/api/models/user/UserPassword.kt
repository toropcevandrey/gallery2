package com.example.gallery2.api.models.user

data class UserPassword(
    val newPassword: String,
    val oldPassword: String
)