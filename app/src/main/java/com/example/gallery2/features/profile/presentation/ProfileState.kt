package com.example.gallery2.features.profile.presentation

sealed class ProfileState {
    class Success(
        val name: String,
        val phone: String,
        val profile: List<ProfileViewData>
    ) : ProfileState()

    object Error : ProfileState()
    object Loading : ProfileState()
}