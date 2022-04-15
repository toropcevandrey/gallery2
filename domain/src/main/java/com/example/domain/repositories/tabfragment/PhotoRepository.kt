package com.example.domain.repositories.tabfragment

import com.example.domain.models.photo.PhotoCollectionModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PhotoRepository {

    fun getPhotos(
        news: Boolean,
        popular: Boolean,
        page: Int
    ): Single<PhotoCollectionModel>

    fun getPhotosBySearch(
        news: Boolean,
        popular: Boolean,
        page: Int,
        name: String
    ): Single<PhotoCollectionModel>
}