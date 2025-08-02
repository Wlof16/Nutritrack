package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val _allUnregisteredPatientsId = MutableStateFlow<List<Int>>(emptyList())
    val allUnregisteredPatientsId: StateFlow<List<Int>> = _allUnregisteredPatientsId.asStateFlow()

    var expanded by mutableStateOf(false)
        private set

    var username by mutableStateOf("")
        private set

    var selectedOptionText by mutableStateOf("")
        private set

    var phoneNum by mutableStateOf("")
        private set

    var phoneNumError by mutableStateOf(false)
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordError by mutableStateOf(false)
        private set

    var loginError by mutableStateOf(false)
        private set

    var inputError by mutableStateOf(false)
        private set

    var userRegistered by mutableStateOf(false)
        private set

    var usernameError by mutableStateOf(false)
        private set


    fun toggleExpanded() {
        expanded = !expanded
    }
    fun updateExpanded(boolean: Boolean) {
        expanded = boolean
    }

    fun updateUsername(string: String) {
        username = string
    }

    fun isValidUsername(string: String) {
        val usernamePattern = Regex("^[A-Za-z _]+$")
        usernameError = !usernamePattern.matches(username)
    }

    fun updateSelectedOptionText(string: String) {
        selectedOptionText = string
    }

    fun updateLogInError(boolean: Boolean) {
        loginError = boolean
    }

    fun updateInputError(boolean: Boolean) {
        inputError = boolean
    }

    fun updateUserRegistered(boolean: Boolean) {
        userRegistered = boolean
    }

    fun updatePhoneNum(string: String) {
        phoneNum = string
    }

    //Check if Its a Valid Phone num
    fun isValidPhone(phone: String) {
        phoneNumError = !Patterns.PHONE.matcher(phone).matches()
    }

    fun updatePassword(string: String) {
        password = string
    }

    fun updateConfirmPassword(string: String) {
        confirmPassword = string
    }

    fun isPasswordMinLength(password: String): Boolean {
        return password.length >= 8
    }

    fun isPasswordMatching(): Boolean {
        return password == confirmPassword
    }


    fun isValidPassword(password: String) {
        passwordError = !isPasswordMinLength(password) or !isPasswordMatching()
    }

    fun getUnregistedPatientID() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.getUnregisteredPatientsId().collect { Id ->
            _allUnregisteredPatientsId.value = Id
        }
    }

    init {
        getUnregistedPatientID()
    }

    fun hasUserWithPhoneNum() = viewModelScope.launch(Dispatchers.IO) {
        val userId = selectedOptionText.toInt()
        val userPassword = password
        val username = username
        var isAuthorized = false
        isAuthorized = patientRepository.hasUserWithPhoneNum(userId, phoneNum)
        loginError = !isAuthorized

        //If phone num, id and pass is valid set password for that user and reset all userPass and userId and confirmPass and give msg succesfully registered user
        if (isAuthorized) {
            resetFields()
            patientRepository.updatePasswordAndUsername(userId, userPassword, username)

            userRegistered = true
        }
    }

    fun registerButtonCheck() {
        if (!passwordError && password != "" && !phoneNumError && selectedOptionText != "" && !usernameError && username != "") {
            hasUserWithPhoneNum()
        }
        else {
            inputError = true
        }
    }

    fun resetFields() {
        selectedOptionText = ""
        phoneNum = ""
        password = ""
        confirmPassword = ""
        username = ""
    }

    class RegisterScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            RegisterScreenViewModel(context) as T
    }
}