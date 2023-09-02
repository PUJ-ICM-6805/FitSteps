package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.R
import com.example.fitsteps.screens.AddExerciseScreen
import com.example.fitsteps.screens.AutoRoutineMainScreen
import com.example.fitsteps.screens.BarbelBenchScreen
import com.example.fitsteps.screens.CreateRoutineScreen
import com.example.fitsteps.screens.CustomRoutineScreen
import com.example.fitsteps.screens.CustomRoutineScreenSteps
import com.example.fitsteps.screens.PrepareScreen1
import com.example.fitsteps.screens.PrepareScreen2
import com.example.fitsteps.screens.PrepareScreen3
import com.example.fitsteps.screens.PrepareScreen4
import com.example.fitsteps.screens.RoutinePreviewScreen

fun NavGraphBuilder.exerciseNavGraph(
    navController: NavHostController,
){
    navigation(
        startDestination = Screen.PlanScreen.route,
        route = PLAN_ROUTE
    ){
        composable(
            route = Screen.PlanScreen.route
        ) {
            CreateRoutineScreen(navController = navController)
        }
        composable(
            route = Screen.AddExerciseScreen.route
        ) {
            AddExerciseScreen(navController = navController)
        }
        composable(
            route = Screen.AddSpecificExerciseScreen.route
        ) {
            CreateRoutineScreen(navController = navController)
        }
        composable(
            route = Screen.RoutineScreen.route
        ) {
            RoutinePreviewScreen(navController = navController)
        }
        composable(
            route = Screen.DemoPrepareScreen.route
        ) {
            BarbelBenchScreen(navController = navController)
        }
        composable(
            route = Screen.DemoPrepareScreen1.route
        ) {
            PrepareScreen1(navController = navController)
        }
        composable(
            route = Screen.DemoPrepareScreen2.route
        ) {
            PrepareScreen2(navController = navController)
        }
        composable(
            route = Screen.DemoPrepareScreen3.route
        ) {
            PrepareScreen3(navController = navController)
        }
        composable(
            route = Screen.DemoPrepareScreen4.route
        ) {
            PrepareScreen4(navController = navController)
        }
    }
}