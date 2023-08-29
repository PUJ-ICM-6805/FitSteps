package com.example.fitsteps

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.screens.BodyScreen
import com.example.fitsteps.screens.ExerciseScreen
import com.example.fitsteps.screens.RunningScreen
import com.example.fitsteps.screens.SocialScreen
import com.example.fitsteps.screens.SummaryScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Summary.route
    ) {
        composable(route = BottomBarScreen.Summary.route) {
            SummaryScreen()
        }
        composable(route = BottomBarScreen.Summary.route) {
            SummaryScreen()
        }
        composable(route = BottomBarScreen.Running.route) {
            RunningScreen()
        }
        composable(route = BottomBarScreen.Exercise.route) {
            ExerciseScreen()
        }
        composable(route = BottomBarScreen.Body.route) {
            BodyScreen()
        }
        composable(route = BottomBarScreen.Social.route) {
            SocialScreen()
        }
    }
}