package com.example.gallery2.features.profile.domain

import com.example.gallery2.api.models.user.User
import com.example.gallery2.api.models.photo.PhotoCollectionModel
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {
    fun getUserPhoto(userId: Int, page: Int): Single<PhotoCollectionModel>
    fun getUserInfo(): Single<User>
}