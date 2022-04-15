package com.example.data.repositories.openphoto

import com.example.data.api.services.PhotoApiService
import com.example.data.api.services.UserApiService
import com.example.domain.models.photo.PhotoModel
import com.example.domain.models.user.User
import com.example.domain.repositories.openphoto.OpenPhotoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OpenPhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val userApiService: UserApiService
) : OpenPhotoRepository {

    override fun getPhoto(id: Int): Single<PhotoModel> = photoApiService.getPhoto(id)
    override fun getUserName(id: Int): Single<User> = userApiService.getUserInfo(id)
}