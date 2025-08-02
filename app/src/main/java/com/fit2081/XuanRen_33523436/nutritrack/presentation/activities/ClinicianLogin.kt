package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.ClinicianLoginViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme

class ClinicianLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ClinicianLoginViewModel = ViewModelProvider(
                this, ClinicianLoginViewModel.ClinicianLoginViewModelFactory(this@ClinicianLogin)
            )[ClinicianLoginViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar("Settings")
                    }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ClinicianLoginScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicianLoginScreen(clinicianLoginViewModel: ClinicianLoginViewModel) {
    val context = LocalContext.current
    val clinicianKey = clinicianLoginViewModel.clincianKey
    val isAuthorized = clinicianLoginViewModel.isAuthorized
    val clinicianKeyError = clinicianLoginViewModel.clinicianKeyError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Clinician Login", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = clinicianKey,
            label = { Text("Clinician Key") },
            onValueChange = {
                clinicianLoginViewModel.updateClinicianKey(it)
            },
            isError = clinicianKeyError,
            shape = RoundedCornerShape(10.dp),
            placeholder = {Text("Enter your clinician key")},
            singleLine = true,

            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground)
            )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {
                clinicianLoginViewModel.checkCorrectClinicianKey()
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login Icon",
                modifier = Modifier.size(40.dp).padding(end = 10.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text("Clinician Login", style = MaterialTheme.typography.bodyLarge)
        }

        if (clinicianKeyError) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Error: Clinician Key Input does not match special clinician key",
                color = MaterialTheme.colorScheme.error,
            )
        }
        if (isAuthorized) {
            val intent = Intent(context, ClinicianDashboard::class.java)
            (context as? Activity)?.finish()
            context.startActivity(intent)
        }
    }
}