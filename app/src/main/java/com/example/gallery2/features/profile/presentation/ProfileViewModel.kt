package com.example.gallery2.features.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.features.profile.domain.ProfileRepository
import com.example.gallery2.features.sharedpreference.domain.SharedPreferenceRepository
import com.example.gallery2.utils.Constants
import com.example.gallery2.utils.Constants.NO_DATA
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _profileLiveData: MutableLiveData<ProfileState> =
        MutableLiveData(ProfileState.Loading)
    val profileLiveData: LiveData<ProfileState> = _profileLiveData
    private val userPhotoList: MutableList<ProfileViewData> = mutableListOf()
    private var page: Int = 1
    private var userName: String = ""
    private var phone: String = ""
    private var userId: Int = 0

    init {
        loadProfile()
    }

    fun loadProfile() {
        userPhotoList.clear()
        _profileLiveData.value = ProfileState.Loading
        profileRepository.getUserInfo()
            .flatMap {
                sharedPreferenceRepository.saveIntToPreference(
                    Constants.APP_PREFERENCE_USER_ID,
                    it.id
                )
                userId = it.id
                userName = it.username ?: NO_DATA
                phone = it.phone
                profileRepository.getUserPhoto(it.id, page)
            }
            .map {
                userPhotoList.addAll(it.data.map { data ->
                    ProfileViewData(
                        id = data.id,
                        image = data.image.name
                    )
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
                        profile = userPhotoList.map { it.copy() }
                    )
                },
                {
                    it.printStackTrace()
                })
            .let(compositeDisposable::add)

    }

    fun loadNextPage() {
        page++
        profileRepository.getUserPhoto(userId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userPhotoList.addAll(it.data.map { data ->
                        ProfileViewData(
                            id = data.id,
                            image = data.image.name
                        )
                    })
                    _profileLiveData.value = ProfileState.Success(
                        name = userName,
                        phone = phone,
                        profile = userPhotoList.map { it.copy() }
                    )
                },
                {
                    it.printStackTrace()
                    _profileLiveData.value = ProfileState.Error
                }
            )
            .let(compositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}