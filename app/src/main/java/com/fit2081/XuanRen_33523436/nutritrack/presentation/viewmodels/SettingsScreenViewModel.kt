package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository
import com.fit2081.XuanRen_33523436.nutritrack.presentation.activities.FoodIntakeQuestionnaire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val themeRepository = ThemeRepository(context)

    private val _user = MutableStateFlow<Patient?>(null)
    val user: StateFlow<Patient?> = _user.asStateFlow()

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    var showChangePassDialog by mutableStateOf(false)
        private set

    var showChangeUsernameDialog by mutableStateOf(false)
        private set

    var oldPassError by mutableStateOf(false)
        private set

    var newAndConfirmPassError by mutableStateOf(false)
        private set

    var oldPass by mutableStateOf("")
        private set

    var newPass by mutableStateOf("")
        private set

    var newUsername by mutableStateOf("")
        private set

    var newUsernameError by mutableStateOf(false)
        private set

    var confirmPass by mutableStateOf("")
        private set

    val userID = AuthManager.getStudentId() ?: -1

    var passInputError by mutableStateOf(false)
        private set

    var usernameInputError by mutableStateOf(false)
        private set


    var isAuthorized by mutableStateOf(false)
        private set

    var passSaveSuccess by mutableStateOf(false)
        private set

    var userNameSaveSuccess by mutableStateOf(false)
        private set

    fun loadDataIntoSettings() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.getPatientById(userID).collect { patient ->
            _user.value = patient
        }
    }

    fun isValidUsername(string: String) {
        val usernamePattern = Regex("^[A-Za-z _]+$")
        newUsernameError = !usernamePattern.matches(newUsername)
    }

    fun updateShowChangePassDialog(boolean: Boolean) {
        showChangePassDialog = boolean
    }

    fun updateShowChangeUsernameDialog(boolean: Boolean) {
        showChangeUsernameDialog = boolean
    }

    fun updateOldPassError(boolean: Boolean) {
        oldPassError = boolean
    }

    fun updatePassSaveSuccess(boolean: Boolean) {
        passSaveSuccess = boolean
    }

    fun updateUsernameSaveSuccess(boolean: Boolean) {
        userNameSaveSuccess = boolean
    }


    fun updatePassInputError(boolean: Boolean) {
        passInputError = boolean
    }

    fun updateUsernameInputError(boolean: Boolean) {
        usernameInputError = boolean
    }

    fun updateOldPassword(string: String) {
        oldPass = string
    }

    fun updateNewPassword(string: String) {
        newPass = string
    }

    fun updateConfirmPassword(string: String) {
        confirmPass = string
    }

    fun updateNewUsername(string: String) {
        newUsername = string
    }

    fun toggleDarkMode() {
        isDark = !isDark
        themeRepository.saveDarkMode(isDark)
    }

    fun isValidPassword(password: String) {
        newAndConfirmPassError = !isPasswordMinLength(password) or !isPasswordMatching()
    }

    fun isPasswordMatching(): Boolean {
        return newPass == confirmPass
    }

    fun isPasswordMinLength(password: String): Boolean {
        return password.length >= 8
    }

    fun checkPassSaveSuccessful() {
        if (!newAndConfirmPassError && newPass != "") {
            checkOldPasswordMatches()
        }
        else {
            passInputError = true
        }
    }

    fun checkUsernameSaveSuccessful() {
        if (!newUsernameError && newUsername != "") {
            saveUsername()
        }
        else {
            usernameInputError = true
        }
    }

    fun saveUsername() = viewModelScope.launch(Dispatchers.IO) {
        val newUsername = newUsername
        patientRepository.updateUsername(userID, newUsername)
        userNameSaveSuccess = true
        resetUsernameFields()
    }

    fun checkOldPasswordMatches() = viewModelScope.launch(Dispatchers.IO) {
        val newPassword = newPass
        isAuthorized = patientRepository.hasUserWithPass(userID, oldPass)
        oldPassError = !isAuthorized
        if (isAuthorized) {
            patientRepository.updatePassword(userID, newPassword)
            passSaveSuccess = true
            resetPassFields()
        }
    }

    fun resetUsernameFields() {
        newUsername = ""
        newUsernameError = false
        showChangeUsernameDialog = false
        usernameInputError = false
    }

    fun resetPassFields() {
        oldPassError = false
        newAndConfirmPassError = false
        oldPass = ""
        newPass = ""
        confirmPass = ""
        passInputError = false
        isAuthorized = false
        showChangePassDialog = false
    }

    init {
        loadDataIntoSettings()
    }

    class SettingsScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SettingsScreenViewModel(context) as T
    }
}