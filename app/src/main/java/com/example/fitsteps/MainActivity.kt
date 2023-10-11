package com.example.fitsteps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.SetupNavGraph
import com.example.fitsteps.ui.theme.FitStepsTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
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