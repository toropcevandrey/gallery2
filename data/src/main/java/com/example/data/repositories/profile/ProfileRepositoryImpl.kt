package com.example.data.repositories.profile

import com.example.data.api.services.PhotoApiService
import com.example.data.api.services.UserApiService
import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.models.user.User
import com.example.domain.repositories.profile.ProfileRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val userApiService: UserApiService
) : ProfileRepository {

    override fun getUserPhoto(userId: Int, page: Int): Single<PhotoCollectionModel> =
        photoApiService.getProfilePhotoCollection(
            userId = userId,
            page = page
        )

    override fun getUserInfo(): Single<User> = userApiService.getCurrentUserInfo()
}