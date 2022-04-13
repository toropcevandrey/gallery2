package com.example.gallery2.features.openphoto.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.api.models.user.User
import com.example.gallery2.features.openphoto.domain.OpenPhotoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class OpenPhotoViewModel @Inject constructor(
    private val openPhotoRepository: OpenPhotoRepository
) : ViewModel() {
    private val _openPhotoLiveData: MutableLiveData<OpenPhotoState> =
        MutableLiveData(OpenPhotoState.Loading)
    val openPhotoLiveData: LiveData<OpenPhotoState> = _openPhotoLiveData
    private val compositeDisposable = CompositeDisposable()

    private var openPhotoViewData = OpenPhotoViewData(
        name = "",
        image = "",
        description = ""
    )

    fun getPhoto(id: String) {
        _openPhotoLiveData.value = OpenPhotoState.Loading
        openPhotoRepository.getPhoto(id.toInt())
            .flatMap { response ->
                openPhotoViewData = OpenPhotoViewData(
                    name = response.name,
                    image = response.image.name,
                    description = response.description
                )

                if (response.user != null) {
                    val userIdResponse = response.user.toString()
                    val userId = userIdResponse.replace("[^0-9]".toRegex(), "").toInt()
                    openPhotoRepository.getUserName(userId)
                } else {
                    Single.just(Unit)
                }
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