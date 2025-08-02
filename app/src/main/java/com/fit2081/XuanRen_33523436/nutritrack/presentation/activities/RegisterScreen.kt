package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.RegisterScreenViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme


class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                val viewModel: RegisterScreenViewModel = ViewModelProvider(
                    this, RegisterScreenViewModel.RegisterScreenViewModelFactory(this@RegisterScreen)
                )[RegisterScreenViewModel::class.java]
                RegisterMenu(viewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterMenu(registerScreenViewModel: RegisterScreenViewModel) {
    val context = LocalContext.current

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

                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(15.dp))

                val options by registerScreenViewModel.allUnregisteredPatientsId.collectAsState(initial = emptyList())

                val expanded = registerScreenViewModel.expanded

                val selectedOptionText = registerScreenViewModel.selectedOptionText

                val username = registerScreenViewModel.username
                val usernameError = registerScreenViewModel.usernameError

                val phoneNumber = registerScreenViewModel.phoneNum
                val phoneNumberError = registerScreenViewModel.phoneNumError

                val password = registerScreenViewModel.password
                val confirmPassword = registerScreenViewModel.confirmPassword
                val passwordError = registerScreenViewModel.passwordError

                /* Boolean for when ID doesn't match phone num in CSV */
                val loginError = registerScreenViewModel.loginError

                /* Boolean when ID is not selected or phone num not valid*/
                val inputError = registerScreenViewModel.inputError

                val userRegistered = registerScreenViewModel.userRegistered

                //User Name Field
                OutlinedTextField(
                    value = username,
                    label = { Text("Username") },
                    onValueChange = {
                        registerScreenViewModel.updateUsername(it)
                        registerScreenViewModel.isValidUsername(it)
                    },

                    shape = RoundedCornerShape(10.dp),
                    placeholder = {Text("Enter Username")},
                    isError = usernameError,
                    singleLine = true,

                    modifier = Modifier.fillMaxWidth(),
                )

                if (usernameError) {
                    Toast.makeText(context, "Error: Username may only contain A-z, whitespaces and underscores.", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.height(20.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        registerScreenViewModel.toggleExpanded()
                    }
                ) {

                    //ID Field
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = {
                            registerScreenViewModel.updateLogInError(false)
                            registerScreenViewModel.updateInputError(false)},
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
                            registerScreenViewModel.updateExpanded(false)
                        }) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.toString()) },
                                onClick = {
                                    registerScreenViewModel.updateSelectedOptionText(selectionOption.toString())
                                    registerScreenViewModel.updateExpanded(false)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Phone number Field
                OutlinedTextField(
                    value = phoneNumber,
                    label = { Text("Phone number") },
                    onValueChange = {
                        registerScreenViewModel.updatePhoneNum(it)
                        registerScreenViewModel.isValidPhone(it)
                        registerScreenViewModel.updateLogInError(false)
                        registerScreenViewModel.updateInputError(false)
                    },

                    shape = RoundedCornerShape(10.dp),
                    placeholder = {Text("Enter your number")},
                    isError = phoneNumberError,
                    singleLine = true,

                    modifier = Modifier.fillMaxWidth(),
                    )

                if (phoneNumberError) {
                    Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Password Field
                OutlinedTextField(
                    value = password,
                    label = { Text("Password") },
                    onValueChange = {
                        registerScreenViewModel.updatePassword(it)
                        registerScreenViewModel.isValidPassword(it)
                    },

                    shape = RoundedCornerShape(10.dp),
                    placeholder = {Text("Enter your password")},
                    isError = passwordError,
                    singleLine = true,

                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(20.dp))

                //Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    label = { Text("Confirm Password") },
                    onValueChange = {
                        registerScreenViewModel.updateConfirmPassword(it)
                        registerScreenViewModel.isValidPassword(it)
                    },

                    shape = RoundedCornerShape(10.dp),
                    placeholder = {Text("Confirm your password")},
                    isError = passwordError,
                    singleLine = true,

                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (passwordError) {
                    Text(
                        text = "Password does not reach min length(8) or are Mismatched",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "This app is only for pre-registered users. Please have your ID, phone number and password to claim your account.",
                    style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    registerScreenViewModel.registerButtonCheck()
                },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(10.dp),) {
                    Text("Register", style = MaterialTheme.typography.bodyLarge)

                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = {
                    val intent = Intent(context, LoginScreen::class.java)
                    context.startActivity(intent)
                },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(10.dp),) {
                    Text("Login", style = MaterialTheme.typography.bodyLarge)

                }

                Spacer(modifier = Modifier.height(30.dp))

                if (inputError) {
                    Toast.makeText(context, "Error: Please enter ID, Password & Username along with a valid Phone No.", Toast.LENGTH_LONG).show()
                    registerScreenViewModel.updateInputError(false)
                }

                else if (loginError) {
                    Toast.makeText(context, "Error: Please ensure that ID corresponds to phone number.", Toast.LENGTH_SHORT).show()
                    registerScreenViewModel.updateLogInError(false)
                }

                if (userRegistered) {
                    Toast.makeText(context, "User successfully registered, Proceed to Login page.", Toast.LENGTH_SHORT).show()
                    registerScreenViewModel.updateUserRegistered(false)
                }
            }
        }
    }
}


