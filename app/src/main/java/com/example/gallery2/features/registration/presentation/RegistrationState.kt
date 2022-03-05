package com.example.gallery2.features.registration.presentation

sealed class RegistrationState {
    object Loading: RegistrationState()
    object Success: RegistrationState()
    object Error : RegistrationState()
    object FirstInit : RegistrationState()
}