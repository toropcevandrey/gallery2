package com.example.gallery2.features.tabfragment.data

import com.example.gallery2.api.models.photo.PhotoCollectionModel
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.features.tabfragment.domain.TabRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TabRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : TabRepository {

    override fun getDataFromApi(
        news: Boolean,
        popular: Boolean,
        page: Int,
        name: String
    ): Single<PhotoCollectionModel> =
        photoApiService.getPhotoCollection(
            new = news,
            popular = popular,
            page = page,
            name = name
        )
}