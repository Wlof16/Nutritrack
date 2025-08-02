package com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels

import android.content.Context
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsightsViewModel (context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    private val _foodInsightMap = mutableMapOf<String, MutableStateFlow<String>>()

    val foodInsightMap: Map<String, StateFlow<String>> get() = _foodInsightMap
    private val _user = MutableStateFlow<Patient?>(null)

    val user: StateFlow<Patient?> = _user.asStateFlow()

    val userID = AuthManager.getStudentId() ?: -1

    fun loadDataIntoInsights() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.getPatientById(userID).collect { patient ->
                _foodInsightMap["vegetablesHEIFAscore"]?.value = patient.vegetablesHEIFAscore.toString()
                _foodInsightMap["fruitHEIFAscore"]?.value = patient.fruitHEIFAscore.toString()
                _foodInsightMap["grainsandcerealsHEIFAscore"]?.value = patient.grainsandcerealsHEIFAscore.toString()
                _foodInsightMap["wholegrainsHEIFAscore"]?.value = patient.wholegrainsHEIFAscore.toString()
                _foodInsightMap["meatandalternativesHEIFAscore"]?.value = patient.meatandalternativesHEIFAscore.toString()
                _foodInsightMap["dairyandalternativesHEIFAscore"]?.value = patient.dairyandalternativesHEIFAscore.toString()
                _foodInsightMap["waterHEIFAscore"]?.value = patient.waterHEIFAscore.toString()
                _foodInsightMap["unsaturatedFatHEIFAscore"]?.value = patient.unsaturatedFatHEIFAscore.toString()
                _foodInsightMap["saturatedFatHEIFAscore"]?.value = patient.saturatedFatHEIFAscore.toString()
                _foodInsightMap["sodiumHEIFAscore"]?.value = patient.sodiumHEIFAscore.toString()
                _foodInsightMap["sugarHEIFAscore"]?.value = patient.sugarHEIFAscore.toString()
                _foodInsightMap["alcoholHEIFAscore"]?.value = patient.alcoholHEIFAscore.toString()
                _foodInsightMap["discretionaryHEIFAscore"]?.value = patient.discretionaryHEIFAscore.toString()
                _foodInsightMap["hEIFAtotalscore"]?.value = patient.hEIFAtotalscore.toString()
        }
    }
    init {
        listOf("vegetablesHEIFAscore", "fruitHEIFAscore", "grainsandcerealsHEIFAscore", "wholegrainsHEIFAscore",
            "meatandalternativesHEIFAscore", "dairyandalternativesHEIFAscore", "waterHEIFAscore",
            "unsaturatedFatHEIFAscore", "saturatedFatHEIFAscore", "sodiumHEIFAscore", "sugarHEIFAscore",
            "alcoholHEIFAscore", "discretionaryHEIFAscore", "hEIFAtotalscore"
        ).forEach { key -> _foodInsightMap[key] = MutableStateFlow("-1") }
        loadDataIntoInsights()
    }

    class InsightsViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            InsightsViewModel(context) as T
    }
}