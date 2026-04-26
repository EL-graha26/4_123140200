package com.example.myprofileapp.di

import android.content.Context
import com.example.myprofileapp.data.local.DatabaseDriverFactory
import com.example.myprofileapp.platform.BatteryInfo
import com.example.myprofileapp.platform.DeviceInfo
import com.example.myprofileapp.platform.NetworkMonitor
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<ObservableSettings> {
        val sharedPrefs = androidContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        SharedPreferencesSettings(sharedPrefs)
    }
    single { NetworkMonitor(androidContext()) }
    single { DeviceInfo() }
    single { BatteryInfo(androidContext()) }
}