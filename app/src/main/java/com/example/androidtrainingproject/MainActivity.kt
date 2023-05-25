package com.example.androidtrainingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtrainingproject.login.LoginScreen
import com.example.androidtrainingproject.products.ProductDetails
import com.example.androidtrainingproject.ui.theme.AndroidTrainingProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTrainingProjectTheme {
                val navController = rememberNavController()

                var canPop by remember { mutableStateOf(false) }
                navController.addOnDestinationChangedListener { controller, _, _ ->
                    canPop = controller.previousBackStackEntry != null
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Title", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                },
                                navigationIcon = {
                                    if (canPop) {
                                        IconButton(onClick = navController::popBackStack) {
                                            Icon(Icons.Filled.ArrowBack, "back")
                                        }
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary
                                )
                            )
                        }
                    ) {
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
                                ProductDetails(1)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTrainingProjectTheme {
        Greeting("Android")
    }
}