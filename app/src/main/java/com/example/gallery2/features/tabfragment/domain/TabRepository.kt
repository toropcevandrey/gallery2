package com.example.gallery2.features.tabfragment.domain

import com.example.gallery2.api.models.photo.PhotoCollectionModel
import io.reactivex.rxjava3.core.Observable

interface TabRepository {

    fun getDataFromApi(news: Boolean, popular: Boolean): Observable<PhotoCollectionModel>
}