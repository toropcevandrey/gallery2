package com.example.data.repositories.addphoto

import com.example.data.api.services.PhotoApiService
import com.example.domain.models.photo.Image
import com.example.domain.models.uploadmedia.UploadMediaObjectModel
import com.example.domain.models.uploadmedia.UploadPhotoModel
import com.example.domain.repositories.addphoto.AddPhotoRepository
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import javax.inject.Inject


class AddPhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : AddPhotoRepository {

    override fun uploadMedia(file: MultipartBody.Part): Single<UploadMediaObjectModel> =
        photoApiService.uploadMediaObject(file = file)

    override fun uploadPhoto(name: String, description: String, imageId: Int): Single<Image> =
        photoApiService.uploadPhoto(
            UploadPhotoModel(
                name = name,
                description = description,
                image = "api/media_objects/$imageId",
                popular = false,
                new = true
            )
        )

}