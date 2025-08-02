package com.fit2081.XuanRen_33523436.nutritrack

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


object AuthManager {
    val _userId: MutableState<Int?> = mutableStateOf(null)


    fun login(userId: Int?) {
        _userId.value = userId
    }

    fun logout() {
        _userId.value = null
    }

    fun getStudentId(): Int? {
        return _userId.value
    }
}