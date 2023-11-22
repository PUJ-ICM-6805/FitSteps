package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.firebaseData.firebaseExerciseData.ExerciseViewModel
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.screens.training.exercise.AddExerciseScreen
import com.example.fitsteps.screens.training.routine.RoutineExecutionScreen
import com.example.fitsteps.screens.training.trainingPlanScreen.CreateRoutineScreen
import com.example.fitsteps.screens.training.exercise.ExercisesPerMuscleScreen
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
            route = Screen.RoutineExecutionScreen.route
        ) {
            RoutineExecutionScreen(navController = navController, trainingProgramViewModel = trainingProgramViewModel)
        }
    }
}