package com.example.gallery2.features.profile.presentation

import com.example.gallery2.api.models.photo.Image

data class ProfileViewData(
    var userPhoto: String,
    var userName: String,
    var phoneNumber: String,
    val image: List<Image>
)
