package com.example.gallery2.features.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.base.SingleLiveEvent

//sealed class HomeEvents {
//    class OpenPhotoEvent(val photoId: String): HomeEvents()
//    object OpenSettingsEvent: HomeEvents()
//}

class SharedViewModel : ViewModel() {
/*    private var _searchData: MutableLiveData<String> = MutableLiveData()
    val searchData: LiveData<String> = _searchData*/
    val searchEvent = SingleLiveEvent<String>()
    private val openPhotoData = SingleLiveEvent<String>()
    private val openSettings = SingleLiveEvent<Boolean>()

    fun observeOpenPhoto(): SingleLiveEvent<String> = openPhotoData

    fun observeOpenSettings(): SingleLiveEvent<Boolean> = openSettings

    fun openSettings(){
//        event.value = HomeEvents.OpenSettingsEvent
        openSettings.value = true
    }

    fun sendPhotoId(id: String) {
//        event.value = HomeEvents.OpenPhotoEvent(id)
        openPhotoData.value = id
    }

    fun sendSearchData(searchData: String) {
        searchEvent.postValue(searchData)
    }
}