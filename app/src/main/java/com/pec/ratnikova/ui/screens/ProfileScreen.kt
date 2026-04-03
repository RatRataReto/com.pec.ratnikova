package com.pec.ratnikova.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pec.ratnikova.data.Student
import com.pec.ratnikova.ui.theme.BackgroundDark
import com.pec.ratnikova.ui.theme.ProfileCardBg
import com.pec.ratnikova.ui.utils.QRCodeUtils

@Composable
fun ProfileScreen(
    student: Student,
    onBack: () -> Unit,
    onAvatarUpdated: (String) -> Unit
) {
    var showAvatarDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "ПРОФИЛЬ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Avatar
            Box(contentAlignment = Alignment.BottomEnd) {
                if (student.avatarBase64 != null) {
                    AsyncImage(
                        model = student.avatarBase64,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 40.sp)
                    }
                }
                IconButton(
                    onClick = { showAvatarDialog = true },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(Icons.Default.Edit, "Edit", tint = Color.Black, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(student.fullName, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(24.dp))
            InfoCard(label = "Курс", value = "${student.course} курс")
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(label = "Организация", value = student.organization)
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(label = "Группа", value = student.specialty.split(" ").last())

            Spacer(modifier = Modifier.weight(1f))

            // QR Code
            student.hash?.let { hash ->
                val qrBitmap = remember(hash) { QRCodeUtils.generateQRCode(hash, 512) }
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }

        if (showAvatarDialog) {
            AvatarSelectionDialog(
                onDismiss = { showAvatarDialog = false },
                onAvatarSelected = { avatarUrl ->
                    onAvatarUpdated(avatarUrl)
                    showAvatarDialog = false
                }
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ProfileCardBg)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(label, color = Color.LightGray, fontSize = 12.sp)
        Text(
            value,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(8.dp)
        )
    }
}
