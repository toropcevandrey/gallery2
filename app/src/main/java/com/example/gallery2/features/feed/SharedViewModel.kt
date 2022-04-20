package com.example.gallery2.features.feed

import androidx.lifecycle.ViewModel
import com.example.gallery2.base.SingleLiveEvent

class SharedViewModel : ViewModel() {
    val searchEvent = SingleLiveEvent<String>()
    private val openPhotoData = SingleLiveEvent<String>()
    private val openSettings = SingleLiveEvent<Boolean>()

    fun observeOpenPhoto(): SingleLiveEvent<String> = openPhotoData

    fun observeOpenSettings(): SingleLiveEvent<Boolean> = openSettings

    fun openSettings() {
        openSettings.value = true
    }

    fun sendPhotoId(id: String) {
        openPhotoData.value = id
    }

    fun sendSearchData(searchData: String) {
        searchEvent.postValue(searchData)
    }
}