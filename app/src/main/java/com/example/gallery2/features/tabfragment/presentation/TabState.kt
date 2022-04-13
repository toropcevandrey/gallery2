package com.example.gallery2.features.tabfragment.presentation

sealed class TabState {
    class Success(val tab: List<TabViewData>) : TabState()
    object Loading : TabState()
    object Error : TabState()
}