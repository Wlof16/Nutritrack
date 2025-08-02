package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntake
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntakeRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.fruit.FruityRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class FoodIntakeQuestionnaireViewModel(context: Context): ViewModel() {
    private val foodIntakeRepository =
        FoodIntakeRepository(context)

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    var showTimeDialog1 by mutableStateOf(false)
        private set

    var showTimeDialog2 by mutableStateOf(false)
        private set

    var showTimeDialog3 by mutableStateOf(false)
        private set

    //Gives useless userID if user is not logged in
    val userID = AuthManager.getStudentId() ?: -1

    var expanded by mutableStateOf(false)
        private set

    var showDialog1 by mutableStateOf(false)
        private set

    var showDialog2 by mutableStateOf(false)
        private set

    var showDialog3 by mutableStateOf(false)
        private set

    var showDialog4 by mutableStateOf(false)
        private set

    var showDialog5 by mutableStateOf(false)
        private set

    var showDialog6 by mutableStateOf(false)
        private set

    var checked1 by mutableStateOf(false)
        private set

    var checked2 by mutableStateOf(false)
        private set

    var checked3 by mutableStateOf(false)
        private set

    var checked4 by mutableStateOf(false)
        private set

    var checked5 by mutableStateOf(false)
        private set

    var checked6 by mutableStateOf(false)
        private set

    var checked7 by mutableStateOf(false)
        private set

    var checked8 by mutableStateOf(false)
        private set

    var checked9 by mutableStateOf(false)
        private set

    var selectedOptionText by mutableStateOf("")
        private set

    var mTime1 by mutableStateOf("00:00")
        private set

    var mTime2 by mutableStateOf("00:00")
        private set

    var mTime3 by mutableStateOf("00:00")
        private set

    var timeError by mutableStateOf(false)
        private set

    var canSave by mutableStateOf(false)
        private set

    fun updateChecked1(boolean: Boolean) {
        checked1 = boolean
    }
    fun updateChecked2(boolean: Boolean) {
        checked2 = boolean
    }
    fun updateChecked3(boolean: Boolean) {
        checked3 = boolean
    }
    fun updateChecked4(boolean: Boolean) {
        checked4 = boolean
    }
    fun updateChecked5(boolean: Boolean) {
        checked5 = boolean
    }
    fun updateChecked6(boolean: Boolean) {
        checked6 = boolean
    }
    fun updateChecked7(boolean: Boolean) {
        checked7 = boolean
    }
    fun updateChecked8(boolean: Boolean) {
        checked8 = boolean
    }
    fun updateChecked9(boolean: Boolean) {
        checked9 = boolean
    }

    fun updateTime1(string: String) {
        mTime1 = string
        showTimeDialog1 = false
    }
    fun updateTime2(string: String) {
        mTime2 = string
        showTimeDialog2 = false
    }
    fun updateTime3(string: String) {
        mTime3 = string
        showTimeDialog3 = false
    }

    fun updateSelectedOptionText(string: String) {
        selectedOptionText = string
    }

    fun toggleExpanded() {
        expanded = !expanded
    }

    fun updateExpanded(boolean: Boolean) {
        expanded = boolean
    }

    fun updateTimeError(boolean: Boolean) {
        timeError = boolean
    }


    fun updateShowTimeDialog(boolean: Boolean, showDialogNum: Int) {
        when (showDialogNum) {
            1-> showTimeDialog1 = boolean
            2-> showTimeDialog2 = boolean
            3-> showTimeDialog3 = boolean
            else -> {}
        }
    }

    fun updateShowDialog(boolean: Boolean, showDialogNum: Int) {
        when (showDialogNum) {
            1-> showDialog1 = boolean
            2-> showDialog2 = boolean
            3-> showDialog3 = boolean
            4-> showDialog4 = boolean
            5-> showDialog5 = boolean
            6-> showDialog6 = boolean
            else -> {}
        }
    }

    fun checkTimeNotEmpty() {
        timeError = mTime1 == "00:00" || mTime2 == "00:00" || mTime3 == "00:00"
    }

    fun checkCanSave() {
        canSave = !timeError
    }


    fun loadFoodIntakeFromDatabase() = viewModelScope.launch(Dispatchers.IO) {
        val foodIntake =  foodIntakeRepository.getFoodIntakeById(userID).firstOrNull()
            if (foodIntake != null) {
                checked1 = foodIntake.checked1
                checked2 = foodIntake.checked2
                checked3 = foodIntake.checked3
                checked4 = foodIntake.checked4
                checked5 = foodIntake.checked5
                checked6 = foodIntake.checked6
                checked7 = foodIntake.checked7
                checked8 = foodIntake.checked8
                checked9 = foodIntake.checked9

                selectedOptionText = foodIntake.selectedOptionText

                mTime1 = foodIntake.mTime1
                mTime2 = foodIntake.mTime2
                mTime3 = foodIntake.mTime3
            }
    }

    init {
        loadFoodIntakeFromDatabase()
    }

    fun saveFoodIntake() = viewModelScope.launch(Dispatchers.IO) {
        val foodIntake = FoodIntake(
            patientId = userID,
            checked1 = checked1,
            checked2 = checked2,
            checked3 = checked3,
            checked4 = checked4,
            checked5 = checked5,
            checked6 = checked6,
            checked7 = checked7,
            checked8 = checked8,
            checked9 = checked9,
            selectedOptionText = selectedOptionText,
            mTime1 = mTime1,
            mTime2 = mTime2,
            mTime3 = mTime3,
        )

        foodIntakeRepository.upsertFoodIntake(foodIntake)
    }

    class FoodIntakeQuestionnaireViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeQuestionnaireViewModel(context) as T
    }
}