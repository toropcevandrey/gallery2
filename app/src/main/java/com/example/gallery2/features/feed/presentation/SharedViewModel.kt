package com.example.gallery2.features.feed.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.base.SingleLiveEvent

class SharedViewModel : ViewModel() {
    private var _searchData: MutableLiveData<String> = MutableLiveData()
    val searchData: LiveData<String> = _searchData
    private val openPhotoData = SingleLiveEvent<String>()
    private val openSettings = SingleLiveEvent<Boolean>()

    fun observeOpenPhoto(): SingleLiveEvent<String> = openPhotoData

    fun observeOpenSettings(): SingleLiveEvent<Boolean> = openSettings

    fun openSettings(){
        openSettings.value = true
    }

    fun sendPhotoId(id: String) {
        openPhotoData.value = id
    }

    fun sendSearchData(searchData: String) {
        _searchData.value = searchData
    }
}