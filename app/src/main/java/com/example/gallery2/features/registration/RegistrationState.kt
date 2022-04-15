package com.example.gallery2.features.registration

sealed class RegistrationState {
    object Loading : RegistrationState()
    object Success : RegistrationState()
    object Error : RegistrationState()
}