package com.example.gallery2.features.addphoto.domain

import com.example.gallery2.api.models.photo.Image
import com.example.gallery2.api.models.uploadmedia.UploadMediaObjectModel
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody

interface AddPhotoRepository {
    fun uploadMedia(file: MultipartBody.Part): Single<UploadMediaObjectModel>

    fun uploadPhoto(name: String, description: String, imageId: Int): Single<Image>
}