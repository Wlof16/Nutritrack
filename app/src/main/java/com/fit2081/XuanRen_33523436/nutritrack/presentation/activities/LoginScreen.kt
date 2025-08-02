package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.LoginScreenViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme
import androidx.core.content.edit
import kotlin.math.log

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                val viewModel: LoginScreenViewModel = ViewModelProvider(
                    this, LoginScreenViewModel.LoginScreenViewModelFactory(this@LoginScreen)
                )[LoginScreenViewModel::class.java]
                    LoginMenu(viewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginMenu(loginScreenViewModel: LoginScreenViewModel) {
    NutritrackTheme {
        val context = LocalContext.current
        val hasUserLoggedIn = loginScreenViewModel.hasUserLoggedIn
        if (hasUserLoggedIn) {
            loginScreenViewModel.updateHasUserLoggedIn(false)
            val intent = Intent(context, FoodIntakeQuestionnaire::class.java)
            (context as? Activity)?.finish()
            context.startActivity(intent)
        }

        Column (modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top)
        {
            Box(modifier = Modifier
                        .fillMaxWidth().height(70.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background

            ) {

                Column(
                    modifier = Modifier.fillMaxSize().padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {



                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    val options by loginScreenViewModel.allRegisteredPatientsId.collectAsState(initial = emptyList())

                    val expanded = loginScreenViewModel.expanded
                    val selectedOptionText = loginScreenViewModel.selectedOptionText
                    val password = loginScreenViewModel.password
                    val passwordError = loginScreenViewModel.passwordError

                    /* Boolean for when ID doesn't match password in Database */
                    val loginError = loginScreenViewModel.loginError

                    /* Boolean when ID is not selected or password is not valid*/
                    val inputError = loginScreenViewModel.inputError
                    val isAuthorized = loginScreenViewModel.isAuthorized
                    val isPasswordVisible = loginScreenViewModel.isPasswordVisible

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            loginScreenViewModel.toggleExpanded()
                        }
                    ) {

                        OutlinedTextField(
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = {
                                loginScreenViewModel.updateLogInError(false)
                                loginScreenViewModel.updateInputError(false)},
                            label = { Text("My ID (Provided by your Clinician) ") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                loginScreenViewModel.updateExpanded(false)
                            }) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.toString()) },
                                    onClick = {
                                        loginScreenViewModel.updateSelectedOptionText(selectionOption.toString())
                                        loginScreenViewModel.updateExpanded(false)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    OutlinedTextField(
                        value = password,
                        label = { Text("Password") },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = {
                            loginScreenViewModel.updatePass(it)
                            loginScreenViewModel.isPasswordValid(it)
                            loginScreenViewModel.updateLogInError(false)
                            loginScreenViewModel.updateInputError(false)
                        },
                        trailingIcon = {
                            val visibilityImage = if (isPasswordVisible) painterResource(id = R.drawable.password_visible) else painterResource(id = R.drawable.not_password_visible)
                            IconButton(onClick = { loginScreenViewModel.togglePassView() }) {
                                Icon(painter = visibilityImage, contentDescription = if (isPasswordVisible) "Hide password" else "Show password")
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        placeholder = {Text("Enter your password")},
                        isError = passwordError,
                        singleLine = true,

                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (passwordError) {
                        Text(
                            text = "Invalid Password (Must have a min length of 8)",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(text = "This app is only for pre-registered users. Please have your ID and password or Register to claim your account on your first try.",
                        style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            /* Check if valid Password and not default ID */
                            loginScreenViewModel.loginButtonCheck()
                        },
                        modifier = Modifier.fillMaxWidth().height(55.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Continue", style = MaterialTheme.typography.bodyLarge)

                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            /* Move to Register Screen */
                            val intent = Intent(context, RegisterScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth().height(55.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Register", style = MaterialTheme.typography.bodyLarge)

                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    if (isAuthorized) {
                        loginScreenViewModel.updateLogInError(false)

                        context.getSharedPreferences("user_session", Context.MODE_PRIVATE).edit() {
                            putInt(
                                "userID",
                                loginScreenViewModel.loggedInUser
                            )
                        }

                        val intent = Intent(context, FoodIntakeQuestionnaire::class.java)
                        (context as? Activity)?.finish()
                        context.startActivity(intent)
                    }

                    if (inputError) {
                        Text(
                            "Error: Please ensure you have selected an ID and that password is valid",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    else if (loginError) {
                        Text(
                            "Error: Please ensure that ID corresponds to password",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}