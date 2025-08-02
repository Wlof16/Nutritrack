package com.fit2081.XuanRen_33523436.nutritrack

sealed interface UiState {
    object Initial: UiState

    object Loading: UiState

    data class Success(val outputText: String) : UiState

    data class Error(val errorMessage: String) : UiState
}