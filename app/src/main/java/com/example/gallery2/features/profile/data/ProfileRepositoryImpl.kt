package com.example.gallery2.features.profile.data

import com.example.gallery2.api.models.User
import com.example.gallery2.api.models.photo.PhotoCollectionModel
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.api.services.UserApiService
import com.example.gallery2.features.profile.domain.ProfileRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val userApiService: UserApiService
) : ProfileRepository {


    override fun getUserPhoto(page: Int, userId: Int): Single<PhotoCollectionModel> =
        photoApiService.getProfilePhotoCollection(page = page, userId = userId)

    override fun getUserInfo(): Single<User> = userApiService.getUserInfo()
}