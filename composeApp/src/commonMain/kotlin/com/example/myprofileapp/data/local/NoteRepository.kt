package com.example.myprofileapp.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.noteQueries
    fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    Note(
                        id = entity.id.toInt(),
                        title = entity.title,
                        content = entity.content,
                        isFavorite = entity.isFavorite == 1L,
                        timestamp = entity.timestamp
                    )
                }
            }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        val formattedQuery = "%$query%"
        return queries.searchNotes(title = formattedQuery, content = formattedQuery)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    Note(
                        id = entity.id.toInt(),
                        title = entity.title,
                        content = entity.content,
                        isFavorite = entity.isFavorite == 1L,
                        timestamp = entity.timestamp
                    )
                }
            }
    }

    suspend fun insertNote(title: String, content: String, timestamp: Long) {
        queries.insertNote(title, content, 0L, timestamp)
    }

    suspend fun updateNote(id: Int, title: String, content: String, timestamp: Long) {
        queries.updateNote(title, content, timestamp, id.toLong())
    }

    suspend fun toggleFavorite(id: Int, currentStatus: Boolean) {
        val newStatus = if (currentStatus) 0L else 1L
        queries.updateFavorite(newStatus, id.toLong())
    }

    suspend fun deleteNote(id: Int) {
        queries.deleteNote(id.toLong())
    }
}