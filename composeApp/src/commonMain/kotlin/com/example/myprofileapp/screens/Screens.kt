package com.example.myprofileapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.data.NotesUiState
import kotlinx.coroutines.delay
import kotlinx.datetime.*
import org.koin.compose.koinInject

val SageGreen = Color(0xFF8BA888)
val OffWhiteBg = Color(0xFFFBFBFB)
val PureWhite = Color(0xFFFFFFFF)
val TextPrimaryDark = Color(0xFF2C302E)
val TextSecondary = Color(0xFF8E918F)

fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
    return "${localDateTime.dayOfMonth} ${monthNames[localDateTime.monthNumber - 1]} ${localDateTime.year}, ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}

fun formatRealtimeClock(instant: Instant): String {
    val time = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
}

@Composable
fun GlobalOfflineBanner() {
    val networkMonitor: com.example.myprofileapp.platform.NetworkMonitor = koinInject()
    val isConnected by networkMonitor.observeConnectivity().collectAsState(initial = true)

    var showBanner by remember { mutableStateOf(false) }

    // Logika Pintar: Muncul 3 detik lalu sembunyi
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            showBanner = true
            delay(3000) // Tampil 3 detik
            showBanner = false
        } else {
            showBanner = false
        }
    }

    AnimatedVisibility(
        visible = showBanner,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50)) // Bentuk kapsul yang rapi
                    .background(Color(0xFFE53935))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CloudOff, contentDescription = "Offline", tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Kamu sedang offline", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
// ==========================================

@Composable
fun AppHeader(isDarkMode: Boolean) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }
    LaunchedEffect(Unit) {
        while (true) { delay(1000); currentTime = Clock.System.now() }
    }
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text("Halo, Piela", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = if(isDarkMode) PureWhite else TextPrimaryDark, letterSpacing = (-0.5).sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Tuliskan ide cemerlangmu hari ini.", fontSize = 15.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
        }
        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(if(isDarkMode) Color(0xFF1E1E1E) else PureWhite).border(1.dp, Color.LightGray.copy(alpha=0.2f), RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(formatRealtimeClock(currentTime), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SageGreen)
        }
    }
}

@Composable
fun SearchBarClean(searchQuery: String, onSearchChanged: (String) -> Unit, isDarkMode: Boolean) {
    val bgColors = if (isDarkMode) Color(0xFF1E1E1E) else PureWhite
    val textColors = if (isDarkMode) PureWhite else TextPrimaryDark
    TextField(
        value = searchQuery, onValueChange = onSearchChanged, placeholder = { Text("Cari catatanmu...", color = TextSecondary) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = TextSecondary) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(54.dp), shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(focusedContainerColor = bgColors, unfocusedContainerColor = bgColors, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = textColors, unfocusedTextColor = textColors)
    )
}

@Composable
fun CategoryChips(selectedCategory: String, onCategorySelected: (String) -> Unit, isDarkMode: Boolean) {
    val categories = listOf("Semua", "Penting \uD83D\uDCCC", "Ide \uD83D\uDCA1", "Tugas \uD83D\uDCDD")
    LazyRow(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp), contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            val chipBg = if (isSelected) SageGreen else if (isDarkMode) Color(0xFF1E1E1E) else PureWhite
            val chipText = if (isSelected) PureWhite else TextSecondary
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(chipBg).clickable { onCategorySelected(category) }.border(1.dp, if (isSelected || isDarkMode) Color.Transparent else Color(0xFFEEEEEE), RoundedCornerShape(20.dp)).padding(horizontal = 16.dp, vertical = 8.dp)
            ) { Text(text = category, color = chipText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold) }
        }
    }
}

@Composable
fun PinterestNoteCard(note: Note, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onNoteClick(note.id) }, shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDarkMode) Color(0xFF1E1E1E) else PureWhite), elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(note.title.ifEmpty { "Tanpa Judul" }, fontWeight = FontWeight.Bold, color = if (isDarkMode) PureWhite else TextPrimaryDark, fontSize = 17.sp, lineHeight = 22.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(note.content, color = TextSecondary, fontSize = 14.sp, maxLines = 5, overflow = TextOverflow.Ellipsis, lineHeight = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Date", tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(formatTimestamp(note.timestamp).split(",")[0], fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                }
                IconButton(onClick = { onToggleFavorite(note.id) }, modifier = Modifier.size(24.dp)) {
                    Icon(imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = if (note.isFavorite) SageGreen else Color.LightGray, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun NoteListScreen(uiState: NotesUiState, searchQuery: String, onSearchChanged: (String) -> Unit, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int, Boolean) -> Unit) {
    var selectedCategory by remember { mutableStateOf("Semua") }
    var isSortDescending by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(if (isDarkMode) Color(0xFF121212) else OffWhiteBg)) {
        GlobalOfflineBanner() // Memanggil Banner di Home

        AppHeader(isDarkMode)
        SearchBarClean(searchQuery, onSearchChanged, isDarkMode)
        CategoryChips(selectedCategory, { selectedCategory = it }, isDarkMode)

        when (uiState) {
            is NotesUiState.Loading -> { Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = SageGreen) } }
            is NotesUiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Belum ada catatan.", color = TextSecondary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text("Mulai tuangkan idemu sekarang.", color = Color.LightGray, fontSize = 14.sp)
                    }
                }
            }
            is NotesUiState.Success -> {
                val filteredNotes = when (selectedCategory) {
                    "Penting \uD83D\uDCCC" -> uiState.notes.filter { it.isFavorite }
                    "Ide \uD83D\uDCA1" -> uiState.notes.filter { it.title.contains("ide", true) || it.content.contains("ide", true) }
                    "Tugas \uD83D\uDCDD" -> uiState.notes.filter { it.title.contains("tugas", true) || it.content.contains("tugas", true) }
                    else -> uiState.notes
                }

                val sortedNotes = if (isSortDescending) {
                    filteredNotes.sortedByDescending { it.timestamp }
                } else {
                    filteredNotes.sortedBy { it.timestamp }
                }

                if (sortedNotes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) { Text("Tidak ada catatan di kategori ini.", color = TextSecondary) }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Catatan Anda", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if(isDarkMode) PureWhite else TextPrimaryDark)
                        TextButton(onClick = { isSortDescending = !isSortDescending }, contentPadding = PaddingValues(0.dp)) {
                            Icon(Icons.Default.SwapVert, contentDescription = "Sort", modifier = Modifier.size(18.dp), tint = SageGreen)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isSortDescending) "Terbaru" else "Terlama", color = SageGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2), modifier = Modifier.fillMaxSize().weight(1f),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 80.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp), verticalItemSpacing = 16.dp
                    ) { items(sortedNotes) { note -> PinterestNoteCard(note = note, isDarkMode = isDarkMode, onNoteClick = onNoteClick, onToggleFavorite = { noteId -> onToggleFavorite(noteId, note.isFavorite) }) } }
                }
            }
        }
    }
}

@Composable
fun FavoritesScreen(notes: List<Note>, isDarkMode: Boolean, onNoteClick: (Int) -> Unit, onToggleFavorite: (Int) -> Unit) {
    val favoriteNotes = notes.filter { it.isFavorite }
    Column(modifier = Modifier.fillMaxSize().background(if (isDarkMode) Color(0xFF121212) else OffWhiteBg)) {
        GlobalOfflineBanner() // Memanggil Banner di Favorit

        Text("Favorit Saya", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = if (isDarkMode) PureWhite else TextPrimaryDark, modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 24.dp), letterSpacing = (-0.5).sp)
        if (favoriteNotes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) { Text("Belum ada yang ditandai.", color = TextSecondary) }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2), modifier = Modifier.fillMaxSize().weight(1f),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp), verticalItemSpacing = 16.dp
            ) { items(favoriteNotes) { note -> PinterestNoteCard(note = note, isDarkMode = isDarkMode, onNoteClick = onNoteClick, onToggleFavorite = { noteId -> onToggleFavorite(noteId) }) } }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(isDarkMode: Boolean, onSave: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isReminderSet by remember { mutableStateOf(false) }
    val bgColor = if (isDarkMode) Color(0xFF121212) else OffWhiteBg
    val textColor = if (isDarkMode) PureWhite else TextPrimaryDark

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) } },
                actions = {
                    IconButton(onClick = { isReminderSet = !isReminderSet }) {
                        Icon(if (isReminderSet) Icons.Default.Notifications else Icons.Outlined.Notifications, contentDescription = "Pengingat", tint = if (isReminderSet) SageGreen else TextSecondary)
                    }
                    TextButton(onClick = { onSave(title, content); onBack() }) { Text("Simpan", color = SageGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = bgColor)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(bgColor).padding(innerPadding).padding(horizontal = 24.dp)) {
            if (isReminderSet) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(SageGreen.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pengingat diatur untuk: Besok, 08:00 WIB", color = SageGreen, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            TextField(
                value = title, onValueChange = { title = it },
                placeholder = { Text("Judul Catatan", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = TextSecondary.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth(), textStyle = LocalTextStyle.current.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = textColor),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
            )
            TextField(
                value = content, onValueChange = { content = it },
                placeholder = { Text("Mulai menulis...", fontSize = 18.sp, color = TextSecondary.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth().weight(1f), textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, lineHeight = 28.sp, color = textColor),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(noteId: Int, note: Note?, isDarkMode: Boolean, onSave: (Int, String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var isReminderSet by remember { mutableStateOf(false) }
    val bgColor = if (isDarkMode) Color(0xFF121212) else OffWhiteBg
    val textColor = if (isDarkMode) PureWhite else TextPrimaryDark

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) } },
                actions = {
                    IconButton(onClick = { isReminderSet = !isReminderSet }) {
                        Icon(if (isReminderSet) Icons.Default.Notifications else Icons.Outlined.Notifications, contentDescription = "Pengingat", tint = if (isReminderSet) SageGreen else TextSecondary)
                    }
                    TextButton(onClick = { onSave(noteId, title, content); onBack() }) { Text("Update", color = SageGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = bgColor)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(bgColor).padding(innerPadding).padding(horizontal = 24.dp)) {
            if (isReminderSet) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(SageGreen.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pengingat diatur untuk: Besok, 08:00 WIB", color = SageGreen, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            TextField(
                value = title, onValueChange = { title = it }, textStyle = LocalTextStyle.current.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = textColor),
                modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
            )
            TextField(
                value = content, onValueChange = { content = it }, textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, lineHeight = 28.sp, color = textColor),
                modifier = Modifier.fillMaxWidth().weight(1f), colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(note: Note?, isDarkMode: Boolean, onBack: () -> Unit, onEditClick: (Int) -> Unit, onDeleteClick: (Int) -> Unit) {
    if (note == null) return
    val bgColor = if (isDarkMode) Color(0xFF121212) else OffWhiteBg
    val textColor = if (isDarkMode) PureWhite else TextPrimaryDark

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = textColor) } },
                actions = {
                    IconButton(onClick = { onDeleteClick(note.id) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color(0xFFE53935)) }
                    IconButton(onClick = { onEditClick(note.id) }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = textColor) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = bgColor)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(bgColor).padding(innerPadding).padding(horizontal = 24.dp)) {
            Text(note.title.ifEmpty { "Tanpa Judul" }, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = textColor, lineHeight = 38.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Notifications, contentDescription = "Date", tint = TextSecondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(formatTimestamp(note.timestamp), fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(note.content, fontSize = 18.sp, modifier = Modifier.weight(1f), color = textColor.copy(alpha = 0.9f), lineHeight = 28.sp)
        }
    }
}