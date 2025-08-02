package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.HomeScreenViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme


class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HomeScreenViewModel = ViewModelProvider(
                this, HomeScreenViewModel.HomeScreenViewModelFactory(this@HomeScreen)
            )[HomeScreenViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { MyBottomAppBar("Home") }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Home(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Home(homeScreenViewModel: HomeScreenViewModel) {
    val context = LocalContext.current
    val foodScore by homeScreenViewModel.foodScore.collectAsState()
    val userName by homeScreenViewModel.userName.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Hello,",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(userName, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(10.dp))

        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            ) {
                Text("You've already filled in your Food Intake Questionnaire, but you can change details here",
                    style = MaterialTheme.typography.bodySmall)

            }

            Button(
                onClick = {
                    val intent = Intent(context, FoodIntakeQuestionnaire::class.java)
                    context.startActivity(intent) },

                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.edit),
                        contentDescription = "Edit Icon",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Edit")

                    Spacer(Modifier.width(10.dp))

                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.homescreen),
            contentDescription = "homescreen logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text("My Score", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))

            Box(modifier = Modifier.clickable {
                val intent = Intent(context, Insights::class.java)
                (context as? Activity)?.finish()
                context.startActivity(intent)
            }
            ) {
                Row (verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("See all scores", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)))
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Right arrow Icon",
                        modifier = Modifier.size(13.dp),
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box() {
                Row (verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.5f))
                            .size(40.dp),
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrow_up),
                            contentDescription = "Right arrow Icon",
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Your Food Quality Score", style = MaterialTheme.typography.bodyLarge)
                }
            }


            Text("$foodScore/100", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight (1000)), color = MaterialTheme.colorScheme.secondary)

        }
        Spacer(modifier = Modifier.height(70.dp))

        OpaqueDivider()

        Spacer(modifier = Modifier.height(15.dp))

        Text("What is Food Quality Score?", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Your Food Quality Score provides a snapshot of how well you eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight =  FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight =  FontWeight.SemiBold)
        )
    }
}

@Composable
fun MyBottomAppBar(currentScreen: String) {
    val context = LocalContext.current

    BottomAppBar(
        modifier = Modifier.height(100.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = painterResource(id = R.drawable.home),
                label = "Home",
                isSelected = currentScreen == "Home"
            ) {
                if (currentScreen != "Home") {
                    (context as? Activity)?.finish()
                    context.startActivity(Intent(context, HomeScreen::class.java))
                }
            }

            BottomNavItem(
                icon = painterResource(id = R.drawable.insights),
                label = "Insights",
                isSelected = currentScreen == "Insights"
            ) {
                if (currentScreen != "Insights") {
                    (context as? Activity)?.finish()
                    context.startActivity(Intent(context, Insights::class.java))
                }
            }

            BottomNavItem(
                icon = painterResource(id = R.drawable.nutricoach),
                label = "NutriCoach",
                isSelected = currentScreen == "NutriCoach"
            ) {
                if (currentScreen != "NutriCoach") {
                    (context as? Activity)?.finish()
                    context.startActivity(Intent(context, NutriCoachScreen::class.java))
                }
            }

            BottomNavItem(
                icon = painterResource(id = R.drawable.setting),
                label = "Settings",
                isSelected = currentScreen == "Settings"
            ) {
                if (currentScreen != "Settings") {
                    (context as? Activity)?.finish()
                    context.startActivity(Intent(context, SettingsScreen::class.java))
                }
            }
        }
    }
}


@Composable
fun BottomNavItem(
    icon: Painter,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = icon,
                contentDescription = "Go $label",
                tint = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
            Text(
                label,
                color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(900))
            )
        }
    }
}




