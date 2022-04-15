package com.example.domain.repositories.addphoto

import com.example.domain.models.photo.Image
import com.example.domain.models.uploadmedia.UploadMediaObjectModel
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody


interface AddPhotoRepository {

    fun uploadMedia(file: MultipartBody.Part): Single<UploadMediaObjectModel>
    fun uploadPhoto(name: String, description: String, imageId: Int): Single<Image>
}