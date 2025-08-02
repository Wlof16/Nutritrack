package com.fit2081.XuanRen_33523436.nutritrack.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.R
import com.fit2081.XuanRen_33523436.nutritrack.ui.theme.NutritrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Make sure when user is Logged in it pulls from SharedPref and logs in user
            val userLoggedIn = this.getSharedPreferences("user_session", MODE_PRIVATE).getInt("userID", -1).takeIf { it != -1 }
                AuthManager.login(userLoggedIn)
                WelcomeScreen()
            }
        }
    }




//@Preview
@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
//    Added NutritrackTheme to apply global MaterialTheme
    NutritrackTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
//                UI Elements for WelcomeScreen
                Text(
                    text = "NutriTrack",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 140.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.nutrishark),
                    contentDescription = "NutriTrack logo",
                    modifier = Modifier.fillMaxWidth().size(200.dp)
                )

                Text(
                    text = "This app provides general health and nutrition information for\n" +
                            "educational purposes only. It is not intended as medical advice,\n" +
                            "diagnosis, or treatment. Always consult a qualified healthcare\n" +
                            "professional before making any changes to your diet, exercise, or\n" +
                            "health regimen.\n" +
                            "Use this app at your own risk.\n" +
                            "If you’d like to an Accredited Practicing Dietitian (APD), please\n" +
                            "visit the Monash Nutrition/Dietetics Clinic (discounted rates for\n" +
                            "students):\n" +
                            "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition ",

                    style = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    /* Switch to Login Screen */
                    onClick = {context.startActivity(Intent(context, LoginScreen :: class.java))},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Login",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Designed with \uD83D\uDC94 by Ong Xuan Ren (33523436)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}