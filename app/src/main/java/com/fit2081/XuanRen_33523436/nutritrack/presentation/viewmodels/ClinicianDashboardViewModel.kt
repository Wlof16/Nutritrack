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
import com.fit2081.XuanRen_33523436.nutritrack.GenAIService
import com.fit2081.XuanRen_33523436.nutritrack.UiState
import com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips.NutriCoachTip
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.AverageHEIFA
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClinicianDashboardViewModel(context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    private val genAIService = GenAIService()

    private var dataSetResultString = ""

    private val _insightList = MutableStateFlow<List<String>>(emptyList())
    val insightList: StateFlow<List<String>> = _insightList.asStateFlow()

    private val _averageHEIFAMale = MutableStateFlow<String>("N/A")
    val averageHEIFAMale: StateFlow<String> = _averageHEIFAMale.asStateFlow()

    private val _averageHEIFAFemale = MutableStateFlow<String>("N/A")
    val averageHEIFAFemale: StateFlow<String> = _averageHEIFAFemale.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadAverageHEIFAData() = viewModelScope.launch(Dispatchers.IO) {

        patientRepository.getAverageHEIFAScore().collect { averageHEIFAList ->
            averageHEIFAList.forEach { averageHEIFA ->
                checkAndAssignAverageHEIFA(averageHEIFA)
            }
        }
    }

    fun findDataPatterns() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = UiState.Loading
        val prompt = """
            You are a meticulous nutrition data analyst.
            
            Carefully analyze the anonymized dataset below, which contains HEIFA nutrition scores across various categories for a sample population.
            
            **Your task:**
            - Identify **3 unique, varied, and meaningful general patterns or trends** present across the dataset.
            - Avoid personalizing insights for any individual.
            - Focus on general statistical patterns, correlations, or notable disparities.
            
            **Output format:**
            For each insight, provide:
            - A **concise, bolded title** at the top.
            - A clear, well-written explanation paragraph below.
            
            **Do not include labels like 'Insight Title' or 'Description' in your output.**  
            Just give the title followed by the explanation.
            
            Separate each insight using this marker string exactly:
            
            ===INSIGHT_SEPARATOR===
            
            **Dataset:**
            $dataSetResultString
            
            IMPORTANT: Only identify general patterns about the overall group.
            """.trimIndent()


        val result = genAIService.sendPrompt(prompt)

        result.onSuccess { value ->
            _uiState.value = UiState.Success("")
            val insights = value.split("===INSIGHT_SEPARATOR===")
            _insightList.value = insights.map { it.trim() }
        }
        result.onFailure { _uiState.value = UiState.Error("Message could not be generated")  }
    }

    fun checkAndAssignAverageHEIFA(averageHEIFA: AverageHEIFA) {
        if (averageHEIFA.userSex == "Male") {
            _averageHEIFAMale.value = averageHEIFA.averageHEIFA.toString()
        }
        else if (averageHEIFA.userSex == "Female") {
            _averageHEIFAFemale.value = averageHEIFA.averageHEIFA.toString()
        }
    }

    fun loadAllPatients() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.allPatients.collect { patientList ->
            var index = 1
            patientList.forEach { patient ->
                dataSetResultString += """
                    
                    [User $index]
                    Gender: ${patient.userSex},
                    vegetablesHEIFAscore: ${patient.vegetablesHEIFAscore},
                    fruitHEIFAscore: ${patient.fruitHEIFAscore},
                    grainsandcerealsHEIFAscore: ${patient.grainsandcerealsHEIFAscore},
                    wholegrainsHEIFAscore: ${patient.wholegrainsHEIFAscore},
                    meatandalternativesHEIFAscore: ${patient.meatandalternativesHEIFAscore},
                    dairyandalternativesHEIFAscore: ${patient.dairyandalternativesHEIFAscore},
                    waterHEIFAscore: ${patient.waterHEIFAscore},
                    unsaturatedFatHEIFAscore: ${patient.unsaturatedFatHEIFAscore},
                    saturatedFatHEIFAscore: ${patient.saturatedFatHEIFAscore},
                    sodiumHEIFAscore: ${patient.sodiumHEIFAscore},
                    sugarHEIFAscore: ${patient.sugarHEIFAscore},
                    alcoholHEIFAscore: ${patient.alcoholHEIFAscore},
                    discretionaryHEIFAscore: ${patient.discretionaryHEIFAscore},
                    hEIFAtotalscore: ${patient.hEIFAtotalscore},
                    fruitservesize: ${patient.fruitservesize},
                    fruitvariationsscore: ${patient.fruitvariationsscore},
                    ...
                    
                """.trimIndent()

                index += 1
            }
        }
    }
    init {
        loadAllPatients()
        loadAverageHEIFAData()
    }

    class ClinicianDashboardViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ClinicianDashboardViewModel(context) as T
    }
}