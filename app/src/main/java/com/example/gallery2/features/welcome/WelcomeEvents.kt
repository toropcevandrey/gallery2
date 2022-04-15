package com.example.gallery2.features.welcome

sealed class WelcomeEvents {
    object OpenMainScreen: WelcomeEvents()
    object OpenAuthScreen : WelcomeEvents()
}