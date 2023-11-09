package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.screens.training.ExerciseScreen
import com.example.fitsteps.firebaseData.firebaseRunningData.RunningViewModel
import com.example.fitsteps.screens.ExerciseScreen
import com.example.fitsteps.screens.ProfileScreen
import com.example.fitsteps.screens.SocialScreen
import com.example.fitsteps.screens.SummaryScreen
import com.example.fitsteps.screens.running.MainRunning
import com.example.fitsteps.screens.running.MyRoutesPartOne
import com.example.fitsteps.screens.running.MyRoutesPartTwo
import com.example.fitsteps.screens.running.RunningMap

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomBarNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    runningViewModel: RunningViewModel = RunningViewModel(),
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
            MainRunning(
                navController = navController,
                rootNavController = rootNavController,
                runningViewModel = runningViewModel
            )
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
