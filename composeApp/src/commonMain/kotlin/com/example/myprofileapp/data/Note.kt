package com.example.myprofileapp.data

import kotlinx.datetime.Clock

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)