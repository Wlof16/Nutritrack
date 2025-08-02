package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.FoodIntakeQuestionnaireViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme
import java.util.Calendar


class FoodIntakeQuestionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FoodIntakeQuestionnaireViewModel = ViewModelProvider(
                this, FoodIntakeQuestionnaireViewModel.FoodIntakeQuestionnaireViewModelFactory(this@FoodIntakeQuestionnaire)
            )[FoodIntakeQuestionnaireViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopAppBar() }) { innerPadding ->
                    Surface (modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {

                        OpaqueDivider()
                        FoodQuestionnaire(viewModel)
                    }
                }
            }
        }
    }
}


//@Preview (showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    val intent = Intent(context, LoginScreen::class.java)

    CenterAlignedTopAppBar(
        title = {
            Text(
                "Food Intake Questionnaire",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 40.dp)
            )

        },

        navigationIcon = {
            IconButton(
                onClick = {
                    AuthManager.logout()
                    context.getSharedPreferences("user_session", Context.MODE_PRIVATE).edit() {
                        remove(
                            "userID"
                        )
                    }
                    (context as? Activity)?.finishAffinity()
                    context.startActivity(intent)  },
                modifier = Modifier.padding(bottom = 40.dp)) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to Food Questioner Screen",
                )
            }
        },
        scrollBehavior = scrollBehavior,

        expandedHeight = 60.dp
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodQuestionnaire(foodIntakeViewModel : FoodIntakeQuestionnaireViewModel) {

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text("Tick all the food categories you can eat", style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
        ))

        Spacer(modifier = Modifier.height(15.dp))
        val currentContext = LocalContext.current

        val checked1 = foodIntakeViewModel.checked1
        val checked2 = foodIntakeViewModel.checked2
        val checked3 = foodIntakeViewModel.checked3
        val checked4 = foodIntakeViewModel.checked4
        val checked5 = foodIntakeViewModel.checked5
        val checked6 = foodIntakeViewModel.checked6
        val checked7 = foodIntakeViewModel.checked7
        val checked8 = foodIntakeViewModel.checked8
        val checked9 = foodIntakeViewModel.checked9


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            item { CreateCheckBox(checked1, "Fruits") { foodIntakeViewModel.updateChecked1(it)} }
            item { CreateCheckBox(checked2, "Vegetables") { foodIntakeViewModel.updateChecked2(it)} }
            item { CreateCheckBox(checked3, "Grains") { foodIntakeViewModel.updateChecked3(it)} }
            item { CreateCheckBox(checked4, "Red Meat") { foodIntakeViewModel.updateChecked4(it)} }
            item { CreateCheckBox(checked5, "Seafood") { foodIntakeViewModel.updateChecked5(it)} }
            item { CreateCheckBox(checked6, "Poultry") { foodIntakeViewModel.updateChecked6(it)} }
            item { CreateCheckBox(checked7, "Fish") { foodIntakeViewModel.updateChecked7(it)} }
            item { CreateCheckBox(checked8, "Eggs") { foodIntakeViewModel.updateChecked8(it)} }
            item { CreateCheckBox(checked9, "Nuts/Seeds") { foodIntakeViewModel.updateChecked9(it)} }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Your Persona", style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "People can be broadly classified into 6 different types based on their eating preferences. " +
                    "Click on each button below to find out the different types and select the type that best fits you!",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            CreatePersonaModal(
                R.drawable.persona_1,
                "Health Devotee",
                "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.", foodIntakeViewModel.showDialog1, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 1) }
            )
            CreatePersonaModal(
                R.drawable.persona_2,
                "Mindful Eater",
                "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",foodIntakeViewModel.showDialog2, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 2) }
            )
            CreatePersonaModal(
                R.drawable.persona_3,
                "Wellness Striver",
                "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.", foodIntakeViewModel.showDialog3, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 3) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            CreatePersonaModal(
                R.drawable.persona_4,
                "Balance Seeker",
                "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                foodIntakeViewModel.showDialog4, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 4) }
            )
            CreatePersonaModal(
                R.drawable.persona_5,
                "Health Procrastinator",
                "\tI’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                foodIntakeViewModel.showDialog5, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 5) }
            )
            CreatePersonaModal(
                R.drawable.persona_6,
                "Food Carefree",
                "\tI’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
                foodIntakeViewModel.showDialog6, { b: Boolean -> foodIntakeViewModel.updateShowDialog(b, 6) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Which persona best fits you?", style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )

        val expanded = foodIntakeViewModel.expanded
        val selectedOptionText = foodIntakeViewModel.selectedOptionText
        val options = listOf<String>("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker", "Health Procrastinator", "Food Carefree")

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                foodIntakeViewModel.toggleExpanded()
            }
        ) {

            OutlinedTextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Select Option ", fontSize = 13.sp) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    foodIntakeViewModel.updateExpanded(false)
                }) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            foodIntakeViewModel.updateSelectedOptionText(selectionOption)
                            foodIntakeViewModel.updateExpanded(false)
                        }
                    )
                }
            }
        }

        val mTime1 = foodIntakeViewModel.mTime1
        val mTimePickerDialog1 = TimePickerFun(onTimeSelected = { newTime -> foodIntakeViewModel.updateTime1(newTime) }, {foodIntakeViewModel.updateShowTimeDialog(false, 1)})

        val mTime2 = foodIntakeViewModel.mTime2
        val mTimePickerDialog2 = TimePickerFun(onTimeSelected = { newTime -> foodIntakeViewModel.updateTime2(newTime) }) {
            foodIntakeViewModel.updateShowTimeDialog(
                false,
                2
            )
        }

        val mTime3 = foodIntakeViewModel.mTime3
        val mTimePickerDialog3 = TimePickerFun(onTimeSelected = { newTime -> foodIntakeViewModel.updateTime3(newTime) }) {
            foodIntakeViewModel.updateShowTimeDialog(
                false,
                3
            )
        }

        val timeError = foodIntakeViewModel.timeError

        val canSave = foodIntakeViewModel.canSave

        val showTimeDialog1 = foodIntakeViewModel.showTimeDialog1
        val showTimeDialog2 = foodIntakeViewModel.showTimeDialog2
        val showTimeDialog3 = foodIntakeViewModel.showTimeDialog3

        Spacer(Modifier.height(10.dp))

        Text(
            "Timings", style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )

        Spacer(Modifier.height(8.dp))

        CreateTiming(
            mTime1,
            "What time of day approx. do you normally eat your biggest meal?",
            { foodIntakeViewModel.updateShowTimeDialog(true, 1) }
        )

        CreateTiming(
            mTime2,
            "What time of day approx. do you go to sleep at night?",
            {foodIntakeViewModel.updateShowTimeDialog(true, 2)}
        )

        CreateTiming(
            mTime3,
            "What time of day approx. do you wake up in the morning?",
            {foodIntakeViewModel.updateShowTimeDialog(true, 3)}
        )

        Spacer(Modifier.height(15.dp))

        Button(
            onClick = {
                foodIntakeViewModel.checkTimeNotEmpty()
                foodIntakeViewModel.checkCanSave()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = "Clock Icon",
                modifier = Modifier.size(40.dp).padding(end = 10.dp)
            )

            Text("Save", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(end = 15.dp), fontWeight = FontWeight.SemiBold)
        }
        if (showTimeDialog1) {
            mTimePickerDialog1.show()
        }

        if (showTimeDialog2) {
            mTimePickerDialog2.show()
        }

        if (showTimeDialog3) {
            mTimePickerDialog3.show()
        }

        if (canSave) {
            foodIntakeViewModel.saveFoodIntake()
            val intent = Intent(currentContext, HomeScreen::class.java)
            (currentContext as? Activity)?.finish()
            currentContext.startActivity(intent)
        }
        if (timeError) {
            Toast.makeText(currentContext, "Ensure a time is picked for each timing", Toast.LENGTH_SHORT).show()
            foodIntakeViewModel.updateTimeError(false)
        }
    }
}


@Composable
fun OpaqueDivider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Black.copy(alpha = 0.3f)
    )
}

@Composable
fun CreateCheckBox(checkBox: Boolean, text: String, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkBox,
            onCheckedChange = onCheckedChange,
        )

        Text(text, fontWeight = FontWeight(650), style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun CreatePersonaModal(
    imageID: Int,
    personaName: String,
    detailedDesc: String,
    showDialog: Boolean,
    updateShowDialog: (Boolean) -> Unit,
) {

    Button(onClick = { updateShowDialog(true) },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(10.dp)

    ) {
        Text(personaName, fontSize = 10.sp)
    }

    if (showDialog) {
        AlertDialog(
            text = {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                Image(
                    painter = painterResource(id = imageID),
                    contentDescription = "Persona Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                )

                Spacer(Modifier.height(10.dp))
                Text(
                    personaName,
                    style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(10.dp))
                Text(
                    detailedDesc,
                    textAlign = TextAlign.Center)
            }},
            onDismissRequest = { updateShowDialog(false)},
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ) {
                    Button(
                        onClick = { updateShowDialog(false)},
                        shape = RoundedCornerShape(10.dp),
                ) {
                Text("Dismiss")
            } }
            }
        )
    }
}

@Composable
fun TimePickerFun(onTimeSelected: (String) -> Unit, function: () -> Unit): TimePickerDialog {
    val mContext = LocalContext.current

    val mCalender = Calendar.getInstance()

    val mHour = mCalender.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalender.get(Calendar.MINUTE)

    mCalender.time = Calendar.getInstance().time

    return TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            val selectedTime = String.format("%02d:%02d", mHour, mMinute)
            onTimeSelected(selectedTime)
        }, mHour, mMinute, false
    ).apply {
        setOnCancelListener {
            function()
        }
    }
}

@Composable
fun CreateTiming(
    mTime: String,
    timingText: String,
    updateShowTimeDialog: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(0.7f)
            .padding(end = 10.dp)) {

            Text(
                text = timingText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Button(
            onClick = { updateShowTimeDialog() },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(start = 15.dp ,end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.3f))
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.accesstime),
                    contentDescription = "Clock Icon",
                    tint = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(text = mTime, color = Color.Black.copy(alpha = 0.3f))
            }
        }
    }
}
