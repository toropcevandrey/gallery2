package com.example.gallery2.features.tabfragment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.models.photo.PhotoCollectionModel
import com.example.domain.repositories.tabfragment.PhotoRepository
import com.example.gallery2.base.BaseViewModel
import com.example.gallery2.features.tabfragment.TabState
import com.example.gallery2.features.tabfragment.TabViewData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseTabViewModel : BaseViewModel() {

    private val _tabLiveData: MutableLiveData<TabState> = MutableLiveData()
    val tabLiveData: LiveData<TabState> = _tabLiveData
    private val photoList: MutableList<TabViewData> = mutableListOf()
    private val searchList: MutableList<TabViewData> = mutableListOf()
    private var isSearching: Boolean = false
    private var query: String = ""
    private var page: Int = 1

    abstract fun getPhotos(page: Int): Single<PhotoCollectionModel>
    abstract fun getPhotosBySearch(query: String, page: Int): Single<PhotoCollectionModel>

    fun refreshData() {
        photoList.clear()
        loadFirstPage()
    }

    fun loadNextPage() {
        getPhotosBySearch(query = query, page = page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _tabLiveData.value = TabState.LoadingBottom
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                page++
            }
            .subscribe({
                val list = if (query.isNotBlank()) {
                    searchList
                } else {
                    photoList
                }
                list.addAll(it.data.map { data ->
                    TabViewData(id = data.id, image = data.image.name)
                })
                _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
            }, {
                _tabLiveData.value = TabState.Error
            })
            .let(compositeDisposable::add)
    }

    fun onSearchEntered(query: String) {
        if (isSearching) return
        if (query.length > 2) {
            page = 1
            getPhotosBySearch(query = query, page = page)
                .doOnSubscribe {
                    _tabLiveData.postValue(TabState.LoadingCenter)
                    searchList.clear()


                    isSearching = true
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isSearching = false }
                .subscribe({
                    searchList.addAll(it.data.map { data ->
                        TabViewData(id = data.id, image = data.image.name)
                    })
                    _tabLiveData.value = TabState.Success(searchList.map { it.copy() })
                }, {
                    it.printStackTrace()
                    _tabLiveData.value = TabState.Error
                })
                .let(compositeDisposable::add)
        } else {
            _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
        }
    }

    protected fun loadFirstPage() {
        getPhotos(page = page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _tabLiveData.value = TabState.LoadingCenter }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { page++ }
            .subscribe({
                photoList.addAll(it.data.map { data ->
                    TabViewData(id = data.id, image = data.image.name)
                })
                _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
            }, {
                it.printStackTrace()
                _tabLiveData.value = TabState.Error
            })
            .let(compositeDisposable::add)
    }
}