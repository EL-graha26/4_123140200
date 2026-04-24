package com.example.myprofileapp.data

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Success(val notes: List<Note>) : NotesUiState()
}