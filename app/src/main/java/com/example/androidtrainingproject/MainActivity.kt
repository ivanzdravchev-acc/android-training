package com.example.androidtrainingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtrainingproject.ui.theme.AndroidTrainingProjectTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTrainingProjectTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //LoginScreen()
                    Scaffold {
                        NavHost(
                            navController = navController,
                            startDestination = "Login",
                            modifier = Modifier.padding(it)
                        ) {
                            composable(route = "Login") {
                                LoginScreen(navigateToProductDetails = {
                                    navController.navigate("ProductDetails")
                                })
                            }
                            composable(route = "ProductDetails") {
                                ProductDetails()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navigateToProductDetails: () -> Unit = {}) {
//    var (textField, setTextField) = remember { mutableStateOf("") }
    val usernameField = remember { mutableStateOf("") }
    val passwordField = remember { mutableStateOf("") }

    val isPasswordVisible = remember { mutableStateOf(false) }

    val hardcodedUsername = "admin@test.com"
    val hardcodedPassword = "1234"

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "Log in", fontSize = 26.sp , modifier = Modifier
            .padding(bottom = 10.dp)

        )
        TextField(
            value = usernameField.value,
            onValueChange = {
                newValue -> usernameField.value = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(3.dp)),
            placeholder = { Text(text = "Email")}
        )
        TextField(
            value = passwordField.value,
            onValueChange = {
                newValue -> passwordField.value = newValue
            },
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(3.dp)),
            placeholder = { Text(text = "Password")},
            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                }) {
                    if (isPasswordVisible.value) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_visibility_on),
                            contentDescription = "password visibility on",
                            Modifier
                                .height(36.dp)
                                .width(32.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_visibility_off),
                            contentDescription = "password visibility off",
                            Modifier
                                .height(36.dp)
                                .width(32.dp)
                        )
                    }
                }
            },
        )
        Button(
            onClick = {
                if (usernameField.value == hardcodedUsername && passwordField.value == hardcodedPassword) {
                    navigateToProductDetails()
                }
                passwordField.value = ""
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Log in")
        }
    }
}

@Composable
fun ProductDetails() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {
        Text(
            text = "Item",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_android_logo),
                contentDescription = "Item Image",
                Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
                    .width(240.dp)
                    .height(240.dp)
                    .border(BorderStroke(1.dp, Color.Black))
            )
        }
        Text(
            text = "Indulge in a heavenly tea experience with our Stargazer's Tea Set, featuring a constellation-themed teapot and matching teacups. Crafted from fine porcelain, this elegant set will transport you to the cosmos with every sip.",
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = "$90.00",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF47337A)),
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Add to cart")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTrainingProjectTheme {
        Greeting("Android")
    }
}