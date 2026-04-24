package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NotesUiState
import com.example.myprofileapp.data.local.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // UI State Logic: Otomatis nampilin hasil pencarian atau semua data
    val uiState: StateFlow<NotesUiState> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) repository.getAllNotes() else repository.searchNotes(query)
        }
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty else NotesUiState.Success(notes)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesUiState.Loading)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title, content, Clock.System.now().toEpochMilliseconds())
        }
    }

    fun editNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            repository.updateNote(id, title, content, Clock.System.now().toEpochMilliseconds())
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch { repository.deleteNote(id) }
    }

    fun toggleFavorite(id: Int, currentStatus: Boolean) {
        viewModelScope.launch { repository.toggleFavorite(id, currentStatus) }
    }
}