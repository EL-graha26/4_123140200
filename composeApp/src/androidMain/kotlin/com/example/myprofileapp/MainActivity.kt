package com.example.myprofileapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myprofileapp.data.local.DatabaseDriverFactory
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Setup DataStore (Preferences) Android
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPrefs)

        // 2. Setup Driver Database Android
        val driverFactory = DatabaseDriverFactory(this)

        setContent {
            // 3. Masukkan ke App()
            App(
                databaseDriverFactory = driverFactory,
                settings = settings
            )
        }
    }
}