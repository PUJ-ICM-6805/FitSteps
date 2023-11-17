package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.firebaseData.firebaseExerciseData.ExerciseViewModel
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.screens.training.exercise.AddExerciseScreen
import com.example.fitsteps.screens.training.routine.BarbelBenchScreen
import com.example.fitsteps.screens.training.trainingPlanScreen.CreateRoutineScreen
import com.example.fitsteps.screens.training.exercise.ExercisesPerMuscleScreen
import com.example.fitsteps.screens.training.routine.PrepareScreen1
import com.example.fitsteps.screens.training.routine.PrepareScreen2
import com.example.fitsteps.screens.training.routine.PrepareScreen3
import com.example.fitsteps.screens.training.routine.PrepareScreen4
import com.example.fitsteps.screens.training.exercise.RoutinePreviewScreen

fun NavGraphBuilder.exerciseNavGraph(
    navController: NavHostController,
    trainingProgramViewModel: TrainingProgramViewModel
){
    val exerciseViewModel = ExerciseViewModel()
    navigation(
        startDestination = Screen.PlanScreen.route,
        route = PLAN_ROUTE
    ){
        composable(
            route = Screen.PlanScreen.route
        ) {
            CreateRoutineScreen(navController = navController, trainingProgramViewModel = trainingProgramViewModel)
        }
        composable(
            route = Screen.AddExerciseScreen.route
        ) {
            AddExerciseScreen(navController = navController, exerciseViewModel = exerciseViewModel)
        }
        composable(
            route = Screen.AddSpecificExerciseScreen.route
        ) {
            ExercisesPerMuscleScreen(navController = navController, exerciseViewModel = exerciseViewModel, trainingProgramViewModel = trainingProgramViewModel)
        }
        composable(
            route = Screen.RoutineScreen.route
        ) {
            RoutinePreviewScreen(navController = navController, trainingProgramViewModel = trainingProgramViewModel)
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