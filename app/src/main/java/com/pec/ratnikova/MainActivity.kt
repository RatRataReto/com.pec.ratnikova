package com.pec.ratnikova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pec.ratnikova.data.RetrofitClient
import com.pec.ratnikova.data.SessionManager
import com.pec.ratnikova.data.StudentRepository
import com.pec.ratnikova.ui.screens.LoginScreen
import com.pec.ratnikova.ui.screens.ProfileScreen
import com.pec.ratnikova.ui.screens.LoadingScreen
import com.pec.ratnikova.ui.theme.BackgroundDark
import com.pec.ratnikova.ui.theme.StudentQRTheme
import com.pec.ratnikova.ui.viewmodel.StudentViewModel
import com.pec.ratnikova.ui.viewmodel.UiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        val repository = StudentRepository(RetrofitClient.apiService)
        val viewModel = StudentViewModel(repository)

        enableEdgeToEdge()
        setContent {
            StudentQRTheme {
                MainApp(sessionManager, viewModel)
            }
        }
    }
}

@Composable
fun MainApp(sessionManager: SessionManager, viewModel: StudentViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    var showSaveDialog by remember { mutableStateOf(false) }
    var pendingCode by remember { mutableStateOf("") }

    val savedCode = remember { sessionManager.getStudentCode() }

    LaunchedEffect(savedCode) {
        if (savedCode != null) {
            viewModel.login(savedCode)
        }
    }

    NavHost(navController = navController, startDestination = if (savedCode != null) "profile" else "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = { code ->
                pendingCode = code
                viewModel.login(code)
            })
        }
        composable("profile") {
            when (uiState) {
                is UiState.Success -> {
                    val student = (uiState as UiState.Success).student
                    ProfileScreen(
                        student = student,
                        onBack = {
                            viewModel.reset()
                            navController.navigate("login")
                        },
                        onAvatarUpdated = { avatarUrl ->
                            viewModel.updateAvatar(student.id, avatarUrl)
                        }
                    )
                }
                is UiState.Loading -> {
                    LoadingScreen()
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark))
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            if (navController.currentDestination?.route == "login") {
                navController.navigate("profile")
                if (sessionManager.getStudentCode() == null) {
                    showSaveDialog = true
                }
            }
        }
    }

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    sessionManager.saveStudentCode(pendingCode)
                    showSaveDialog = false
                }) {
                    Text("Хорошо", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Отмена", color = Color.Gray)
                }
            },
            title = {
                Text("Сохраните данные для быстрого входа", fontSize = 16.sp)
            },
            text = {
                Text("Выйдите из аккаунта, если вошли в приложение не на своем устройстве", fontSize = 12.sp, color = Color.Gray)
            },
            containerColor = Color.White
        )
    }
}