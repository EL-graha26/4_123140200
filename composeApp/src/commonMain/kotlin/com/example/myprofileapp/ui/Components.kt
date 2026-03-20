package com.example.myprofileapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.ProfileUiState
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.download
import org.jetbrains.compose.resources.painterResource

@Composable
fun LabeledTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}

@Composable
fun EditProfileDialog(
    currentName: String, currentBio: String, onDismiss: () -> Unit, onSave: (String, String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }
    var bioInput by remember { mutableStateOf(currentBio) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Profil") },
        text = {
            Column {
                LabeledTextField(label = "Nama Lengkap", value = nameInput, onValueChange = { nameInput = it })
                LabeledTextField(label = "Bio", value = bioInput, onValueChange = { bioInput = it })
            }
        },
        confirmButton = { Button(onClick = { onSave(nameInput, bioInput) }) { Text("Simpan") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Batal") } }
    )
}

@Composable
fun ProfileHeader(
    name: String, title: String, bio: String, isDarkMode: Boolean, onToggleDarkMode: (Boolean) -> Unit,
    headerTop: Color, headerBottom: Color
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(Brush.verticalGradient(listOf(headerTop, headerBottom)))
            .padding(bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Switch Dark Mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isDarkMode) "🌙 Dark Mode" else "☀️ Light Mode",
                    color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onToggleDarkMode,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = headerBottom,
                        checkedTrackColor = Color.White,
                        uncheckedThumbColor = Color.LightGray,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.5f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Foto Profil
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(110.dp).border(3.dp, Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                Image(
                    painter = painterResource(Res.drawable.download),
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(102.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.18f)).padding(horizontal = 16.dp, vertical = 5.dp)) {
                Text(text = title, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(modifier = Modifier.width(36.dp), color = Color.White.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = bio, fontSize = 13.sp, color = Color.White, textAlign = TextAlign.Center, lineHeight = 21.sp, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun StatItem(value: String, label: String, textColor: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun InfoItem(emoji: String, label: String, value: String, textColorPrimary: Color, dividerColor: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Gray.copy(alpha = 0.12f))) {
            Text(text = emoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 14.sp, color = textColorPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
    HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(start = 54.dp))
}

@Composable
fun ProfileCard(title: String, containerColor: Color, titleColor: Color, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = title.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold,
                color = titleColor, letterSpacing = 1.5.sp, modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
            content()
        }
    }
}

@Composable
fun ProfileScreen(uiState: ProfileUiState, onEditProfile: (String, String) -> Unit, onToggleDarkMode: (Boolean) -> Unit) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(false) } // State tombol Follow
    val scrollState = rememberScrollState()

    // === LOGIKA WARNA DINAMIS (DARK MODE / LIGHT MODE) ===
    val isDark = uiState.isDarkMode

    // Background dan Kartu
    val bgColor = if (isDark) Color(0xFF121212) else Color(0xFFFFF0F0)
    val surfaceColor = if (isDark) Color(0xFF1E1E1E) else Color(0xFFFFFFFF)

    // Teks dan Pembatas
    val textColorPrimary = if (isDark) Color.White else Color(0xFF212121)
    val dividerColor = if (isDark) Color(0xFF333333) else Color(0xFFE0E0E0)

    // Warna Merah (Di-soft-kan kalau masuk Dark Mode biar nggak sakit di mata)
    val headerTop = if (isDark) Color(0xFF6B1111) else Color(0xFFB71C1C)
    val headerBottom = if (isDark) Color(0xFF8E1D1D) else Color(0xFFE53935)

    // Warna Tombol
    val btnFollowBg = if (isFollowing) Color.DarkGray else headerTop
    val btnEditBg = if (isDark) Color(0xFF333333) else Color.White
    val btnEditContent = if (isDark) Color.White else headerTop

    // Tampilkan Dialog saat tombol Edit ditekan
    if (showEditDialog) {
        EditProfileDialog(
            currentName = uiState.name, currentBio = uiState.bio,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio ->
                onEditProfile(newName, newBio)
                showEditDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(bgColor).verticalScroll(scrollState)) {

        ProfileHeader(
            name = uiState.name, title = uiState.title, bio = uiState.bio,
            isDarkMode = isDark, onToggleDarkMode = onToggleDarkMode,
            headerTop = headerTop, headerBottom = headerBottom
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).offset(y = (-20).dp),
            shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = surfaceColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem(value = "12", label = "Proyek", textColor = textColorPrimary)
                StatItem(value = "4.00", label = "IPK", textColor = textColorPrimary)
                StatItem(value = "6", label = "Semester", textColor = textColorPrimary)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tombol Follow
            Button(
                onClick = { isFollowing = !isFollowing },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = btnFollowBg)
            ) {
                Text(if (isFollowing) "✔️ Following" else "➕ Follow", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }

            // Tombol Edit Profil
            OutlinedButton(
                onClick = { showEditDialog = true },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = btnEditBg)
            ) {
                Text("✏️ Edit Profil", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = btnEditContent)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showDetails = !showDetails },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = headerBottom)
        ) {
            Text(if (showDetails) "Tutup Detail Kontak" else "Lihat Detail Kontak", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = showDetails,
            enter = expandVertically(animationSpec = tween(500)),
            exit = shrinkVertically(animationSpec = tween(500))
        ) {
            Column {
                ProfileCard(title = "Informasi Kontak", containerColor = surfaceColor, titleColor = headerBottom) {
                    InfoItem("✉️", "Email", "emailmu@student.itera.ac.id", textColorPrimary, dividerColor)
                    InfoItem("📞", "Telepon", "+62 812-3456-7890", textColorPrimary, dividerColor)
                    InfoItem("📍", "Lokasi", "Bandar Lampung, Indonesia", textColorPrimary, dividerColor)
                }

                ProfileCard(title = "Keahlian", containerColor = surfaceColor, titleColor = headerBottom) {
                    InfoItem("⭐", "Mobile Dev", "Kotlin, Compose", textColorPrimary, dividerColor)
                    InfoItem("🛠️", "Data", "MySQL, MongoDB", textColorPrimary, dividerColor)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}