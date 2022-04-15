package com.example.domain.repositories.profile

import com.example.domain.models.user.User
import com.example.domain.models.photo.PhotoCollectionModel
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {
    fun getUserPhoto(userId: Int, page: Int): Single<PhotoCollectionModel>
    fun getUserInfo(): Single<User>
}