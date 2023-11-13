package com.example.trendy.common

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object Empty : UiState()
    class Error(val errorCode: Int) : UiState()
}