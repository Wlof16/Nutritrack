package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.style.TextOverflow
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.UiState
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.NutriCoachScreenViewModel

class NutriCoachScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NutriCoachScreenViewModel = ViewModelProvider(
                this, NutriCoachScreenViewModel.NutriCoachScreenViewModelFactory(this@NutriCoachScreen)
            )[NutriCoachScreenViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar("NutriCoach")
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NutriCoach(viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun NutriCoach(nutriCoachScreenViewModel: NutriCoachScreenViewModel) {
    val context = LocalContext.current
    val fruitName = nutriCoachScreenViewModel.fruitName
    val fruitFamilyName = nutriCoachScreenViewModel.fruitFamilyName
    val fruitCalories = nutriCoachScreenViewModel.calories
    val fruitFat = nutriCoachScreenViewModel.fat
    val fruitSugar = nutriCoachScreenViewModel.sugar
    val fruitCarbohydrates = nutriCoachScreenViewModel.carbohydrates
    val fruitProtein = nutriCoachScreenViewModel.protein
    val showAllTips = nutriCoachScreenViewModel.showAllTips
    val isFruitScoreOptimal = nutriCoachScreenViewModel.isFruitScoreOptimal.collectAsState(initial = false)
    val uiState by nutriCoachScreenViewModel.uiState.collectAsState()
    val messages by nutriCoachScreenViewModel.messages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("NutriCoach", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(15.dp))

        if (isFruitScoreOptimal.value == false) {
            Text("Fruit Name", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500)))

            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = fruitName,
                    onValueChange = {
                        nutriCoachScreenViewModel.updateFruitName(it)
                    },
                    shape = RoundedCornerShape(25.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            nutriCoachScreenViewModel.retrieveFruit()
                        }
                    ),
                    placeholder = {
                        Text(
                            text = "fruit name",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(
                                ), fontWeight = FontWeight(900)
                            )
                        )
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight(900)
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(25.dp)
                        ),
                )

                Spacer(modifier = Modifier.width(30.dp).height(45.dp))

                Button(
                    onClick = { nutriCoachScreenViewModel.retrieveFruit() },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.search_outlined),
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("Details")

                        Spacer(Modifier.width(10.dp))
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            CreateFruitRow("family", fruitFamilyName)
            Spacer(modifier = Modifier.height(10.dp))
            CreateFruitRow("calories", fruitCalories)
            Spacer(modifier = Modifier.height(10.dp))
            CreateFruitRow("fat", fruitFat)
            Spacer(modifier = Modifier.height(10.dp))
            CreateFruitRow("sugar", fruitSugar)
            Spacer(modifier = Modifier.height(10.dp))
            CreateFruitRow("carbohydrates", fruitCarbohydrates)
            Spacer(modifier = Modifier.height(10.dp))
            CreateFruitRow("protein", fruitProtein)
        }
        else {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://picsum.photos/200?random=${(0..100000).random()}", //Should be random so its fine for it to not be held within ViewModel
                    contentDescription = "Random Image When Fruit Score is Optimal",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        if (nutriCoachScreenViewModel.fruitError) {
            Toast.makeText(context, "Invalid Fruit Name", Toast.LENGTH_SHORT).show()
            nutriCoachScreenViewModel.updateFruitError(false)
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { nutriCoachScreenViewModel.generateAIMotivationMessage() },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.speech_bubble),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(10.dp))
                Text("Motivational Message (AI)")

                Spacer(Modifier.width(10.dp))

            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            //is this breaking MVVM? I just followed the Lab
            var result = ""
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                var textColor = MaterialTheme.colorScheme.onSurface

                if (uiState is UiState.Error) {
                    textColor = MaterialTheme.colorScheme.error
                    result = (uiState as UiState.Error).errorMessage
                } else if (uiState is UiState.Success) {
                    textColor = MaterialTheme.colorScheme.onSurface
                    result = (uiState as UiState.Success).outputText
                }
                val scrollState = rememberScrollState()
                Text(
                    text = result,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                )
            }
            Button(
                onClick = { nutriCoachScreenViewModel.updateShowAllTips(true)  },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.lightbulb),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Show All Tips")

                    Spacer(Modifier.width(10.dp))
                }
            }
        }
        if (showAllTips) {
            AlertDialog(
                text = {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().heightIn(min = 100.dp, max = 300.dp),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(messages) { message ->
                            CreateMessage(message)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                onDismissRequest = { nutriCoachScreenViewModel.updateShowAllTips(false) },
                confirmButton = {
                    Button(
                        onClick = { nutriCoachScreenViewModel.updateShowAllTips(false) },
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Done")
                    }
                }
            )
        }
    }
}


@Composable
fun CreateFruitRow(fruitStatsCategory: String, fruitStats: String) {
    Row(modifier = Modifier
        .border(width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .height(30.dp)
        .background(
            color = Color.LightGray.copy(alpha = 0.2f),
            shape = RoundedCornerShape(5.dp)),
        verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(5.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.3f)
            .padding(end = 10.dp)) {

            Text(
                text = fruitStatsCategory,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500)),
            )
        }
        Spacer(modifier = Modifier.width(40.dp))
        Text(": $fruitStats", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500)))
    }
}





@Composable
fun CreateMessage(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}