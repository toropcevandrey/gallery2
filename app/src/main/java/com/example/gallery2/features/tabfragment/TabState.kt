package com.example.gallery2.features.tabfragment

sealed class TabState {
    class Success(val tab: List<TabViewData>) : TabState()
    object LoadingBottom : TabState()
    object LoadingCenter : TabState()
    object Error : TabState()
}