package com.example.androidtrainingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.androidtrainingproject.ui.NavGraphs
import com.example.androidtrainingproject.ui.theme.AndroidTrainingProjectTheme
import com.ramcosta.composedestinations.DestinationsNavHost

import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTrainingProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navigateUp = navController::navigateUp

                    val navHostEngine = rememberNavHostEngine()
                    DestinationsNavHost(
                        startRoute = NavGraphs.login,
                        engine = navHostEngine,
                        navController = navController,
                        dependenciesContainerBuilder = {
                            dependency(navigateUp)
                        },
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}
