package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.rest.ImageFromJSONViewModel
import com.example.fitsteps.screens.training.trainingMainScreen.ExerciseScreen
import com.example.fitsteps.firebaseData.firebaseRunningData.RunningViewModel
import com.example.fitsteps.screens.ProfileScreen
import com.example.fitsteps.screens.social.SocialScreen
import com.example.fitsteps.screens.SummaryScreen
import com.example.fitsteps.screens.running.MainRunning
import com.example.fitsteps.screens.running.MyRoutesPartOne
import com.example.fitsteps.screens.running.MyRoutesPartTwo
import com.example.fitsteps.screens.running.RunningMap
import com.example.fitsteps.screens.social.UserContactsViewModel

@Composable
fun BottomBarNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    runningViewModel: RunningViewModel = RunningViewModel(),
    trainingProgramViewModel: TrainingProgramViewModel = TrainingProgramViewModel(),
    imagesViewModel: ImageFromJSONViewModel = remember { ImageFromJSONViewModel() },
    contactsViewModel: UserContactsViewModel = UserContactsViewModel()
){
    NavHost(
        navController = navController,
        route = HOME_ROUTE,
        startDestination = BottomBarScreen.Summary.route
    ) {
        composable(route = BottomBarScreen.Summary.route) {
            SummaryScreen(navController = navController, rootNavController = rootNavController, trainingProgramViewModel)
        }
        composable(route = BottomBarScreen.Running.route) {
            MainRunning(
                navController = navController,
                rootNavController = rootNavController,
                runningViewModel = runningViewModel
            )
        }
        composable(route = BottomBarScreen.Exercise.route) {
            ExerciseScreen(navController = navController,
                rootNavController = rootNavController,
                trainingProgramViewModel = trainingProgramViewModel,
                imagesViewModel = imagesViewModel)
        }
        composable(route = BottomBarScreen.Social.route) {
            SocialScreen(userContactsViewModel = contactsViewModel)
        }
        customRoutineNavGraph(navController = navController)
        bodyNavGraph(navController = navController, rootNavController = rootNavController)
        exerciseNavGraph(navController = navController, trainingProgramViewModel)
        //TODO organize this
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController, rootNavController = rootNavController)
        }
        composable(
            route = Screen.RunningMapScreen.route
        ) {
            RunningMap(navController = navController)
        }
        composable(
            route = Screen.RunningRouteDetails.route
        ) {
            MyRoutesPartOne(
                navController = navController,
                rootNavController = rootNavController,
                runningViewModel = runningViewModel
            )
        }
        composable(
            route = Screen.RunningRouteDetails2.route
        ) {
            MyRoutesPartTwo(
                navController = navController,
                rootNavController = rootNavController,
                runningViewModel = runningViewModel
            )
        }
    }
}
