package com.example.gallery2.features.tabfragment.popular_tab

import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.repositories.tabfragment.PhotoRepository
import com.example.gallery2.features.tabfragment.base.BaseTabViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PopularTabViewModel @Inject constructor(private val photoRepository: PhotoRepository): BaseTabViewModel() {

    override fun getPhotos(page: Int): Single<PhotoCollectionModel> = photoRepository.getPhotos(
        news = false, popular = true, page = page
    )

    override fun getPhotosBySearch(query: String, page: Int): Single<PhotoCollectionModel> = photoRepository.getPhotosBySearch(
        news = false, popular = true, page = page, name = query
    )

    init {
        loadFirstPage()
    }
}