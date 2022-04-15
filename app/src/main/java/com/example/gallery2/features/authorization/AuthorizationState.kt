package com.example.gallery2.features.authorization

sealed class AuthorizationState {
    object Loading : AuthorizationState()
    object Success : AuthorizationState()
    object Error : AuthorizationState()
}