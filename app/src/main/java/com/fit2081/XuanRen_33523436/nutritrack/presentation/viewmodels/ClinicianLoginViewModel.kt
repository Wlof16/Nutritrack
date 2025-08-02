package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository

class ClinicianLoginViewModel(context: Context): ViewModel() {
    private val specialKey = "dollar-entry-apples"

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    var clincianKey by mutableStateOf("")
        private set

    var isAuthorized by mutableStateOf(false)
        private set

    var clinicianKeyError by mutableStateOf(false)
        private set

    fun updateClinicianKey(string: String) {
        clincianKey = string
        clinicianKeyError = false
    }

    fun checkCorrectClinicianKey() {
        isAuthorized = specialKey == clincianKey
        clinicianKeyError = !isAuthorized
    }

    class ClinicianLoginViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ClinicianLoginViewModel(context) as T
    }
}