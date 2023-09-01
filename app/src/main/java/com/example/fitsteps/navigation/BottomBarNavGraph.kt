package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.screens.BodyScreen
import com.example.fitsteps.screens.ExerciseScreen
import com.example.fitsteps.screens.ProfileScreen
import com.example.fitsteps.screens.RunningScreen
import com.example.fitsteps.screens.SocialScreen
import com.example.fitsteps.screens.SummaryScreen

@Composable
fun BottomBarNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
){
    NavHost(
        navController = navController,
        route = HOME_ROUTE,
        startDestination = BottomBarScreen.Summary.route
    ) {
        composable(route = BottomBarScreen.Summary.route) {
            SummaryScreen(navController = navController, rootNavController = rootNavController)
        }
        composable(route = BottomBarScreen.Running.route) {
            RunningScreen()
        }
        composable(route = BottomBarScreen.Exercise.route) {
            ExerciseScreen(navController = navController, rootNavController = rootNavController)
        }
        composable(route = BottomBarScreen.Social.route) {
            SocialScreen()
        }
        customRoutineNavGraph(navController = navController)
        bodyNavGraph(navController = navController, rootNavController = rootNavController)
        exerciseNavGraph(navController = navController)
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController, rootNavController = rootNavController)
        }
    }
}
