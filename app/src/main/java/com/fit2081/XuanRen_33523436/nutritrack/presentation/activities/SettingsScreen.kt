package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.presentation.viewmodels.SettingsScreenViewModel
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme

class SettingsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SettingsScreenViewModel = ViewModelProvider(
                this, SettingsScreenViewModel.SettingsScreenViewModelFactory(this@SettingsScreen)
            )[SettingsScreenViewModel::class.java]
            NutritrackTheme(darkTheme = viewModel.isDark) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomAppBar("Settings")
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        SettingScreen(viewModel)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun SettingScreen(settingsScreenViewModel: SettingsScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        val user by settingsScreenViewModel.user.collectAsState(null)
        val username = user?.userName
        val phoneNum = user?.phoneNo
        val userID = settingsScreenViewModel.userID
        val isDark = settingsScreenViewModel.isDark
        val showChangePassDialog = settingsScreenViewModel.showChangePassDialog
        val passSaveSuccess = settingsScreenViewModel.passSaveSuccess
        val userNameSaveSuccess = settingsScreenViewModel.userNameSaveSuccess
        val showChangeUsernameDialog  = settingsScreenViewModel.showChangeUsernameDialog

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Settings", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("ACCOUNT", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)))
        Spacer(modifier = Modifier.height(25.dp))


        CreateClickableUserInfoRow(
            painterResource(id = R.drawable.person_outlined),
            painterResource(id = R.drawable.edit),
            username.toString(),
            "Profile Icon",
        ) {settingsScreenViewModel.updateShowChangeUsernameDialog(true)}

        Spacer(modifier = Modifier.height(15.dp))
        CreateUserInfoRow(
            painterResource(id = R.drawable.phone_outlined),
            phoneNum.toString(),
            "Phone Icon"
        )
        Spacer(modifier = Modifier.height(15.dp))
        CreateUserInfoRow(
            painterResource(id = R.drawable.person_outlined),
            userID.toString(),
            "User ID Icon"
        )

        Spacer(modifier = Modifier.height(25.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(25.dp))

        Text("OTHER SETTINGS", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)))

        Spacer(modifier = Modifier.height(25.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                AuthManager.logout()
                context.getSharedPreferences("user_session", Context.MODE_PRIVATE).edit() {
                    remove(
                        "userID"
                    )
                }
                val intent = Intent(context, LoginScreen::class.java)
                (context as? Activity)?.finishAffinity()
                context.startActivity(intent)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout_outlined),
                    contentDescription = "Logout Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text("Logout", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Logout Arrow Right Icon",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //Add Logic for Clinician Login here
                val intent = Intent(context, ClinicianLogin::class.java)
                context.startActivity(intent)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.key_outlined),
                    contentDescription = "Clinician Login Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text("Clinician Login", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Clinician Login Arrow Right Icon",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        //Row for DarkMode

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                settingsScreenViewModel.toggleDarkMode()
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.darkmode),
                    contentDescription = "DarkMode Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text("Toggle Dark Mode", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
            }

            Switch(
                checked = isDark,
                onCheckedChange = {settingsScreenViewModel.toggleDarkMode()}
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        //Row for change password
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                settingsScreenViewModel.updateShowChangePassDialog(true)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.password),
                    contentDescription = "Change Password Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text("Change Password", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Change Password Arrow Right Icon",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                modifier = Modifier.size(25.dp)
            )
        }
        //If Alert Dialog true
        if (showChangePassDialog) {
            ChangePasswordDialog(settingsScreenViewModel, context)
        }

        if (showChangeUsernameDialog) {
            ChangeUsernameDialog(settingsScreenViewModel, username.toString(), context)
        }
        if (passSaveSuccess) {
            Toast.makeText(context, "Password changed successfull!", Toast.LENGTH_SHORT).show()
            settingsScreenViewModel.updatePassSaveSuccess(false)
        }

        if (userNameSaveSuccess) {
            Toast.makeText(context, "Username changed successfull!", Toast.LENGTH_SHORT).show()
            settingsScreenViewModel.updateUsernameSaveSuccess(false)
        }
    }
}



@Composable
fun CreateUserInfoRow(painterResource: Painter, userInfo: String, contentDesc: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource,
            contentDescription = contentDesc,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(25.dp))
        Text(userInfo, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
    }
}


@Composable
fun CreateClickableUserInfoRow(painterResource: Painter, painterResource2: Painter, userInfo: String, contentDesc: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier.fillMaxWidth(0.7f), ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource,
                    contentDescription = contentDesc,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text(userInfo, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(500)))
            }
        }
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource2,
                contentDescription = contentDesc,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun ChangeUsernameDialog(settingsScreenViewModel: SettingsScreenViewModel, userName: String, context: Context) {
    val newUsername = settingsScreenViewModel.newUsername
    val newUsernameError = settingsScreenViewModel.newUsernameError
    val usernameInputError = settingsScreenViewModel.usernameInputError

    AlertDialog(
        text = {
            Column() {
                Text("Current Username is: $userName")
                CreateOutlinedTextField(
                    value = newUsername,
                    label = "Enter New Username",
                    onValueChange = {
                        settingsScreenViewModel.updateNewUsername(it)
                        settingsScreenViewModel.isValidUsername(it)
                    },
                    isError = newUsernameError,
                    placeholder = "Enter your new username"
                )
                if (newUsernameError) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Error: Username may only contain A-z, whitespaces and underscores.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (usernameInputError) {
                    Toast.makeText(
                        context,
                        "Error: Make Sure new username is not left blank",
                        Toast.LENGTH_LONG
                    ).show()
                    settingsScreenViewModel.updateUsernameInputError(false)
                }
            }
        },
        onDismissRequest = { settingsScreenViewModel.updateShowChangeUsernameDialog(false) },
        dismissButton =  {
            Button(
                onClick = {settingsScreenViewModel.updateShowChangeUsernameDialog(false)},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red)
            ) {
                Text("Cancel")
            } },
        confirmButton = {
            Button(
                onClick = {
                    settingsScreenViewModel.checkUsernameSaveSuccessful()},
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Save")
            }
        }
    )
}

@Composable
fun ChangePasswordDialog(settingsScreenViewModel: SettingsScreenViewModel, context: Context) {
    val oldPass = settingsScreenViewModel.oldPass
    val newPass = settingsScreenViewModel.newPass
    val confirmPass = settingsScreenViewModel.confirmPass
    val oldPassError = settingsScreenViewModel.oldPassError
    val newAndConfirmPassError = settingsScreenViewModel.newAndConfirmPassError
    val inputError = settingsScreenViewModel.passInputError

    AlertDialog(
        text = {
            Column() {
                //Old Password
                CreateOutlinedTextField(
                    value = oldPass,
                    label = "Enter Old Password",
                    onValueChange = {settingsScreenViewModel.updateOldPassword(it)},
                    isError = oldPassError,
                    placeholder = "Enter your previous password"
                )
                Spacer(modifier = Modifier.height(15.dp))
                //New Password
                CreateOutlinedTextField(
                    value = newPass,
                    label = "Enter New Password",
                    onValueChange = {
                        settingsScreenViewModel.updateNewPassword(it)
                        settingsScreenViewModel.isValidPassword(it)},
                    isError = newAndConfirmPassError,
                    placeholder = "Enter your new password"
                )
                Spacer(modifier = Modifier.height(15.dp))
                //Confirm Password
                CreateOutlinedTextField(
                    value = confirmPass,
                    label = "Confirm New Password",
                    onValueChange = {
                        settingsScreenViewModel.updateConfirmPassword(it)
                        settingsScreenViewModel.isValidPassword(it)},
                    isError = newAndConfirmPassError,
                    placeholder = "Confirm your new password"
                )
                if (newAndConfirmPassError) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "New Password does not reach min length(8) or are Mismatched",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (inputError) {
                    Toast.makeText(context, "New Passwords cannot be mismatched, less than 8 characters or empty", Toast.LENGTH_SHORT).show()
                    settingsScreenViewModel.updatePassInputError(false)
                }
                else if (oldPassError) {
                    Toast.makeText(context, "Old Password Input does not macth actual Password", Toast.LENGTH_SHORT).show()
                    settingsScreenViewModel.updateOldPassError(false)
                }
            }
        },
        onDismissRequest = { settingsScreenViewModel.updateShowChangePassDialog(false) },
        dismissButton =  {
            Button(
                onClick = {settingsScreenViewModel.updateShowChangePassDialog(false)},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red)
            ) {
                Text("Cancel")
            } },
        confirmButton = {
            Button(
                onClick = {
                    settingsScreenViewModel.checkPassSaveSuccessful()},
                shape = RoundedCornerShape(10.dp),
                ) {
                    Text("Save")
            }
        }
    )
}

@Composable
fun CreateOutlinedTextField(value: String, label: String, onValueChange: (String) -> Unit, isError: Boolean, placeholder: String) {
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = { onValueChange(it) },
        isError = isError,
        shape = RoundedCornerShape(10.dp),
        placeholder = {Text(placeholder)},
        singleLine = true,

        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onBackground)
    )
}