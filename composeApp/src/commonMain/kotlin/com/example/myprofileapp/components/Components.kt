package com.example.myprofileapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.download
import org.jetbrains.compose.resources.painterResource

val SageGreen = Color(0xFF8BA888)

@Composable
fun LabeledTextField(label: String, value: String, onValueChange: (String) -> Unit, isDark: Boolean) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isDark) Color(0xFF2A2A2A) else Color(0xFFF5F6F8),
            unfocusedContainerColor = if (isDark) Color(0xFF2A2A2A) else Color(0xFFF5F6F8),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EditProfileDialog(
    currentName: String, currentBio: String, isDark: Boolean, onDismiss: () -> Unit, onSave: (String, String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }
    var bioInput by remember { mutableStateOf(currentBio) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text(text = "Edit Profil", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                LabeledTextField(label = "Nama Lengkap", value = nameInput, onValueChange = { nameInput = it }, isDark = isDark)
                LabeledTextField(label = "Bio", value = bioInput, onValueChange = { bioInput = it }, isDark = isDark)
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(nameInput, bioInput) },
                // PERBAIKAN: Ubah warna merah jadi Sage Green!
                colors = ButtonDefaults.buttonColors(containerColor = SageGreen)
            ) { Text("Simpan", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal", color = Color.Gray) }
        }
    )
}


@Composable
fun ProfileHeader(name: String, title: String, bio: String, isDarkMode: Boolean) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp, start = 24.dp, end = 24.dp, bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp).border(2.dp, Color.LightGray.copy(alpha = 0.3f), CircleShape).padding(4.dp)) {
            Image(painter = painterResource(Res.drawable.download), contentDescription = "Foto Profil", modifier = Modifier.size(112.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = name, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center, letterSpacing = (-0.5).sp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.clip(RoundedCornerShape(50)).background(if (isDarkMode) Color(0xFF2A2A2A) else Color(0xFFF5F6F8)).padding(horizontal = 16.dp, vertical = 6.dp)) {
            Text(text = title, fontSize = 13.sp, color = if (isDarkMode) Color.LightGray else Color.DarkGray, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = bio, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center, lineHeight = 22.sp, modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun StatItem(value: String, label: String, textColor: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun InfoItem(icon: ImageVector, label: String, value: String, textColorPrimary: Color, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(44.dp).clip(CircleShape).background(Color.Gray.copy(alpha = 0.1f))) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.Gray, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 15.sp, color = textColorPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ProfileCard(title: String, containerColor: Color, titleColor: Color, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = containerColor), elevation = CardDefaults.cardElevation(0.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = titleColor, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 12.dp))
            content()
        }
    }
}