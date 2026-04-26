package com.example.myprofileapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

class SettingsManager(settings: ObservableSettings) {
    private val flowSettings: FlowSettings = settings.toFlowSettings()
    val isDarkModeFlow: Flow<Boolean> = flowSettings.getBooleanFlow("dark_mode", false)
    suspend fun setDarkMode(isDark: Boolean) {
        flowSettings.putBoolean("dark_mode", isDark)
    }
}