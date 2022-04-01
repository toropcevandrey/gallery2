package com.example.gallery2.features.welcome.presentation

sealed class WelcomeState {
    object Success : WelcomeState()
    object Error : WelcomeState()
    object Loading : WelcomeState()
}