package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.UiState
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.ClinicianDashboardViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme

class ClinicianDashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ClinicianDashboardViewModel = ViewModelProvider(
                this, ClinicianDashboardViewModel.ClinicianDashboardViewModelFactory(this@ClinicianDashboard)
            )[ClinicianDashboardViewModel::class.java]
            NutritrackTheme(viewModel.isDark) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar("Settings")
                    }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ClinicianDashboardScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicianDashboardScreen(clinicianDashboardViewModel: ClinicianDashboardViewModel) {
    val context = LocalContext.current
    val averageHEIFAMale by clinicianDashboardViewModel.averageHEIFAMale.collectAsState()
    val averageHEIFAFemale by clinicianDashboardViewModel.averageHEIFAFemale.collectAsState()
    val uiState by clinicianDashboardViewModel.uiState.collectAsState()
    val insightList by clinicianDashboardViewModel.insightList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Clinician Dashboard", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(30.dp))
        CreateClinicRow("Average HEIFA (MALE)", averageHEIFAMale)
        Spacer(modifier = Modifier.height(10.dp))
        CreateClinicRow("Average HEIFA (FEMALE)", averageHEIFAFemale)
        Spacer(modifier = Modifier.height(30.dp))
        OpaqueDivider()

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                clinicianDashboardViewModel.findDataPatterns()
            },
            modifier = Modifier.height(55.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_outlined),
                contentDescription = "Login Icon",
                modifier = Modifier.size(40.dp).padding(end = 10.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text("Find Data Pattern", style = MaterialTheme.typography.bodyLarge)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f), contentAlignment = Alignment.Center) {
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else {
                if (uiState is UiState.Success) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().heightIn(min = 100.dp, max = 300.dp),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(insightList) { message ->
                            CreateMessage(message)
                        }
                    }
                }
                else if (uiState is UiState.Error) {
                    Text((uiState as UiState.Error).errorMessage)
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    val intent = Intent(context, SettingsScreen::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(55.dp)
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Done", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun CreateClinicRow(categoryName: String, value: String) {
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
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(end = 10.dp)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(900)),
            )
        }
        Text(":    $value", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500)))
    }
}