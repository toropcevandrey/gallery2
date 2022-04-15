package com.example.gallery2.features.tabfragment.new_tab

import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.repositories.tabfragment.PhotoRepository
import com.example.gallery2.features.tabfragment.base.BaseTabViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NewTabViewModel @Inject constructor(private val photoRepository: PhotoRepository) : BaseTabViewModel() {

    override fun getPhotos(page: Int): Single<PhotoCollectionModel> = photoRepository.getPhotos(
        news = true, popular = false, page = page
    )

    override fun getPhotosBySearch(query: String, page: Int): Single<PhotoCollectionModel> = photoRepository.getPhotosBySearch(
        news = true, popular = false, page = page, name = query
    )

    init {
        loadFirstPage()
    }
}