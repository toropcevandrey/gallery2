package com.example.gallery2.features.addphoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.repositories.addphoto.AddPhotoRepository
import com.example.gallery2.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(
    private val addPhotoRepository: AddPhotoRepository
) : BaseViewModel() {

    private var _addPhotoLiveData: MutableLiveData<AddPhotoState> = MutableLiveData<AddPhotoState>()
    val addPhotoLiveData: LiveData<AddPhotoState> = _addPhotoLiveData

    fun sendMediaFile(name: String, newFile: String, description: String) {

        val file = File(newFile)
        val reqFile = file.asRequestBody("image/*".toMediaType())
        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)

        addPhotoRepository.uploadMedia(
            file = body
        )
            .doOnSubscribe { _addPhotoLiveData.postValue(AddPhotoState.Loading) }
            .flatMap {
                addPhotoRepository.uploadPhoto(
                    name = name,
                    description = description,
                    imageId = it.id
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _addPhotoLiveData.value = AddPhotoState.Success
                },
                {
                    _addPhotoLiveData.value = AddPhotoState.Error
                    it.printStackTrace()
                }
            )

    }
}