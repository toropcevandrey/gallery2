package com.example.gallery2.features.openphoto.domain

import com.example.gallery2.api.models.photo.PhotoModel
import com.example.gallery2.api.models.user.User
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface OpenPhotoRepository {
    fun getPhoto(id: Int): Single<PhotoModel>
    fun getUserName(id: Int): Single<User>
}