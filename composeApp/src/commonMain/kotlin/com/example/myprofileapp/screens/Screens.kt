package com.example.myprofileapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.Note
import kotlinx.datetime.*

val RedPrimary = Color(0xFFB71C1C)
val RedDark = Color(0xFF8E1D1D)


fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
    return "${localDateTime.dayOfMonth} ${monthNames[localDateTime.monthNumber - 1]} ${localDateTime.year}, ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppSecondaryTopBar(title: String, onBack: () -> Unit, actions: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = RedDark,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


@Composable
fun NoteCard(note: Note, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onNoteClick(note.id) }

            .border(
                1.dp,
                if (!isDarkMode) Color.LightGray.copy(alpha=0.5f) else Color.Transparent,
                CardDefaults.shape
            ),
        colors = CardDefaults.cardColors(

            containerColor = if (isDarkMode) Color(0xFF333333) else Color.White
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = if (!isDarkMode) 6.dp else 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top // Teks dan hati sejajar di atas
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(note.title.ifEmpty { "Tanpa Judul" }, fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black, fontSize = 20.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(note.content, maxLines = 1, color = Color.Gray, fontSize = 14.sp)
                }

                IconButton(onClick = { onToggleFavorite(note.id) }) {
                    Icon(
                        imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (note.isFavorite) RedPrimary else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                formatTimestamp(note.timestamp),
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun NoteListScreen(notes: List<Note>, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int) -> Unit) {
    if (notes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Belum ada catatan. Tekan + untuk menambah.", color = if (isDarkMode) Color.White else Color.Black)
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(notes) { note ->

                NoteCard(note = note, isDarkMode = isDarkMode, onNoteClick = onNoteClick, onToggleFavorite = onToggleFavorite)
            }
        }
    }
}

@Composable
fun FavoritesScreen(notes: List<Note>, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int) -> Unit) {
    val favoriteNotes = notes.filter { it.isFavorite }
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Catatan Favorit", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black, modifier = Modifier.padding(16.dp))
        NoteListScreen(notes = favoriteNotes, isDarkMode = isDarkMode, onNoteClick = onNoteClick, onToggleFavorite = onToggleFavorite)
    }
}

@Composable
fun AddNoteScreen(onSave: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            NoteAppSecondaryTopBar(
                title = "Tambah Catatan",
                onBack = onBack,
                actions = {
                    TextButton(onClick = {
                        onSave(title, content)
                        onBack()
                    }) { Text("Simpan", color = Color.White) }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Catatan") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Isi Catatan") }, modifier = Modifier.fillMaxWidth().weight(1f))
        }
    }
}

@Composable
fun EditNoteScreen(noteId: Int, note: Note?, onSave: (Int, String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }


    Scaffold(
        topBar = {
            NoteAppSecondaryTopBar(
                title = "Edit Catatan",
                onBack = onBack,
                actions = {
                    TextButton(onClick = {
                        onSave(noteId, title, content)
                        onBack()
                    }) { Text("Update", color = Color.White) }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Catatan") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Isi Catatan") }, modifier = Modifier.fillMaxWidth().weight(1f))
        }
    }
}

@Composable
fun NoteDetailScreen(note: Note?, isDarkMode: Boolean, onBack: () -> Unit, onEditClick: (Int) -> Unit) {
    if (note == null) {
        Text("Catatan tidak ditemukan", modifier = Modifier.padding(16.dp))
        return
    }


    Scaffold(
        topBar = {
            NoteAppSecondaryTopBar(
                title = "Detail Catatan",
                onBack = onBack,
                actions = {
                    IconButton(onClick = { onEditClick(note.id) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit") // Ikon Edit standar
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp)) {
            Text(note.title.ifEmpty { "Tanpa Judul" }, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black)
            Spacer(modifier = Modifier.height(6.dp))
            // 👇 TANGGAL DI DETAIL 👇
            Text(
                "Dibuat: ${formatTimestamp(note.timestamp)}",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))
            Text(note.content, fontSize = 16.sp, modifier = Modifier.weight(1f), color = if (isDarkMode) Color.White else Color.Black)
        }
    }
}