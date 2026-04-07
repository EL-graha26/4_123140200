package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofileapp.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var nextId = 1

    fun addNote(title: String, content: String) {
        if (title.isNotBlank() || content.isNotBlank()) {
            _notes.update { currentList ->
                currentList + Note(
                    id = nextId++,
                    title = title,
                    content = content,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )
            }
        }
    }

    fun editNote(id: Int, newTitle: String, newContent: String) {
        _notes.update { currentList ->
            currentList.map { note ->
                if (note.id == id) {
                    note.copy(
                        title = newTitle,
                        content = newContent,
                        timestamp = Clock.System.now().toEpochMilliseconds()
                    )
                } else note
            }
        }
    }

    fun toggleFavorite(id: Int) {
        _notes.update { currentList ->
            currentList.map { note ->
                if (note.id == id) note.copy(isFavorite = !note.isFavorite) else note
            }
        }
    }
}