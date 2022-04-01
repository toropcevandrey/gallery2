package com.example.gallery2.features.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.api.models.User
import com.example.gallery2.features.settings.domain.SettingsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val _settingsLiveData: MutableLiveData<User> = MutableLiveData()
    val settingsLiveData: LiveData<User> = _settingsLiveData
    private val compositeDisposable = CompositeDisposable()


    fun getUserInfo(){
        settingsRepository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _settingsLiveData.value = it
            },{

            })
            .let(compositeDisposable::add)
    }

    fun saveUserInfo(username: String, email: String, phone: String, password: String){
        settingsRepository.saveUserInfo(
            name = username,
            email = email,
            phone = phone,
            password = password
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}