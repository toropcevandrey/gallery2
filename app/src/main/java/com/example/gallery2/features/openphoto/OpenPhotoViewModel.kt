package com.example.gallery2.features.openphoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.models.user.User
import com.example.domain.repositories.openphoto.OpenPhotoRepository
import com.example.gallery2.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class OpenPhotoViewModel @Inject constructor(
    private val openPhotoRepository: OpenPhotoRepository
) : BaseViewModel() {
    private val _openPhotoLiveData: MutableLiveData<OpenPhotoState> =
        MutableLiveData(OpenPhotoState.Loading)
    val openPhotoLiveData: LiveData<OpenPhotoState> = _openPhotoLiveData

    private var openPhotoViewData = OpenPhotoViewData(
        name = "",
        image = "",
        description = ""
    )

    fun getPhoto(id: String) {
        openPhotoRepository.getPhoto(id.toInt())
            .doOnSubscribe { _openPhotoLiveData.postValue(OpenPhotoState.Loading) }
            .flatMap { response ->
                openPhotoViewData = OpenPhotoViewData(
                    name = response.name,
                    image = response.image.name,
                    description = response.description
                )

                val userId = response.user?.replace("[^0-9]".toRegex(), "")?.toInt()
                    ?: return@flatMap Single.just(Unit)
                openPhotoRepository.getUserName(userId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _openPhotoLiveData.value = OpenPhotoState.Success(
                    if (result is User) {
                        result.username
                    } else "", openPhotoViewData
                )
            }, {
                it.printStackTrace()
                _openPhotoLiveData.value = OpenPhotoState.Error
            })
            .let(compositeDisposable::add)
    }
}