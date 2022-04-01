package com.example.gallery2.api.models.photo

data class PhotoCollectionModel(
    val countOfPages: Int,
    val data: List<Data>,
    val itemsPerPage: Int,
    val totalItems: Int
)