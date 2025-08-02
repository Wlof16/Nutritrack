package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val _foodScore = MutableStateFlow<String>("")
    val foodScore: StateFlow<String> = _foodScore.asStateFlow()

    private val _userName = MutableStateFlow<String>("User")
    val userName: StateFlow<String> = _userName.asStateFlow()

    val userID = AuthManager.getStudentId() ?: -1

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    fun loadDataIntoHomeScreen() = viewModelScope.launch((Dispatchers.IO)) {
        patientRepository.getPatientById(userID).collect { patient ->
            _userName.value = patient.userName.toString()
            _foodScore.value = patient.hEIFAtotalscore.toString()
        }
    }

    init {
        loadDataIntoHomeScreen()
    }

    class HomeScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            HomeScreenViewModel(context) as T
    }
}