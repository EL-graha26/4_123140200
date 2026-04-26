package com.example.myprofileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.components.EditProfileDialog
import com.example.myprofileapp.data.ProfileUiState
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.download
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(uiState: ProfileUiState, onEditProfile: (String, String) -> Unit, onToggleDarkMode: (Boolean) -> Unit) {
    var showEditDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val deviceInfo: com.example.myprofileapp.platform.DeviceInfo = koinInject()
    val batteryInfo: com.example.myprofileapp.platform.BatteryInfo = koinInject()

    // Mengumpulkan Aliran Data Baterai secara Real-time
    val batteryLevel by batteryInfo.observeBatteryLevel().collectAsState(initial = 0)
    val isCharging by batteryInfo.observeChargingStatus().collectAsState(initial = false)

    val isDark = uiState.isDarkMode
    val bgColor = if (isDark) Color(0xFF121212) else OffWhiteBg
    val cardColor = if (isDark) Color(0xFF1E1E1E) else PureWhite
    val textColor = if (isDark) PureWhite else TextPrimaryDark

    if (showEditDialog) {
        EditProfileDialog(
            currentName = uiState.name, currentBio = uiState.bio, isDark = isDark,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio -> onEditProfile(newName, newBio); showEditDialog = false }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(bgColor).verticalScroll(scrollState).padding(bottom = 80.dp)) {
        GlobalOfflineBanner()

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp).border(2.dp, SageGreen.copy(alpha = 0.5f), CircleShape).padding(6.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.download),
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(108.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(uiState.name, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(uiState.title, fontSize = 14.sp, color = SageGreen, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(uiState.bio, fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp), lineHeight = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showEditDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF333333) else Color(0xFFEEEEEE)),
                shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = textColor, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profil", color = textColor, fontWeight = FontWeight.SemiBold)
            }
        }

        Text("Pengaturan", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = textColor, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(if(isDark) Color(0xFF333333) else Color(0xFFF0F0F0)), contentAlignment = Alignment.Center) {
                        Icon(if (isDark) Icons.Default.DarkMode else Icons.Default.LightMode, contentDescription = "Mode", tint = if (isDark) Color(0xFFF6E05E) else Color(0xFFED8936))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(if (isDark) "Mode Gelap Aktif" else "Mode Terang Aktif", fontWeight = FontWeight.Bold, color = textColor, fontSize = 15.sp)
                        Text("Tema aplikasi akan tersimpan", color = TextSecondary, fontSize = 12.sp)
                    }
                }
                Switch(
                    checked = isDark,
                    onCheckedChange = { onToggleDarkMode(it) },
                    colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = SageGreen, uncheckedThumbColor = Color.Gray, uncheckedTrackColor = Color(0xFFE0E0E0))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Informasi Perangkat", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = textColor, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                ContactItem(Icons.Default.PhoneAndroid, "Tipe Perangkat", deviceInfo.getDeviceName(), textColor, isDark)
                HorizontalDivider(color = if(isDark) Color(0xFF333333) else Color(0xFFF0F0F0), modifier = Modifier.padding(start = 72.dp))
                ContactItem(Icons.Default.SystemUpdate, "Sistem Operasi", deviceInfo.getOsVersion(), textColor, isDark)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Status Baterai", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = textColor, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(44.dp).clip(CircleShape).background(if(isDark) Color(0xFF333333) else Color(0xFFF0F0F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Ganti Ikon saat ngecas
                            Icon(
                                imageVector = if (isCharging) Icons.Default.Power else Icons.Default.BatteryFull,
                                contentDescription = "Battery",
                                tint = if (isCharging) Color(0xFFFFB300) else if (batteryLevel <= 20) Color(0xFFE53935) else SageGreen,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Daya Perangkat", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text(
                                text = if (isCharging) "Sedang Mengisi Daya" else "Menggunakan Baterai",
                                color = textColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Text(
                        text = "$batteryLevel%",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isCharging) Color(0xFFFFB300) else if (batteryLevel <= 20) Color(0xFFE53935) else SageGreen
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)).background(if (isDark) Color(0xFF333333) else Color(0xFFE0E0E0))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(batteryLevel / 100f) // Lebar menyesuaikan persen
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (isCharging) Color(0xFFFFB300) // Kuning Emas saat ngecas
                                else if (batteryLevel <= 20) Color(0xFFE53935) // Merah kalau mau habis
                                else SageGreen // Hijau Sage
                            )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ContactItem(icon: ImageVector, label: String, value: String, textColor: Color, isDark: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(if(isDark) Color(0xFF333333) else Color(0xFFF0F0F0)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = label, tint = SageGreen, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(value, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}