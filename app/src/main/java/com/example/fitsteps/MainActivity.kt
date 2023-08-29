package com.example.fitsteps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.SetupNavGraph
import com.example.fitsteps.ui.theme.FitStepsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitStepsTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }
}