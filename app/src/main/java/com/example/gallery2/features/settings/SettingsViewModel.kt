package com.example.gallery2.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.models.user.ApiUserData
import com.example.domain.models.user.UserPassword
import com.example.domain.repositories.settings.SettingsRepository
import com.example.domain.repositories.sharedpreference.SharedPreferenceRepository
import com.example.gallery2.base.BaseViewModel
import com.example.gallery2.base.ValidateState
import com.example.gallery2.utils.Constants.APP_PREFERENCE_USER_ID
import com.example.gallery2.utils.Constants.NO_DATA
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import isValidEmail
import retrofit2.HttpException
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : BaseViewModel() {

    private val _settingsLiveData: MutableLiveData<SettingsState> =
        MutableLiveData(SettingsState.Loading)
    val settingsLiveData: LiveData<SettingsState> = _settingsLiveData
    private val userId = sharedPreferenceRepository.getIntFromPreference(APP_PREFERENCE_USER_ID)
    private val _settingsValidateLiveData: MutableLiveData<ValidateState> = MutableLiveData()
    val settingsValidateLiveData: LiveData<ValidateState> = _settingsValidateLiveData

    fun getUserInfo() {
        _settingsLiveData.value = SettingsState.Loading
        settingsRepository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _settingsLiveData.value = SettingsState.Success(
                    SettingsViewData(
                        username = it.username ?: NO_DATA,
                        phone = it.phone,
                        email = it.email
                    )
                )
            }, {
                _settingsLiveData.value = SettingsState.Error
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    fun validateData(
        name: String,
        phone: String,
        email: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
            if (email.isValidEmail()) {
                saveUserInfo(name, phone, email)
                _settingsValidateLiveData.value = ValidateState.Success
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) return
                if (newPassword == confirmPassword) {
                    updatePassword(oldPassword, newPassword)
                } else {
                    _settingsValidateLiveData.value = ValidateState.ComparePassword
                }
            } else {
                _settingsValidateLiveData.value = ValidateState.WrongEmail
            }
        } else {
            _settingsValidateLiveData.value = ValidateState.IsEmpty
        }
        _settingsValidateLiveData.value = ValidateState.Clear
    }

    private fun saveUserInfo(name: String, phone: String, email: String) {
        settingsRepository.saveUserInfo(
            userId = userId,
            apiUserData = ApiUserData(
                username = name,
                email = email,
                phone = phone
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        settingsRepository.updatePassword(
            userId,
            UserPassword(oldPassword = oldPassword, newPassword = newPassword)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _settingsValidateLiveData.value = ValidateState.PasswordUpdated
            }, {
                if (it is HttpException)
                    if (it.code() == 400) {
                        _settingsValidateLiveData.value = ValidateState.WrongOldPassword
                    }
            })
    }

    fun unAuthorization() {
        sharedPreferenceRepository.clearPreference()
    }

    fun deleteUser() {
        settingsRepository.deleteUser(userId)
    }
}