package com.example.gallery2.features.settings.presentation

sealed class SettingsState {
    class Success(val settingsViewData: SettingsViewData) : SettingsState()
    object Error : SettingsState()
    object Loading : SettingsState()
}