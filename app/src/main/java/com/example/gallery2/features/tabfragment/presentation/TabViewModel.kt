package com.example.gallery2.features.tabfragment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery2.features.tabfragment.domain.TabRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TabViewModel @Inject constructor(
    private val tabRepository: TabRepository
) : ViewModel() {

    private val _tabLiveData: MutableLiveData<TabState> = MutableLiveData()
    val tabLiveData: LiveData<TabState> = _tabLiveData
    private val compositeDisposable = CompositeDisposable()
    private val photoList: MutableList<TabViewData> = mutableListOf()
    private val searchList: MutableList<TabViewData> = mutableListOf()
    private var page: Int = 1
    private var tab: Int = 0
    private var news: Boolean = false
    private var popular: Boolean = false
    private var query: String = ""
    private var isSearch: Boolean = false

    fun postDataToRepository(tab: Int) {
        if (photoList.isNotEmpty()) return
        this.tab = tab
        when (tab) {
            0 -> news = true
            1 -> popular = true
        }
        tabRepository.getDataFromApi(news, popular, page, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                photoList.addAll(it.data.map { data ->
                    TabViewData(id = data.id, image = data.image.name)
                })

                _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
            }, {

            })
            .let(compositeDisposable::add)
    }

    fun loadNextPage() {
        page++
        _tabLiveData.value = TabState.Loading
        if (!isSearch) {
            tabRepository.getDataFromApi(news, popular, page, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    photoList.addAll(it.data.map { data ->
                        TabViewData(id = data.id, image = data.image.name)
                    })
                    _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
                }, {

                })
                .let(compositeDisposable::add)
        } else {
            tabRepository.getDataFromApi(news, popular, page, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    searchList.addAll(it.data.map { data ->
                        TabViewData(id = data.id, image = data.image.name)
                    })
                    _tabLiveData.value = TabState.Success(searchList.map { it.copy() })
                }, {

                })
                .let(compositeDisposable::add)
        }
    }

    fun onSearchEntered(query: String) {
        if (this.query == query) return
        page = 1
        searchList.clear()
        this.query = query
        if (query.isNotEmpty()) {
            isSearch = true
            tabRepository.getDataFromApi(news, popular, page, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    searchList.addAll(it.data.map { data ->
                        TabViewData(id = data.id, image = data.image.name)
                    })
                    _tabLiveData.value = TabState.Success(searchList.map { it.copy() })
                }, {

                })
                .let(compositeDisposable::add)
        } else {
            isSearch = false
            _tabLiveData.value = TabState.Success(photoList.map { it.copy() })
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}