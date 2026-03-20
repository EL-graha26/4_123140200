package com.example.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.myprofileapp.ui.ProfileScreen
import com.example.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun App() {
    val viewModel = remember { ProfileViewModel() }

    val uiState by viewModel.uiState.collectAsState()

    val colorScheme = if (uiState.isDarkMode) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(colorScheme = colorScheme) {
        ProfileScreen(
            uiState = uiState,
            onEditProfile = { newName, newBio ->
                viewModel.updateProfile(newName, newBio)
            },
            onToggleDarkMode = { isDark ->
                viewModel.toggleDarkMode(isDark)
            }
        )
    }
}