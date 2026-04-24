package com.example.myprofileapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

class SettingsManager(settings: ObservableSettings) {
    // Mengubah ObservableSettings jadi FlowSettings biar reaktif (auto-update UI)
    private val flowSettings: FlowSettings = settings.toFlowSettings()

    // Membaca status Dark Mode (default-nya false / Light Mode)
    val isDarkModeFlow: Flow<Boolean> = flowSettings.getBooleanFlow("dark_mode", false)

    // Menyimpan status Dark Mode baru
    suspend fun setDarkMode(isDark: Boolean) {
        flowSettings.putBoolean("dark_mode", isDark)
    }
}