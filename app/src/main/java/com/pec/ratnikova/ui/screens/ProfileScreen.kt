package com.pec.ratnikova.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.pec.ratnikova.ui.components.ArcHeader
import com.pec.ratnikova.ui.components.HeaderCardInfo
import com.pec.ratnikova.ui.theme.ProfileArcHeader
import com.pec.ratnikova.ui.theme.ProfileBottomBg
import com.pec.ratnikova.ui.theme.ProfileTopBg
import com.pec.ratnikova.ui.utils.QRCodeUtils

@Composable
fun ProfileScreen(
    student: Student,
    onBack: () -> Unit,
    onAvatarUpdated: (String) -> Unit
) {
    var showAvatarDialog by remember { mutableStateOf(false) }
    
    // Formatting name: Remove patronymic (Фамилия Имя)
    val displayName = remember(student.fullName) {
        student.fullName.split(" ").take(2).joinToString(" ")
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // --- TOP ZONE (#141832) ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ProfileTopBg)
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Header Arc with darker color
                        ArcHeader(title = "ПРОФИЛЬ", backgroundColor = ProfileArcHeader)

                        Spacer(modifier = Modifier.height(30.dp))

                        // Avatar with White Circle and Edit Button
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                if (student.avatarBase64 != null) {
                                    AsyncImage(
                                        model = student.getAvatarBytes(),
                                        contentDescription = "Avatar",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text("👤", fontSize = 40.sp)
                                }
                            }

                            IconButton(
                                onClick = { showAvatarDialog = true },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color.Black, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = displayName,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    // Back arrow in the corner
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 40.dp, start = 8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                }
            }

            // --- SEPARATOR (80% White Line) ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ProfileTopBg), // Matches top zone
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalDivider(
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        thickness = 1.dp
                    )
                }
            }

            // --- BOTTOM ZONE (#232954) ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ProfileBottomBg)
                        .padding(top = 32.dp, bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Info Cards
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        HeaderCardInfo(label = "Курс", headerColor = ProfileArcHeader) {
                            Text("${student.course} курс", color = Color.Black, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HeaderCardInfo(label = "Организация", headerColor = ProfileArcHeader) {
                            Text(student.organization, color = Color.Black, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HeaderCardInfo(label = "Группа", headerColor = ProfileArcHeader) {
                            // Fixed: Wrap text for group if it's too long
                            Text(
                                student.specialty,
                                color = Color.Black, 
                                fontSize = 16.sp,
                                softWrap = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // QR Code
                    student.hash?.let { hash ->
                        val qrBitmap = remember(hash) { QRCodeUtils.generateQRCode(hash, 512) }
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                bitmap = qrBitmap.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
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
