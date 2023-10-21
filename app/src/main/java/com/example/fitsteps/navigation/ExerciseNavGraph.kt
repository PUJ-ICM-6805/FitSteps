package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.screens.exercise.AddExerciseScreen
import com.example.fitsteps.screens.exercise.BarbelBenchScreen
import com.example.fitsteps.screens.CreateRoutineScreen
import com.example.fitsteps.screens.exercise.ExercisesPerMuscleScreen
import com.example.fitsteps.screens.PrepareScreen1
import com.example.fitsteps.screens.PrepareScreen2
import com.example.fitsteps.screens.PrepareScreen3
import com.example.fitsteps.screens.PrepareScreen4
import com.example.fitsteps.screens.exercise.RoutinePreviewScreen
import com.example.fitsteps.screens.exercise.RoutinePreviewScreen2

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
            ExercisesPerMuscleScreen(navController = navController)
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
        composable(
            route = Screen.RoutineScreen2.route
        ) {
            RoutinePreviewScreen2(navController = navController)
        }
    }
}