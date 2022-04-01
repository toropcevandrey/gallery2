package com.example.gallery2.features.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.features.profile.domain.ProfileRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _profileLiveData: MutableLiveData<ProfileState> =
        MutableLiveData()
    val profileLiveData: LiveData<ProfileState> = _profileLiveData
    private val userPhotoList: MutableList<String> = mutableListOf()
    private val page: Int = 1
    private var userName: String = ""
    private var phone: String = ""


    init {
        loadProfile()
    }

    fun loadProfile() {
        profileRepository.getUserInfo()
            .flatMap {
                userName = it.username
                phone = it.phone
                profileRepository.getUserPhoto(page, it.id)
            }
            .map {
                userPhotoList.addAll(it.data.map { data ->
                    data.image.name
                }
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _profileLiveData.value = ProfileState.Success(
                            name = userName,
                            phone = phone,
                            profile = userPhotoList
                        )
                },
                {

                })
            .let(compositeDisposable::add)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}