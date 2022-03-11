package com.example.gallery2.features.tabfragment.data

import com.example.gallery2.api.models.photo.PhotoCollectionModel
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.features.tabfragment.domain.TabRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class TabRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : TabRepository {

    override fun getDataFromApi(news: Boolean, popular: Boolean): Observable<PhotoCollectionModel> =
        photoApiService.getPhotoCollection(news, popular)

}