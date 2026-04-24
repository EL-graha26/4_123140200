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
    // 1. Inisialisasi Database menggunakan Driver yang sudah kita buat
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.noteQueries
    // 2. READ: Ambil Semua Catatan (Bentuk Flow biar UI reaktif)
    fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    // Mapping dari SQLite Entity ke Data Class UI
                    Note(
                        id = entity.id.toInt(),
                        title = entity.title,
                        content = entity.content,
                        isFavorite = entity.isFavorite == 1L, // SQLite nyimpen Boolean sebagai 0 atau 1
                        timestamp = entity.timestamp
                    )
                }
            }
    }

    // 3. SEARCH: Fitur Pencarian Catatan
    fun searchNotes(query: String): Flow<List<Note>> {
        // Tambahkan karakter '%' di Kotlin, sesuai perbaikan Note.sq tadi
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

    // 4. CREATE: Tambah Catatan
    suspend fun insertNote(title: String, content: String, timestamp: Long) {
        // 0L artinya default isFavorite = false
        queries.insertNote(title, content, 0L, timestamp)
    }

    // 5. UPDATE: Edit Catatan
    suspend fun updateNote(id: Int, title: String, content: String, timestamp: Long) {
        queries.updateNote(title, content, timestamp, id.toLong())
    }

    // 6. UPDATE: Toggle Favorit
    suspend fun toggleFavorite(id: Int, currentStatus: Boolean) {
        val newStatus = if (currentStatus) 0L else 1L
        queries.updateFavorite(newStatus, id.toLong())
    }

    // 7. DELETE: Hapus Catatan
    suspend fun deleteNote(id: Int) {
        queries.deleteNote(id.toLong())
    }
}