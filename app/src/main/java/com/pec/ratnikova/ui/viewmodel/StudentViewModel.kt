package com.pec.ratnikova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec.ratnikova.data.Student
import com.pec.ratnikova.data.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val student: Student) : UiState()
    data class Error(val message: String) : UiState()
}

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(code: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val student = repository.login(code)
            if (student != null) {
                _uiState.value = UiState.Success(student)
            } else {
                _uiState.value = UiState.Error("Invalid code or connection error")
            }
        }
    }

    fun getStudent(code: String) {
        viewModelScope.launch {
            val student = repository.getStudent(code)
            if (student != null) {
                _uiState.value = UiState.Success(student)
            }
        }
    }

    fun updateAvatar(code: String, avatarUrl: String) {
        viewModelScope.launch {
            val success = repository.uploadAvatar(code, avatarUrl)
            if (success) {
                getStudent(code) // Refresh student data
            }
        }
    }
    
    fun reset() {
        _uiState.value = UiState.Idle
    }
}
