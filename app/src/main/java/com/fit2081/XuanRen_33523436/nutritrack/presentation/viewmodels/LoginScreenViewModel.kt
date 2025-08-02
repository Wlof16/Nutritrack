package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(context: Context): ViewModel() {
    var hasUserLoggedIn by mutableStateOf(false)
        private set

    init {
        checkPrevUserLoggedIn()
    }
    private val patientRepository =
        PatientsRepository(context)

    private val _allRegisteredPatientsId = MutableStateFlow<List<Int>>(emptyList())
    val allRegisteredPatientsId: StateFlow<List<Int>> = _allRegisteredPatientsId.asStateFlow()

    var expanded by mutableStateOf(false)
        private set

    var selectedOptionText by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordError by mutableStateOf(false)
        private set

    var loginError by mutableStateOf(false)
        private set

    var inputError by mutableStateOf(false)
        private set

    var isAuthorized by mutableStateOf(false)
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    var loggedInUser by mutableIntStateOf(-1)
        private set

    fun toggleExpanded() {
        expanded = !expanded
    }

    fun togglePassView() {
        isPasswordVisible = !isPasswordVisible
    }

    fun updateExpanded(boolean: Boolean) {
        expanded = boolean
    }

    fun updateSelectedOptionText(string: String) {
        selectedOptionText = string
    }

    fun updatePass(string: String) {
        password = string
    }

    fun updateLogInError(boolean: Boolean) {
        loginError = boolean
    }

    fun updateInputError(boolean: Boolean) {
        inputError = boolean
    }

    fun updateHasUserLoggedIn(boolean: Boolean) {
        hasUserLoggedIn = boolean
    }

    fun hasUserWithPass() = viewModelScope.launch(Dispatchers.IO) {
        val userId = selectedOptionText.toInt()
        isAuthorized = patientRepository.hasUserWithPass(userId, password)
        loginError = !isAuthorized
        if (isAuthorized) {
            loggedInUser = userId
            AuthManager.login(userId)
        }
    }

    fun getRegisteredPatientID() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.getRegisteredPatientsId().collect { id ->
            _allRegisteredPatientsId.value = id
        }
    }

    init {
        getRegisteredPatientID()
    }

    fun loginButtonCheck() {
        if (!passwordError && selectedOptionText != "") {
            hasUserWithPass()
        }
        else {
            inputError = true
        }
    }

    fun checkPrevUserLoggedIn() {
        hasUserLoggedIn = AuthManager.getStudentId() != null
    }

    fun isPasswordValid(password: String) {
        passwordError = password.length < 8
    }

    class LoginScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            LoginScreenViewModel(context) as T
    }
}