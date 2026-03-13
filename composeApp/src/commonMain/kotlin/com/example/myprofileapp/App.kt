package com.example.myprofileapp

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
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.download
import org.jetbrains.compose.resources.painterResource

object AppColors {
    val HeaderTop        = Color(0xFFB71C1C)
    val HeaderBottom     = Color(0xFFE53935)
    val Background       = Color(0xFFFFF0F0)
    val Surface          = Color(0xFFFFFFFF)
    val TextPrimary      = Color(0xFF212121)
    val TextSecondary    = Color(0xFF757575)
    val TextOnDark       = Color(0xFFFFFFFF)
    val TextOnDarkMuted  = Color(0xFFFFCDD2)
    val IconEmail        = Color(0xFFD32F2F)
    val IconPhone        = Color(0xFF1976D2)
    val IconLocation     = Color(0xFFF57C00)
    val IconWebsite      = Color(0xFF7B1FA2)
    val IconSkill        = Color(0xFFE53935)
    val DividerColor     = Color(0xFFFFEBEE)
}

@Composable
fun ProfileHeader(name: String, title: String, bio: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(AppColors.HeaderTop, AppColors.HeaderBottom)))
            .padding(bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 44.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AppColors.TextOnDark, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(text = title, fontSize = 12.sp, color = AppColors.TextOnDark, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(modifier = Modifier.width(36.dp), color = Color.White.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = bio, fontSize = 13.sp, color = AppColors.TextOnDarkMuted, textAlign = TextAlign.Center, lineHeight = 21.sp, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AppColors.TextPrimary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = AppColors.TextSecondary)
    }
}

@Composable
fun InfoItem(emoji: String, label: String, value: String, iconTint: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp).clip(CircleShape).background(iconTint.copy(alpha = 0.12f))
        ) {
            Text(text = emoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = AppColors.TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 14.sp, color = AppColors.TextPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
    HorizontalDivider(color = AppColors.DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 54.dp))
}

@Composable
fun ProfileCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = title.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AppColors.HeaderTop,
                letterSpacing = 1.5.sp, modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
            content()
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        var isFollowing by remember { mutableStateOf(false) }
        var showDetails by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        Column(modifier = Modifier.fillMaxSize().background(AppColors.Background).verticalScroll(scrollState)) {
            ProfileHeader(
                name  = "Muhammad Piela Nugraha",
                title = "Mahasiswa Teknik Informatika",
                bio   = "NIM saya 123140200, Saya adalah mahasiswa yang aktif dan sedang mengambil mata kuliah pengenalan aplikasi mobile."
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).offset(y = (-20).dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatItem(value = "12", label = "Proyek")
                    StatItem(value = "4.00", label = "IPK")
                    StatItem(value = "6", label = "Semester")
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { isFollowing = !isFollowing },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isFollowing) Color.DarkGray else AppColors.HeaderTop)
                ) {
                    Text(text = if (isFollowing) "✔️" else "➕", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isFollowing) "Following" else "Follow", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.HeaderBottom)
                ) {
                    Text(if (showDetails) "Tutup Detail" else "Lihat Detail", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = showDetails,
                enter = expandVertically(animationSpec = tween(500)),
                exit = shrinkVertically(animationSpec = tween(500))
            ) {
                Column {
                    ProfileCard(title = "Informasi Kontak") {
                        InfoItem(emoji = "✉️", label = "Email", value = "emailmu@student.itera.ac.id", iconTint = AppColors.IconEmail)
                        InfoItem(emoji = "📞", label = "Telepon", value = "+62 812-3456-7890", iconTint = AppColors.IconPhone)
                        InfoItem(emoji = "📍", label = "Lokasi", value = "Bandar Lampung, Indonesia", iconTint = AppColors.IconLocation)
                        InfoItem(emoji = "🌐", label = "Website", value = "https://github.com/EL-graha26", iconTint = AppColors.IconWebsite)
                    }

                    ProfileCard(title = "Keahlian") {
                        InfoItem(emoji = "⭐", label = "Mobile Dev", value = "Kotlin, Compose", iconTint = AppColors.IconSkill)
                        InfoItem(emoji = "🛠️", label = "Data", value = "MySQL, MongoDB", iconTint = AppColors.IconSkill)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}