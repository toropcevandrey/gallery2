package com.example.gallery2.api.services

import com.example.gallery2.api.models.photo.PhotoCollectionModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {

    @GET("/api/photos")
    fun getPhotoCollection(
        @Query("new") new: Boolean,
        @Query("popular") popular: Boolean,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
    ): Observable<PhotoCollectionModel>
}