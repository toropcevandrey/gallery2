package com.example.gallery2.features.settings

sealed class SettingsState {
    class Success(val settingsViewData: SettingsViewData) : SettingsState()
    object Error : SettingsState()
    object Loading : SettingsState()
}