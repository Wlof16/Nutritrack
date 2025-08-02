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
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntake
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntakeRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.fruit.FruityRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips.NutriCoachTip
import com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips.NutriCoachTipsRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientsRepository
import com.fit2081.XuanRen_33523436.nutritrack.data.theme.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NutriCoachScreenViewModel(context: Context): ViewModel() {
    private val patientRepository =
        PatientsRepository(context)

    private val foodIntakeRepository =
        FoodIntakeRepository(context)

    private val nutriCoachTipRepository =
        NutriCoachTipsRepository(context)

    private val fruityRepository =
        FruityRepository()

    private val themeRepository = ThemeRepository(context)

    var isDark by mutableStateOf(themeRepository.getDarkMode())
        private set

    private val genAIService = GenAIService()

    val userID = AuthManager.getStudentId() ?: -1

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private var genAIUserInfo: String = ""
    private var genAIFoodIntakeInfo: String = ""

    var fruitName by mutableStateOf("")
        private set

    var fruitFamilyName by mutableStateOf("N/A")
        private set

    var calories by mutableStateOf("N/A")
        private set

    var fat by mutableStateOf("N/A")
        private set

    var sugar by mutableStateOf("N/A")
        private set

    var carbohydrates by mutableStateOf("N/A")
        private set

    var protein by mutableStateOf("N/A")
        private set

    var fruitError by mutableStateOf(false)
        private set

    var showAllTips by mutableStateOf(false)
        private set

    private val _isFruitScoreOptimal = MutableStateFlow<Boolean>(false)
    val isFruitScoreOptimal: StateFlow<Boolean?> = _isFruitScoreOptimal.asStateFlow()

    fun updateFruitError(boolean: Boolean) {
        fruitError = boolean
    }

    fun updateFruitName(string: String) {
        fruitName = string
    }

    fun updateShowAllTips(boolean: Boolean) {
        showAllTips = boolean
    }

    fun retrieveFruit() = viewModelScope.launch(Dispatchers.IO) {
        val fruitResponse = fruityRepository.getFruit(fruitName)
        if (fruitResponse == null) {
            fruitError = true
            resetFruitStats()
        }
        else {
            fruitFamilyName = fruitResponse.family
            calories = fruitResponse.nutritions.calories.toString()
            fat = fruitResponse.nutritions.calories.toString()
            calories = fruitResponse.nutritions.fat.toString()
            sugar = fruitResponse.nutritions.sugar.toString()
            carbohydrates = fruitResponse.nutritions.carbohydrates.toString()
            protein = fruitResponse.nutritions.protein.toString()
        }
    }

    fun resetFruitStats() {
        fruitFamilyName = "N/A"
        calories = "N/A"
        fat = "N/A"
        calories = "N/A"
        sugar = "N/A"
        carbohydrates = "N/A"
        protein = "N/A"
    }

    fun loadFoodQuizData() = viewModelScope.launch(Dispatchers.IO) {
        foodIntakeRepository.getFoodIntakeById(userID).collect { foodIntake ->
            genAIFoodIntakeInfo = getUserFoodIntakeInfo(foodIntake)
        }
    }

    fun loadUserDataForNutriCoach() = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.getPatientById(userID).collect { patient ->
             _isFruitScoreOptimal.value = patient.fruitservesize >= 2 && patient.fruitvariationsscore >= 2
            genAIUserInfo = getUserHEIFAInfo(patient)
        }
    }

    fun generateAIMotivationMessage() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = UiState.Loading
        val tones = listOf("light-hearted", "poetic", "scientific", "uplifting", "friendly")
        val selectedTone = tones.random()
        val prompt = """
            You are a creative and empathetic nutrition coach. 
            In a $selectedTone tone, generate a **unique, varied, and encouraging message** to help someone improve their fruit intake. 
            Each time this is requested, the message should be phrased differently — it can be a quote, a humorous statement, a practical tip, or a motivational thought.
            
            Personalise the message based on the following user information:
            $genAIUserInfo
            
            And their Food Questionnaire results:
            $genAIFoodIntakeInfo
            
            Focus on making each response feel fresh, human, and supportive. (add a couple emojis to make it pop!)
            IMPORTANT: Keep the message under 50 words
            """.trimIndent()

        val result = genAIService.sendPrompt(prompt)

        result.onSuccess { value ->
            _uiState.value = UiState.Success(value)
            //Also save to db
            nutriCoachTipRepository.insert(NutriCoachTip(patientId = userID, tipMessage = value))
        }
        result.onFailure { _uiState.value = UiState.Error("Message could not be generated")  }
    }

    fun getUserFoodIntakeInfo(foodIntake: FoodIntake?): String {
        var result = ""
        val prefix = "Can consume: "
        if (foodIntake != null) {
            result += prefix + foodIntake.checked1 + "," +
                    prefix + foodIntake.checked2 + "," +
                    prefix + foodIntake.checked3 + "," +
                    prefix + foodIntake.checked4 + "," +
                    prefix + foodIntake.checked5 + "," +
                    prefix + foodIntake.checked6 + "," +
                    prefix + foodIntake.checked7 + "," +
                    prefix + foodIntake.checked8 + "," +
                    prefix + foodIntake.checked9 + "," +
                    "Persona: " + foodIntake.selectedOptionText + "," +
                    "And each day they: " + "," +
                    "Eats their biggest meal at approximately: " + foodIntake.mTime1 + "," +
                    "Sleeps at approximately: " + foodIntake.mTime2 + "," +
                    "Wakes up in the morning at approximately: " + foodIntake.mTime3
        }
        return result
    }

    fun getUserHEIFAInfo(patient : Patient): String {
        var result = ""
            result +=
                "vegetablesHEIFAscore = ${patient.vegetablesHEIFAscore}," +
                "fruitHEIFAscore = ${patient.sodiumHEIFAscore}," +
                "grainsandcerealsHEIFAscore = ${patient.grainsandcerealsHEIFAscore}," +
                "wholegrainsHEIFAscore = ${patient.wholegrainsHEIFAscore}\n" +
                "meatandalternativesHEIFAscore = ${patient.meatandalternativesHEIFAscore}," +
                "dairyandalternativesHEIFAscore = ${patient.dairyandalternativesHEIFAscore}," +
                "unsaturatedFatHEIFAscore = ${patient.unsaturatedFatHEIFAscore}," +
                "saturatedFatHEIFAscore = ${patient.saturatedFatHEIFAscore}," +
                "sodiumHEIFAscore = ${patient.sodiumHEIFAscore}," +
                "sugarHEIFAscore = ${patient.sugarHEIFAscore}," +
                "alcoholHEIFAscore = ${patient.alcoholHEIFAscore}," +
                "discretionaryHEIFAscore = ${patient.discretionaryHEIFAscore}," +
        return result
    }

    fun loadNutriCoachTipData() = viewModelScope.launch(Dispatchers.IO) {
        nutriCoachTipRepository.getAllTipsByID(userID).collect { messages ->
            _messages.value = messages

        }
    }

    init {
        loadUserDataForNutriCoach()
        loadFoodQuizData()
        loadNutriCoachTipData()
    }

    class NutriCoachScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachScreenViewModel(context) as T
    }

}