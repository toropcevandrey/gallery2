package com.example.gallery2.features.openphoto.data

import com.example.gallery2.api.models.photo.PhotoModel
import com.example.gallery2.api.models.user.User
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.api.services.UserApiService
import com.example.gallery2.features.openphoto.domain.OpenPhotoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OpenPhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val userApiService: UserApiService
) : OpenPhotoRepository {

    override fun getPhoto(id: Int): Single<PhotoModel> = photoApiService.getPhoto(id)
    override fun getUserName(id: Int): Single<User> = userApiService.getUserInfo(id)
}