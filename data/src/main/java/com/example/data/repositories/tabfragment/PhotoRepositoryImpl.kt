package com.example.data.repositories.tabfragment

import com.example.data.api.services.PhotoApiService
import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.repositories.tabfragment.PhotoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : PhotoRepository {

    override fun getPhotos(
        news: Boolean,
        popular: Boolean,
        page: Int
    ): Single<PhotoCollectionModel> =
        photoApiService.getPhotoCollection(
            new = news,
            popular = popular,
            page = page,
        )

    override fun getPhotosBySearch(
        news: Boolean,
        popular: Boolean,
        page: Int,
        name: String
    ): Single<PhotoCollectionModel> = photoApiService.getPhotoCollection(
        new = news,
        popular = popular,
        page = page,
        name = name
    )
}