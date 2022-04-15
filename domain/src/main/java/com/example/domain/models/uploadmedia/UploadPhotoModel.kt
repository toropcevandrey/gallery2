package com.example.domain.models.uploadmedia

data class UploadPhotoModel(
    val description: String,
    val image: String,
    val name: String,
    val new: Boolean,
    val popular: Boolean
)