package com.example.myprofileapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.components.EditProfileDialog
import com.example.myprofileapp.components.InfoItem
import com.example.myprofileapp.components.ProfileCard
import com.example.myprofileapp.components.ProfileHeader
import com.example.myprofileapp.components.StatItem
import com.example.myprofileapp.data.ProfileUiState

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
                        InfoItem("✉️", "Email", "muhammad.123140200@student.itera.ac.id", textColorPrimary, dividerColor)
                        InfoItem("📞", "Telepon", "+62 815-7311-0182", textColorPrimary, dividerColor)
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
