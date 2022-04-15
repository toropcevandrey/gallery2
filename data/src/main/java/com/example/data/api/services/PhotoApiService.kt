package com.example.data.api.services

import com.example.domain.models.photo.Image
import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.models.photo.PhotoModel
import com.example.domain.models.uploadmedia.UploadMediaObjectModel
import com.example.domain.models.uploadmedia.UploadPhotoModel
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*


interface PhotoApiService {

    @GET("/api/photos/{id}")
    fun getPhoto(
        @Path("id") id: Int,
    ): Single<PhotoModel>

    @GET("/api/photos")
    fun getPhotoCollection(
        @Query("new") new: Boolean = true,
        @Query("popular") popular: Boolean = true,
        @Query("name") name: String = "",
        @Query("user.id") userId: Int? = null,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
    ): Single<PhotoCollectionModel>

    @GET("/api/photos")
    fun getProfilePhotoCollection(
        @Query("user.id") userId: Int? = null,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
    ): Single<PhotoCollectionModel>

    @Multipart
    @POST("/api/media_objects")
    fun uploadMediaObject(
        @Part file: MultipartBody.Part
    ): Single<UploadMediaObjectModel>

    @POST("/api/photos")
    fun uploadPhoto(@Body uploadPhotoModel: UploadPhotoModel): Single<Image>
}