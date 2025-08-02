package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.activities.SettingsScreen
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.InsightsViewModel
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.SettingsScreenViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme
import java.io.BufferedReader
import java.io.InputStreamReader

class Insights : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userID = intent.getStringExtra("userID") ?: ""
            val viewModel: InsightsViewModel = ViewModelProvider(
                this, InsightsViewModel.InsightsViewModelFactory(this@Insights)
            )[InsightsViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar("Insights") }
                ) { innerPadding ->
                    Surface (
                        modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    ) {
                        InsightsScreen(viewModel)
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(insightsViewModel: InsightsViewModel){
    val context = LocalContext.current

    val vegetablesHEIFAscore = insightsViewModel.foodInsightMap["vegetablesHEIFAscore"]?.collectAsState()
    val fruitHEIFAscore = insightsViewModel.foodInsightMap["fruitHEIFAscore"]?.collectAsState()
    val grainsandcerealsHEIFAscore = insightsViewModel.foodInsightMap["grainsandcerealsHEIFAscore"]?.collectAsState()
    val wholegrainsHEIFAscore = insightsViewModel.foodInsightMap["wholegrainsHEIFAscore"]?.collectAsState()
    val meatandalternativesHEIFAscore = insightsViewModel.foodInsightMap["meatandalternativesHEIFAscore"]?.collectAsState()
    val dairyandalternativesHEIFAscore = insightsViewModel.foodInsightMap["dairyandalternativesHEIFAscore"]?.collectAsState()
    val waterHEIFAscore = insightsViewModel.foodInsightMap["waterHEIFAscore"]?.collectAsState()
    val unsaturatedFatHEIFAscore = insightsViewModel.foodInsightMap["unsaturatedFatHEIFAscore"]?.collectAsState()
    val saturatedFatHEIFAscore = insightsViewModel.foodInsightMap["saturatedFatHEIFAscore"]?.collectAsState()
    val sodiumHEIFAscore = insightsViewModel.foodInsightMap["sodiumHEIFAscore"]?.collectAsState()
    val sugarHEIFAscore = insightsViewModel.foodInsightMap["sugarHEIFAscore"]?.collectAsState()
    val alcoholHEIFAscore = insightsViewModel.foodInsightMap["alcoholHEIFAscore"]?.collectAsState()
    val discretionaryHEIFAscore = insightsViewModel.foodInsightMap["discretionaryHEIFAscore"]?.collectAsState()
    val hEIFAtotalscore = insightsViewModel.foodInsightMap["hEIFAtotalscore"]?.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text("Insights: Food Score", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier =  Modifier.height(20.dp))


        /* Progress bars insights */

        CreateProgressBar("Vegetables", vegetablesHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Fruits", fruitHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Grains & Cereals", grainsandcerealsHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Whole Grains", wholegrainsHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Meat & Alternatives", meatandalternativesHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Dairy", dairyandalternativesHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Water", waterHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Unsaturated Fats", unsaturatedFatHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Saturated Fats", saturatedFatHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Sodium", sodiumHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Sugar", sugarHEIFAscore?.value ?: "-1", "10")
        CreateProgressBar("Alcohol", alcoholHEIFAscore?.value ?: "-1", "5")
        CreateProgressBar("Discretionary Foods", discretionaryHEIFAscore?.value ?: "-1", "10")

        /* Progress Bar for Total Food Quality Score*/

        Spacer(modifier = Modifier.height(20.dp))
        Text("Total Food Quality Score", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold), modifier = Modifier.align(Alignment.Start).padding(start = 10.dp))

        Row (
            modifier = Modifier.fillMaxWidth().height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Slider(
                modifier = Modifier.fillMaxWidth(0.75f),
                value = hEIFAtotalscore?.value?.toFloat() ?: (-1).toFloat(),
                onValueChange = {},
                valueRange = 0f..100.toFloat(),
                steps = 5,
                thumb = { // Custom thumb to keep size unchanged
                    SliderDefaults.Thumb(
                        interactionSource = remember { MutableInteractionSource() },
                        modifier = Modifier.size(20.dp)
                    )
                }
            )


            Spacer(modifier = Modifier.width(20.dp))
            Text(
                hEIFAtotalscore?.value + "/100",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        /* Share and improve diet buttons */

        Button(onClick = {
            val shareIntent = Intent(ACTION_SEND)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! I got a HEIFA Total Food Quality Score of: " + hEIFAtotalscore?.value)

            context.startActivity((Intent.createChooser(shareIntent, "Share text via")))

        }, shape = RoundedCornerShape(10.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.share),
                    contentDescription = "Share Icon",
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(10.dp))

                Text("Share with someone")

                Spacer(Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(onClick = {
            val intent = Intent(context, NutriCoachScreen::class.java)
            (context as? Activity)?.finish()
            context.startActivity(intent)
        }, shape = RoundedCornerShape(10.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.rocket),
                    contentDescription = "Improve Diet Rocket",
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(10.dp))

                Text("Improve my diet!")

                Spacer(Modifier.width(10.dp))
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProgressBar(label: String, progress: String, max: String) {
    Row (
        modifier = Modifier.fillMaxWidth().height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box (modifier = Modifier.width(100.dp)
        ) {
            Text(label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight (850), fontSize = 11.sp))
        }


        Slider(
            modifier = Modifier.width(200.dp),
            value = progress.toFloat(),
            onValueChange = {},
            valueRange = 0f..max.toFloat(),
            steps = 5,
            thumb = { // Custom thumb to keep size unchanged
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.size(20.dp),
                )
            }
        )

        Spacer(modifier = Modifier.width(20.dp))
        Text("$progress/$max", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))

    }
    Spacer(modifier = Modifier.height(10.dp))
}

