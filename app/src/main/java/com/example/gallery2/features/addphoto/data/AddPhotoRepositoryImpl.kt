package com.example.gallery2.features.addphoto.data

import com.example.gallery2.api.models.photo.Image
import com.example.gallery2.api.models.uploadmedia.UploadMediaObjectModel
import com.example.gallery2.api.models.uploadmedia.UploadPhotoModel
import com.example.gallery2.api.services.PhotoApiService
import com.example.gallery2.features.addphoto.domain.AddPhotoRepository
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