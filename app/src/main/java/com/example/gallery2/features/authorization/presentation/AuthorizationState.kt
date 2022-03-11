package com.example.gallery2.features.authorization.presentation

sealed class AuthorizationState {
    object Loading : AuthorizationState()
    object Success : AuthorizationState()
    object Error : AuthorizationState()
    object FirstInit : AuthorizationState()
}