package com.example.domain.repositories.openphoto

import com.example.domain.models.photo.PhotoModel
import com.example.domain.models.user.User
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface OpenPhotoRepository {
    fun getPhoto(id: Int): Single<PhotoModel>
    fun getUserName(id: Int): Single<User>
}