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
import com.example.fitsteps.screens.AutoRoutineMainScreen
import com.example.fitsteps.screens.CreateRoutineScreen
import com.example.fitsteps.screens.CustomRoutineScreen
import com.example.fitsteps.screens.CustomRoutineScreenSteps

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
    }
}