package com.example.gallery2.features.feed.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _searchData: MutableLiveData<String> = MutableLiveData()
    val searchData: LiveData<String> = _searchData

    fun sendSearchData(searchData: String) {
        _searchData.value = searchData
    }
}