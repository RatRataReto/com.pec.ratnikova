package com.pec.ratnikova.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pec.ratnikova.ui.theme.BackgroundDark
import com.pec.ratnikova.ui.theme.InputBackground

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var isRemembered by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(40.dp))
            
            Text(
                text = "ВХОД В АККАУНТ",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Logo
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = com.pec.ratnikova.R.drawable.ic_logo_pec),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            // Input Field Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(InputBackground)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = "Введите ваш код",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = code,
                            onValueChange = { code = it },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            placeholder = { Text("123123") },
                            singleLine = true
                        )
                        IconButton(onClick = { if (code.isNotEmpty()) onLoginSuccess(code) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Login",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = isRemembered,
                    onCheckedChange = { isRemembered = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF4A90E2)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "ДОБРО\nПОЖАЛОВАТЬ!",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier.padding(bottom = 60.dp)
            )
        }
    }
}
