package com.example.gallery2.api.models.photo

data class PhotoModel(
    val dateCreate: String,
    val description: String,
    val id: Int,
    val image: ImageX,
    val name: String,
    val new: Boolean,
    val popular: Boolean,
    val user: String?
)