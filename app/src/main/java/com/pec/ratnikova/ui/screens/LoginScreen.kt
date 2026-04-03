package com.pec.ratnikova.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.pec.ratnikova.ui.components.ArcHeader
import com.pec.ratnikova.ui.components.HeaderCardInfo
import com.pec.ratnikova.ui.theme.LoginBackground
import com.pec.ratnikova.ui.theme.LoginHeader

@Composable
fun LoginScreen(
    onLoginSuccess: (String, Boolean) -> Unit // Added isRemembered parameter
) {
    var code by remember { mutableStateOf("") }
    var isRemembered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Arc Header with user-provided darker color
            ArcHeader(
                title = "Вход в аккаунт", 
                backgroundColor = LoginHeader
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = com.pec.ratnikova.R.drawable.ic_logo_pec),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Input Card
            HeaderCardInfo(
                label = "Введите ваш код",
                headerColor = LoginHeader,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
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
                    IconButton(onClick = { if (code.isNotEmpty()) onLoginSuccess(code, isRemembered) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Login",
                            tint = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Redesigned Switch with Label (Quick Login)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "БЫСТРЫЙ ВХОД",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.width(12.dp))

                Switch(
                    checked = isRemembered,
                    onCheckedChange = { isRemembered = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF4A90E2),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFF6B728E)
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
