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

    private val _tabViewData: MutableLiveData<List<TabViewData>> = MutableLiveData()
    val tabViewData: LiveData<List<TabViewData>> = _tabViewData
    private var viewList: MutableList<TabViewData> = mutableListOf()
    private val compositeDisposable = CompositeDisposable()

    fun postDataToRepository(news: Boolean, popular: Boolean) {
        tabRepository.getDataFromApi(news, popular)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data.forEach {
                    viewList.add(TabViewData(id = it.id, image = it.image.name))
                }
                _tabViewData.value = viewList
            }, {

            })
            .let(compositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}